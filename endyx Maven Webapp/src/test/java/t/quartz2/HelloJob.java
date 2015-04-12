package t.quartz2;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {

	private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

	public HelloJob() {

	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.print("I can count to 10 ->");
		// 输出1-10
		for (int i = 1; i <= 10; i++) {
			System.out.print(" | " + i + " ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
		}
		System.out.println("<- See I did it.");
		JobDataMap properties = context.getJobDetail().getJobDataMap();
		System.out.println("Previous Fire Time: "
				+ context.getPreviousFireTime());// 上次执行时间
		System.out.println("Current Fire Time: " + context.getFireTime());// 本次执行时间
		System.out.println("Next Fire Time: " + context.getNextFireTime());// 下一次执行时间

	}
}