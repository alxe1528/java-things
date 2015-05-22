package t.quartz2.example2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上下文的一些参数信息
 * 
 * User: liyd Date: 13-12-20 Time: 下午2:47
 */
public final class DataWorkContext {

	/**
	 * 计划任务map
	 */
	private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

	static {

		ScheduleJob job = new ScheduleJob();
		job.setJobId("10001");
		job.setJobName("contentPullConversionTask");
		job.setJobGroup("dataWork");
		job.setJobStatus("1");
		job.setCronExpression("0 0 2 * * ?");
		job.setDesc("数据导入任务");
		addJob(job);
	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 */
	public static void addJob(ScheduleJob scheduleJob) {
		jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(),
				scheduleJob);
	}

	/**
	 * 获取任务
	 * 
	 * @param jobId
	 * @return
	 */
	public static ScheduleJob getJob(String jobId) {
		return jobMap.get(jobId);
	}

	/**
	 * 获取所有任务
	 * 
	 * @return
	 */
	public static List getAllJob() {
		List jobList = new ArrayList(jobMap.size());
		for (Map.Entry entry : jobMap.entrySet()) {
			jobList.add(entry.getValue());
		}
		return jobList;
	}

}
