package t.net.mina.http.msg;

import t.net.mina.nio.msg.IMsg;

/**
 * Http 请求消息对象.
 * 
 * @author lishuisheng
 *
 */
public class HttpRequestMsg implements IMsg {
	
	private HttpRequestHead head;
	
	public void setHead(HttpRequestHead head){
		this.head=head;
	}
	public HttpRequestHead getHead(){
		return head;
	}
	public byte getVersion() {
		return 0;
	}
	public void setVersion(byte version) {
		
	}

}
