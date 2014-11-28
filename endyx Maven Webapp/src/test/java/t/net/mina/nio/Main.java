package t.net.mina.nio;

/**
 * 主类，启动服务线程.
 * 
 * @author lishuisheng
 *
 */
public class Main {
	
	public static void main(String arg[]){
		ServerThread server=new ServerThread();
		server.startServer();
	}

}
