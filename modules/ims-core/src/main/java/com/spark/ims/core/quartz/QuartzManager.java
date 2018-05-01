package com.spark.ims.core.quartz;

import com.spark.ims.common.util.ParamUtils;
import com.spark.ims.common.util.SpringUtils;
import com.spark.ims.common.util.UUIDUtils;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 定时任务管理类
 *
 * Created by liyuan on 2018/4/26.
 */
public class QuartzManager {

    private static Scheduler scheduler = (Scheduler) SpringUtils.getBean("schedulerFactory");

    /**
     * 添加任务
     *
     * @param name
     * @param clz
     * @param cronException
     * @param parameter
     * @throws SchedulerException
     */
    public static void createJob(String name, Class<? extends Job> clz, String cronException, Map<String, Object> parameter) throws SchedulerException {
    	JobDetail jobDetail = null;
    	JobKey jobKey = new JobKey(name);
    	if (ParamUtils.isNull(scheduler.getJobDetail(jobKey))) {
    		jobDetail = JobBuilder.newJob(clz).withIdentity(name).storeDurably().build(); 
    		scheduler.addJob(jobDetail, false);
    	} else {
        	jobDetail = scheduler.getJobDetail(jobKey);
    	}
    	JobDataMap jobDataMap = new JobDataMap();
    	if (!ParamUtils.isEmpty(parameter)) {
			for (Entry<String, Object> entry : parameter.entrySet()) {
				jobDataMap.put(entry.getKey(), entry.getValue());
	        }
        }
        CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronException);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + name + UUIDUtils.generate()).forJob(jobDetail).usingJobData(jobDataMap).withSchedule(schedBuilder).build();
        scheduler.scheduleJob(trigger);
    }

    /**
     * 将java.util.Date转换成cronException
     *
     * @param date
     * @return
     */
    public static String dateToCronException(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }

}
