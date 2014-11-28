package t.net.mina.nio;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import t.net.mina.nio.codec.DefaultCodecFactory;
import t.net.mina.nio.codec.ICodecFactory;
import t.net.mina.nio.codec.ProtocolCodecFactory;
import t.net.mina.nio.handle.DefaultHandlerFactory;
import t.net.mina.nio.handle.IHandlerFactory;
import t.net.mina.nio.handle.ProtocolHandlerAdapter;

/**
 * 服务启动线程.
 * 
 * @author lishuisheng
 *
 */
public class ServerThread implements Runnable {
	
	private static Logger log=Logger.getLogger(ServerThread.class);
	
	private ICodecFactory codecFactory;
	
	private IHandlerFactory handlerFactory;
	
	private Integer port;
	
	private static final int DEFAULT_PORT=8686;
	
	public ServerThread(){
		this(new DefaultCodecFactory(),new DefaultHandlerFactory());
	}
	
	public ServerThread(ICodecFactory codecFactory,IHandlerFactory handlerFactory){
		this.codecFactory=codecFactory;
		this.handlerFactory=handlerFactory;
	}
	
	public void setPort(int port){
		this.port=port;
	}
	
	public void startServer(){
		Thread t=new Thread(this);
		t.start();
	}

	public void run() {
		try{
			IoAcceptor acceptor=new SocketAcceptor();
			SocketAcceptorConfig config=new SocketAcceptorConfig();
			ProtocolCodecFactory codFactory=new ProtocolCodecFactory(codecFactory);
			ProtocolCodecFilter filter=new ProtocolCodecFilter(codFactory);
			
			config.getFilterChain().addLast("codec", filter);
			
			ProtocolHandlerAdapter handlerAdaptor=new ProtocolHandlerAdapter(handlerFactory);
			if(port==null){
				port=DEFAULT_PORT;
			}
			acceptor.bind(new InetSocketAddress(port), handlerAdaptor, config);
			log.info(".....mina nio server started...");
			log.info(".....listening at :"+port+"....");
		}catch(Exception e){
			log.debug("Server thread start faile!");
			e.printStackTrace();
		}
	}

}
