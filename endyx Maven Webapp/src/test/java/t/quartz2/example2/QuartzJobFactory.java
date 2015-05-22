package t.quartz2.example2;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 这里我们实现的是无状态的Job，如果要实现有状态的Job在以前是实现StatefulJob接口，在我使用的quartz
 * 2.2.1中，StatefulJob接口已经不推荐使用了，
 * 换成了注解的方式，只需要给你实现的Job类加上注解@DisallowConcurrentExecution即可实现有状态：
 */

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("任务成功运行");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
	}
}