package t.net.mina.nio.codec;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;

/**
 * 编码器接口.
 * 
 * @author lishuisheng
 *
 */
public interface IEncoder {
	
	public Class[] getMessageTypes();
	
	public ByteBuffer encode(IoSession session,Object msg);

}
