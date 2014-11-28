package t.net.mina.nio.codec;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import t.net.mina.nio.msg.IMsg;

/**
 * 解码器接口.
 * 
 * @author lishuisheng
 *
 */
public interface IDecoder {
	
	public IMsg decode(IoSession session,ByteBuffer buffer);

	public MessageDecoderResult decodeable(IoSession session,ByteBuffer buffer);
}
