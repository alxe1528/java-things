package t.jdk.thread.uncaughtExceptionHandler;


/**
 * 使用UncaughtExceptionHandler重启线程
 * @author Endy
 */
public class DemoThreadExample {
	public static void main(String[] args) {
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.start();
	}
}