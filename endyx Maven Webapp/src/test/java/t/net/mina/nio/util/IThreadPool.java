package t.net.mina.nio.util;

/**
 * 线程池接口.
 * 
 * @author lishuisheng
 *
 */
public interface IThreadPool {
	
	public void setMaxThread(int maxThread);
	
	public int getMaxThread();
	
	public void setMinThread(int minThread);
	
	public int getMinThread();
	
	public void setQueueSize(int queueSize);
	
	public int getQueueSize();
	
	public void initThread();
	
	public void addWorker(Worker worker);
	
	public Worker getWorker();

}
