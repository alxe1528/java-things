package t.net.mina.nio.handle;

import t.net.mina.nio.msg.IMsg;


/**
 * 默认处理器工厂，没有相应的处理器.
 * @author lishuisheng
 *
 */
public class DefaultHandlerFactory implements IHandlerFactory {

	public IHandler getHandler(IMsg msg) {
		return null;
	}

}
