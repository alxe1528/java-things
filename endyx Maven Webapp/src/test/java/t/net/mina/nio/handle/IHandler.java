package t.net.mina.nio.handle;

import org.apache.mina.common.IoSession;

import t.net.mina.nio.msg.IMsg;

/**
 * 处理器接口.
 * 
 * @author lishuisheng
 *
 */
public interface IHandler {
	
	public void handle(IoSession session,IMsg message);

}
