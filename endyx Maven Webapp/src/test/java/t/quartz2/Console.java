package t.quartz2;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class Console {
	public static void main(String[] args) {
		try {
			testJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试使用quartz实现的调度任务
	public static void testJob() throws SchedulerException,
			InterruptedException {
		// 创建调度者工厂
		SchedulerFactory sfc = new StdSchedulerFactory();
		// 通过工厂创建一个调度者
		Scheduler scheduler = sfc.getScheduler();
		/*
		 * //----------Quartz1.8.4的写法---------------//
		 * 
		 * // 创建一个任务，命名“myjob”，组名“group1”，对应工作类“ExampleJob” JobDetail myJob =
		 * new JobDetail("myjob", "group1", ExampleJob.class); //
		 * 使用触发器工具类创建一个每隔15秒执行一次的触发器 Trigger trigger =
		 * TriggerUtils.makeSecondlyTrigger(15); trigger.setName("mytrigger");
		 * trigger.setStartTime(new Date());
		 */
		// ----------Quartz 2.0.2的写法---------------//
		JobDetail myJob = newJob(HelloJob.class).withIdentity("myJob",
				"job-group").build();
		Trigger trigger = newTrigger()
				.withIdentity("mytrigger", "trigger-group")
				.startAt(new Date())
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(15)
								.repeatForever()).build(); // 调度任务
		Date startDate = scheduler.scheduleJob(myJob, trigger);
		System.out.println(myJob.getKey() + " will start at:"
				+ startDate.toLocaleString());
		// 开始运行调度程序
		scheduler.start();
		Thread.sleep(20000);// 等待20秒
		scheduler.shutdown();// 关闭调度程序
		SchedulerMetaData metaData = scheduler.getMetaData();
		System.out.println("Executed " + metaData.getNumberOfJobsExecuted()
				+ " jobs.");
		System.out.println("Test end------>");
	}
}
