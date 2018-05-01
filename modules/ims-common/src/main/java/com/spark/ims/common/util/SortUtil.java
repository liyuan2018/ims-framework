package com.spark.ims.common.util;


import java.util.*;

public class SortUtil {

    public static <T> List<T> sortMapByType(Map<Integer,T> map){
        Set<Integer> ids = map.keySet();
        List<Integer> sortedIds = set2list(ids);
        List<T> results = new ArrayList<>();
        for(Integer id:sortedIds){
            results.add(map.get(id));
        }
        return results;
    }


    public  static List<Integer> set2list(Set<Integer> ids){
        List<Integer> lists = new ArrayList<>();
        for(Integer id:ids){
            lists.add(id);
        }
        Collections.sort(lists);
        return lists;
    }
}

class MyComparator<T> implements Comparator<T>{
    /**
     * 排序，1升序，-1降序，默认升序
     */
    private int sort = 1;
    public MyComparator() {

    }
    public MyComparator(int sort){
        this.sort = sort;
    }
    @Override
    public int compare(T o1, T o2) {
        String name = o1.getClass().getSimpleName();
        int result = 0;
        switch(this.sort){
            case 1:
                if("Integer".equals(name)){
                    result = (Integer)o1-(Integer)o2;
                }
                else if("Double".equals(name)){
                    result = (int) ((Double)o1-(Double)o2);
                }
                else if("String".equals(name)){
                    result = ((String)o1).compareTo((String)o2);
                }
                break;
            case -1:
                if("Integer".equals(name)){
                    result = (Integer)o2-(Integer)o1;
                }
                else if("Double".equals(name)){
                    result = (int) ((Double)o2-(Double)o1);
                }
                else if("String".equals(name)){
                    result = ((String)o2).compareTo((String)o1);
                }
                break;
            default:
                result =  0;
        }
        return result;
    }
}