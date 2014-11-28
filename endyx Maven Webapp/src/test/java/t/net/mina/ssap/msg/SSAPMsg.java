package t.net.mina.ssap.msg;

import t.net.mina.nio.msg.IMsg;

/**
 * SSAP 协议消息对象.
 * 
 * @author lishuisheng
 *
 */
public  class SSAPMsg implements IMsg {
	
	private SSAPMsgHead head;
	
	private byte[] body;

	
	
	public void setHead(SSAPMsgHead head){
		this.head=head;
	}
	public SSAPMsgHead getHead(){
		return head;
	}
	
	public  void setBody(byte[] body){
		this.body=body;
	}
	
	public byte[] getBody(){
		return body;
	}
	public byte getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setVersion(byte version) {
		// TODO Auto-generated method stub
		
	}

}
