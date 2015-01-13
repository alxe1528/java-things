package t.jdk.thread.uncaughtExceptionHandler;

class Task implements Runnable {
	public void run() {
		Thread.currentThread().setUncaughtExceptionHandler(
				new ExceptionHandler());
		System.out.println(Integer.parseInt("123"));
		System.out.println(Integer.parseInt("234"));
		System.out.println(Integer.parseInt("345"));
		System.out.println(Integer.parseInt("XYZ")); // This will cause
														// NumberFormatException
		System.out.println(Integer.parseInt("456"));
	}
}