package com.dj.boot.controller.job;

import com.dj.boot.pojo.ScheduleJob;
import com.dj.boot.service.scheduleJob.ScheduleJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @ClassName JobController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/11 19:15
 * @Version 1.0
 **/
@RestController
public class JobController {

    private static Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private SchedulerFactoryBean factory;
    @Autowired
    private ScheduleJobService jobService;
    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            reStartAllJobs();
            logger.info("INIT SUCCESS");
        } catch (SchedulerException e) {
            logger.info("INIT EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }
    //根据ID重启某个Job
    @RequestMapping("/refresh/{id}")
    public String refresh(@PathVariable Integer id) throws SchedulerException {
        String result;
        ScheduleJob entity = jobService.getJobEntityById(id);
        if (entity == null) return "error: id is not exist ";
        TriggerKey triggerKey = new TriggerKey(entity.getBeanName(), entity.getMethodName());
        JobKey jobKey = jobService.getJobKey(entity);
        Scheduler scheduler = factory.getScheduler();
        try {
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getDescription(), map);
            if (entity.getStatus() == 0) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getBeanName()  + " success !";
            } else {
                result = "Refresh Job : " + entity.getBeanName() + " failed ! , " +
                        "Because the Job status is " + entity.getStatus();
            }
        } catch (SchedulerException e) {
            result = "Error while Refresh " + e.getMessage();
        }
        return result;
    }
    //重启数据库中所有的Job
    @RequestMapping("/refresh/all")
    public String refreshAll() {
        String result;
        try {
            reStartAllJobs();
            result = "SUCCESS";
        } catch (SchedulerException e) {
            result = "EXCEPTION : " + e.getMessage();
        }
        return "refresh all jobs : " + result;
    }
    /**
     * 重新启动所有的job
     */
    private void reStartAllJobs() throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : set) {
            scheduler.deleteJob(jobKey);
        }
        for (ScheduleJob job : jobService.getAllJobEntity()) {
            logger.info("Job register beanName : {} , methodName : {} , cron : {}", job.getBeanName(), job.getMethodName(), job.getCron());
            JobDataMap map = jobService.getJobDataMap(job);
            JobKey jobKey = jobService.getJobKey(job);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, job.getDescription(), map);
            if (job.getStatus() == 0) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
            } else {
                logger.info("Job jump beanName : {} , Because {} status is {}", job.getBeanName(), job.getBeanName(), job.getStatus());
            }
        }
    }
}
