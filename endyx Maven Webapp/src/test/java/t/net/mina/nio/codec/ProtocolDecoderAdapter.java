package t.net.mina.nio.codec;

import org.apache.log4j.Logger;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import t.net.mina.nio.msg.IMsg;

/**
 * 解码器适配器.
 * 
 * @author lishuisheng
 *
 */
public class ProtocolDecoderAdapter implements MessageDecoder {
	
	private static Logger log=Logger.getLogger(ProtocolDecoderAdapter.class);
	
	private IDecoder decoder;
	
	
	public ProtocolDecoderAdapter(IDecoder decoder){
		this.decoder=decoder;
	}

	public MessageDecoderResult decodable(IoSession session, ByteBuffer buffer) {
		return decoder.decodeable(session, buffer);
	}

	public MessageDecoderResult decode(IoSession session, ByteBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
		log.info("....decoder ready to decode protocol...");
		MessageDecoderResult result=MessageDecoderResult.OK;
		try{
			IMsg msg=decoder.decode(session, buffer);
			if(msg!=null){
				out.write(msg);
			}else{
				result=MessageDecoderResult.NOT_OK;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		
	}

}
