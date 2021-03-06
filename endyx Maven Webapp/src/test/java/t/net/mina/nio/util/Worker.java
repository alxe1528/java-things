package t.net.mina.nio.util;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import t.net.mina.nio.handle.IHandler;
import t.net.mina.nio.msg.IMsg;

/**
 * 工作进程.
 * 
 * @author lishuisheng
 *
 */
public class Worker implements Runnable {
	
	private static Logger log=LoggerFactory.getLogger(Worker.class);
	
	private IHandler handler;
	
	private IoSession session;
	
	private IMsg msg;
	
	public Worker(IoSession session,IHandler handler,IMsg msg){
		this.session=session;
		this.handler=handler;
		this.msg=msg;
	}

	public void run() {
		try{
			handler.handle(session,msg);
		}catch(Exception e){
			e.printStackTrace();
			log.debug("Work work faile");
		}
	}

}
