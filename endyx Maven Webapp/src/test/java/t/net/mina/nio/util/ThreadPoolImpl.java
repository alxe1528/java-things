package t.net.mina.nio.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池实现类.
 * 
 * @author lishuisheng
 * 
 */
public class ThreadPoolImpl implements IThreadPool {

	private int maxThread = 20;

	private int minThread = 10;

	private int timeOut = 3;

	private int queueSize = 10;

	private boolean started = false;

	private ThreadPoolExecutor workThreads;

	public int getMaxThread() {
		return maxThread;
	}

	public int getMinThread() {
		return minThread;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setMaxThread(int maxThread) {
		this.maxThread = maxThread;
	}

	public void setMinThread(int minThread) {
		this.minThread = minThread;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public void addWorker(Worker worker) {
		workThreads.execute(worker);
	}

	public Worker getWorker() {
		return null;
	}

	public void initThread() {
		if (started) {
			return;
		}
		ArrayBlockingQueue<Runnable> abq = new ArrayBlockingQueue<Runnable>(
				queueSize);
		workThreads = new ThreadPoolExecutor(minThread, maxThread, timeOut,
                TimeUnit.SECONDS, abq,
                new ThreadPoolExecutor.DiscardOldestPolicy());
		started=true;
	}

}
