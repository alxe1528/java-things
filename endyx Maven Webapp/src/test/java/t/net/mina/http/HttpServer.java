package t.net.mina.http;

import t.net.mina.http.codec.HttpCodecFactory;
import t.net.mina.http.handle.HttpHandlerFactory;
import t.net.mina.nio.ServerThread;

/**
 * Http Server 启动类.
 * 
 * @author lishuisheng
 *
 */
public class HttpServer {
	
	private static final int PORT=80;
	
	public static void main(String arg[]){
		HttpCodecFactory codecFactory=new HttpCodecFactory();
		HttpHandlerFactory handlerFactory=new HttpHandlerFactory();
		ServerThread st=new ServerThread(codecFactory,handlerFactory);
		st.setPort(PORT);
		st.startServer();
	}

}
