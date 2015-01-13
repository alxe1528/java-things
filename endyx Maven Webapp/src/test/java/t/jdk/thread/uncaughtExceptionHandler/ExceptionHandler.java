package t.jdk.thread.uncaughtExceptionHandler;

import java.lang.Thread.UncaughtExceptionHandler;

class ExceptionHandler implements UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		System.out.printf("An exception has been capturedn");
		System.out.printf("Thread: %sn", t.getId());
		System.out.printf("Exception: %s: %sn", e.getClass().getName(),
				e.getMessage());
		System.out.printf("Stack Trace: n");
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %sn", t.getState());
		new Thread(new Task()).start();
	}
}