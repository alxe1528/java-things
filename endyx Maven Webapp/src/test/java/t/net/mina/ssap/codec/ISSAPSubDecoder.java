package t.net.mina.ssap.codec;

import t.net.mina.ssap.msg.SSAPMsg;

/**
 * SSAP 各消息解码接口.
 * 
 * @author lishuisheng
 *
 */
public interface ISSAPSubDecoder {
	
	public SSAPMsg decode(byte[] body);

}
