package t.quartz2.example2;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class LoadkettleRepositoryJob {

	private SchedulerFactoryBean schedulerFactoryBean;

	public void LoadkettleRepository() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			List<ScheduleJob> jobList = new ArrayList();

			for (int i = 0; i < 10; i++) {
				ScheduleJob schedulejob = new ScheduleJob();
				schedulejob.setJobId(String.valueOf(i));
				schedulejob.setJobName("任务-" + schedulejob.getJobId());
				schedulejob.setJobGroup("groupsimpletrigger");
				schedulejob.setCronExpression("30/1 0/1 * * * ?");
				jobList.add(schedulejob);
			}

			for (ScheduleJob job : jobList) {

				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
						job.getJobGroup());

				// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
				CronTrigger trigger = (CronTrigger) scheduler
						.getTrigger(triggerKey);

				// 不存在，创建一个
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder
							.newJob(QuartzJobFactory.class)
							.withIdentity(job.getJobName(), job.getJobGroup())
							.build();
					jobDetail.getJobDataMap().put("scheduleJob", job);

					// 表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
							.cronSchedule(job.getCronExpression());

					// 按新的cronExpression表达式构建一个新的trigger
					trigger = TriggerBuilder.newTrigger()
							.withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();

					scheduler.scheduleJob(jobDetail, trigger);
				} else {
					// Trigger已存在，那么更新相应的定时设置
					// 表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
							.cronSchedule(job.getCronExpression());

					// 按新的cronExpression表达式重新构建trigger
					trigger = trigger.getTriggerBuilder()
							.withIdentity(triggerKey)
							.withSchedule(scheduleBuilder).build();

					// 按新的trigger重新设置job执行
					scheduler.rescheduleJob(triggerKey, trigger);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SchedulerFactoryBean getSchedulerFactoryBean() {
		return schedulerFactoryBean;
	}

	public void setSchedulerFactoryBean(
			SchedulerFactoryBean schedulerFactoryBean) {
		this.schedulerFactoryBean = schedulerFactoryBean;
	}
}
