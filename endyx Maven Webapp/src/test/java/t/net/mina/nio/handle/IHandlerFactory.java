package t.net.mina.nio.handle;

import t.net.mina.nio.msg.IMsg;


/**
 * 处理器工厂接口.
 * 
 * @author lishuisheng
 *
 */
public interface IHandlerFactory {
	
	public IHandler getHandler(IMsg msg);
	

}
