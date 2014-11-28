package t.net.mina.http.handle;

import t.net.mina.nio.handle.IHandler;
import t.net.mina.nio.handle.IHandlerFactory;
import t.net.mina.nio.msg.IMsg;

/**
 * Http 请求处理工厂.
 * 
 * @author lishuisheng
 *
 */
public class HttpHandlerFactory implements IHandlerFactory {

	public IHandler getHandler(IMsg msg) {
		return new HttpHandler();
	}

}
