package t.net.mina.ssap;

import t.net.mina.nio.ServerThread;
import t.net.mina.ssap.codec.SSAPCodecFactory;
import t.net.mina.ssap.handler.SSAPHandlerFactory;

/**
 * SSAP 服务启动类.
 * 
 * @author lishuisheng
 *
 */
public class SSAPServer {
	
	public static void main(String arg[]){
		SSAPCodecFactory codecFactory=new SSAPCodecFactory();
		SSAPHandlerFactory handlerFactory=new SSAPHandlerFactory();
		
		ServerThread st=new ServerThread(codecFactory,handlerFactory);
		st.setPort(1984);
		st.startServer();
	}
}
