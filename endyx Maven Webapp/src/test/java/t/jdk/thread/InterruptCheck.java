package t.jdk.thread;
public class InterruptCheck extends Thread {
 
    @Override
    public void run() {
        System.out.println("start");
        while (true){
        	//System.out.println("继续");
        }
           /* if (Thread.currentThread().isInterrupted())
                break;*/
        //System.out.println("while exit");
    }
 
    public static void main(String[] args) {
        Thread thread = new InterruptCheck();
        thread.start();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        thread.interrupt();
        
        System.out.println("中断拉："+thread.isInterrupted());
    }
}