package t.net.mina.http.handle;

import org.apache.mina.common.IoSession;

import t.net.mina.http.msg.HttpRequestHead;
import t.net.mina.http.msg.HttpRequestMsg;
import t.net.mina.nio.handle.IHandler;
import t.net.mina.nio.msg.IMsg;

/**
 * Http 请求处理器.
 * 
 * @author lishuisheng
 *
 */
public class HttpHandler implements IHandler {

	public void handle(IoSession session,IMsg msg) {
		HttpRequestMsg message=(HttpRequestMsg)msg;
		HttpRequestHead head=message.getHead();
		System.out.println(head.toString());
	}

}
