package t.net.mina.nio.handle;

import org.apache.log4j.Logger;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

import t.net.mina.nio.msg.IMsg;
import t.net.mina.nio.util.IThreadPool;
import t.net.mina.nio.util.ThreadPoolImpl;
import t.net.mina.nio.util.Worker;

/**
 * 
 * @author lishuisheng
 *
 */
public class ProtocolHandlerAdapter extends IoHandlerAdapter {
	
	private static Logger log=Logger.getLogger(ProtocolHandlerAdapter.class);
	
	private IHandlerFactory handlerFactory;
	
	private IThreadPool threadPool;
	
	public ProtocolHandlerAdapter(IHandlerFactory handlerFactory){
		this.handlerFactory=handlerFactory;
		threadPool=new ThreadPoolImpl();
		threadPool.initThread();
	}
	
	public void messageReceived(IoSession session, Object message)throws Exception{
		log.info("...receive a/an message...");
		IMsg msg=(IMsg)message;
		if(handlerFactory==null){
			log.debug("not have handlerFactory!");
		}else{
			IHandler handler=handlerFactory.getHandler(msg);
			if(handler==null){
				log.debug("not have handler!");
			}else{
				Worker worker=new Worker(session,handler,msg);
				threadPool.addWorker(worker);
			}
		}
		
	}

}
