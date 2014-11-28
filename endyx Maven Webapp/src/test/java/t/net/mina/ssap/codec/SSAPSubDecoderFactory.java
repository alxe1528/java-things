package t.net.mina.ssap.codec;

import t.net.mina.ssap.msg.SSAPMsg;

/**
 * SSAP 各消息解码工厂.
 * 
 * @author lishuisheng
 *
 */
public class SSAPSubDecoderFactory {
	
	/**
	 * 单例.
	 */
	private static SSAPSubDecoderFactory instance=new SSAPSubDecoderFactory();
	
	/**
	 * 构造仔.
	 */
	private SSAPSubDecoderFactory(){
		
	}
	
	/**
	 * 返回实例.
	 * 
	 * @return
	 */
	public static SSAPSubDecoderFactory getInstance(){
		return instance;
	}
	
	public ISSAPSubDecoder decode(SSAPMsg msg){
		
		return null;
	}
	
	

}
