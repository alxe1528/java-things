package t.jdk.thread;
public class InterruptWait extends Thread {
    public static Object lock = new Object();
 
    @Override
    public void run() {
        System.out.println("start");
        synchronized (lock) {
            try {
            	System.out.println("wait~前");
                lock.wait();
                System.out.println("wait~后");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt(); // set interrupt flag again
                System.out.println(Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        }
    }
 
    public static void main(String[] args) {
        Thread thread = new InterruptWait();
        thread.start();
        try {
        	System.out.println("sleep前");
            sleep(2000);
            System.out.println("sleep后");
        } catch (InterruptedException e) {
        	System.out.println("InterruptedException in main");
        	e.printStackTrace();
        }
        System.out.println("thread.interrupt() 前");
        thread.interrupt();
        System.out.println("thread.interrupt() 后:"+thread.isInterrupted());
        System.out.println("thread.interrupt() 后:"+thread.isInterrupted());
        System.out.println("thread.interrupt() 后:"+thread.isInterrupted());
        System.out.println("thread.interrupt() 后:"+thread.isInterrupted());
        System.out.println("thread.interrupt() 后:"+thread.isInterrupted());
        
    }
}