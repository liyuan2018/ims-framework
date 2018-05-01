package com.spark.ims.core.service.impl;

import com.spark.ims.common.domain.BaseModel;
import com.spark.ims.common.domain.BaseRelationModel;
import com.spark.ims.common.domain.SearchFilter;
import com.spark.ims.common.enums.MatchType;
import com.spark.ims.common.util.BeanUtils;
import com.spark.ims.common.util.ClassUtils;
import com.spark.ims.common.util.ReflectUtils;
import com.spark.ims.core.exception.SystemException;
import com.spark.ims.core.repository.BaseJpaRepository;
import com.spark.ims.core.util.ServiceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.spark.ims.service.IBaseRelationService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：基础关联关系service实现类
 *
 * Created by liyuan on 2018/4/26.
 */
@Transactional
public class BaseRelationServiceImpl<T extends BaseRelationModel, ID extends Serializable> implements IBaseRelationService<T, ID> {

    public static final Logger logger = LoggerFactory.getLogger(BaseRelationServiceImpl.class);

    @Autowired
    protected BaseJpaRepository<T, ID> repository;

	public T create(@Valid T model) {
		// 确保只是新增
		model.setId(null);
		List<T> models = repository.find(model);
		if (models != null && models.size() > 0) {
			return models.get(0);
		}
		return repository.save(model);
	}

	public List<T> batchCreate(@Valid @Size(min = 1) List<T> models) {
		List<T> newModels = new ArrayList<T>();
		for (T model : models) {
			newModels.add(create(model));
		}
		return newModels;
	}

    public void deleteById(ID id){
        repository.delete((T) id);
    }

    public void deleteByIds(@Size(min = 1) ID[] ids){
        repository.deleteByIds(ids);
    }

    public T findOne(T model) {
        return repository.findOne(model);
    }

    public List<T> find(T entity) {
        return this.repository.find(entity);
    }

    public Page<Map<String, Object>> findWithMaster(String relativeId, String principalType, List<SearchFilter> searchFilters,
                                                    Pageable pageable) throws Exception {
        Page<BaseModel> masterPage = ServiceUtils.getTargetService(((T) getModelClass().newInstance()).gainMasterClass().getSimpleName()).
                findMaster(getModelClass(), relativeId, principalType, searchFilters, pageable);
        List<Map<String, Object>> content = formatResult(masterPage.getContent(), principalType, relativeId, true);
        return new PageImpl<Map<String, Object>>(content, pageable, masterPage.getTotalElements());
    }

    public List<Map<String, Object>> findWithMaster(String relativeId, String principalType, List<SearchFilter> searchFilters)
            throws Exception {
        List<BaseModel> masterList = ServiceUtils.getTargetService(((T) getModelClass().newInstance()).gainMasterClass().getSimpleName()).
                findMaster(getModelClass(), relativeId, principalType, searchFilters);
        return formatResult(masterList, principalType, relativeId, true);
    }

    public Page<Map<String, Object>> findWithPrincipal(String relativeId, String principalType,
                                                       List<SearchFilter> searchFilters, Pageable pageable) throws Exception {
        Page<BaseModel> principalPage = ServiceUtils.getTargetService(principalType).findPrincipal(getModelClass(), relativeId, searchFilters, pageable);
        List<Map<String, Object>> content = formatResult(principalPage.getContent(), principalType, relativeId, false);
        return new PageImpl<Map<String, Object>>(content, pageable, principalPage.getTotalElements());
    }

    public List<Map<String, Object>> findWithPrincipal(String relativeId, String principalType,
                                                       List<SearchFilter> searchFilters) throws Exception {
        List<BaseModel> principalList = ServiceUtils.getTargetService(principalType).findPrincipal(getModelClass(), relativeId, searchFilters);
        return formatResult(principalList, principalType, relativeId, false);
    }

    public Page excludeMaster(String relativeId, String principalType, List<SearchFilter> searchFilters,
                              Pageable pageable) throws Exception {
        return ServiceUtils.getTargetService(((T) getModelClass().newInstance()).gainMasterClass().getSimpleName()).
                excludeMaster(getModelClass(), relativeId, principalType, searchFilters, pageable);
    }

    public Page excludePrincipal(String relativeId, String principalType, List<SearchFilter> searchFilters,
                                 Pageable pageable) throws Exception {
        return ServiceUtils.getTargetService(principalType).excludePrincipal(getModelClass(), relativeId, searchFilters, pageable);
    }

    /**
     * 格式化关联关系列表信息
     *
     * @param baseModelList 目标模型列表
     * @param principalType 辅表类型
     * @param relativeId    相对模型ID
     * @param isMaster      是否为查找主表
     * @return 格式化关联关系列表信息
     * @throws Exception
     */
    private List<Map<String, Object>> formatResult(List<BaseModel> baseModelList, String principalType,
                                                   String relativeId, boolean isMaster) throws Exception {
        List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
        if (baseModelList != null && baseModelList.size() > 0) {
            T relationModel = (T) getModelClass().newInstance();
            relationModel.setPrincipalType(principalType);
            for (BaseModel baseModel : baseModelList) {
                if (isMaster) {
                    relationModel.setPrincipalId(relativeId);
                    relationModel.setObjectId(baseModel.getId());
                } else {
                    relationModel.setPrincipalId(baseModel.getId());
                    relationModel.setObjectId(relativeId);
                }
                Map<String, Object> map = BeanUtils.convertBean(this.find(relationModel).get(0));
                map.put(ClassUtils.getLowerCamelAndSingularize(baseModel.getClass().getSimpleName()), baseModel);
                content.add(map);
            }
        }
        return content;
    }

    /**
     * 获取泛型模型的类型
     *
     * @return
     */
    private Class getModelClass() {
        return ReflectUtils.getClassGenricType(getClass());
    }

	public List<T> findByObjectId(String objectId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
        relationModel.setObjectId(objectId);
		return repository.find(relationModel);
	}

	public List<T> findByObjectIdIn(List<String> objectIds) {
		List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
		searchFilters.add(new SearchFilter("objectId", MatchType.INQ, objectIds));
		return repository.find(searchFilters);
	}

	public List<T> findByPrincipalTypeAndObjectId(String principalType , String objectId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
	     relationModel.setPrincipalType(principalType);
	     relationModel.setObjectId(objectId);   
		return repository.find(relationModel);
	}

	public List<T> findByPrincipalTypeAndObjectIdIn(String principalType, List<String> objectIds) {
		List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
		searchFilters.add(new SearchFilter("principalType", MatchType.EQ, principalType));
		searchFilters.add(new SearchFilter("objectId", MatchType.INQ, objectIds));
		return repository.find(searchFilters);
	}

	public List<T> findByPrincipalTypeAndPrincipalId(String principalType,
			String principalId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setPrincipalType(principalType);
		relationModel.setPrincipalId(principalId);
		return repository.find(relationModel);
	}

	public void deleteByObjectId(String objectId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setObjectId(objectId);
		repository.deleteInBatch(relationModel);
	}

	public void deleteByObjectIdIn(List<String> objectIds) {
		List<T> relationModels = new ArrayList<T>();
		if (CollectionUtils.isNotEmpty(objectIds)) {
			for(String objectId:objectIds){
				T relationModel = null;
				try {
					relationModel = (T)getModelClass().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					throw new SystemException("关联关系创建实例失败");
				}
				relationModel.setObjectId(objectId);
				relationModels.add(relationModel);
			}
		}
		repository.deleteInBatch(relationModels);
	}

		public void deleteByPrincipalTypeAndObjectId(String principalType,
			String objectId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setPrincipalType(principalType);
		relationModel.setObjectId(objectId);
		repository.deleteInBatch(relationModel);
	}

	public void deleteByPrincipalTypeAndPrincipalId(String principalType,
			String principalId){
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setPrincipalType(principalType);
		relationModel.setPrincipalId(principalId);
		repository.deleteInBatch(relationModel);
		
	}

	public void deleteByPrincipalId(String principalId) {
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setPrincipalId(principalId);
		repository.deleteInBatch(relationModel);
	}

	public void deleteByPrincipalTypeAndPrincipalIdAndObjectId(
		String principalType, String principalId, String objectId){
		T relationModel = null;
		try {
			relationModel = (T)getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("关联关系创建实例失败");
		} 
		relationModel.setObjectId(objectId);
		relationModel.setPrincipalId(principalId);
		relationModel.setPrincipalType(principalType);
		repository.deleteInBatch(relationModel);
	}

	public List<T> createRelations(String objectId, String principalType,
			List<? extends BaseModel> principals) {
		List<T> relations = new ArrayList<T>();
		if (CollectionUtils.isNotEmpty(principals)) {
			for(BaseModel principal:principals){
				T relationModel = null;
				try {
					relationModel = (T)getModelClass().newInstance();
				} catch (Exception e) {
					throw new SystemException("关联关系创建实例失败");
				}
				relationModel.setObjectId(objectId);
				relationModel.setPrincipalId(principal.getId());
				relationModel.setPrincipalType(principalType);
				relations.add(relationModel);
			}
			return batchCreate(relations);
		}
		return relations;
	}

}
