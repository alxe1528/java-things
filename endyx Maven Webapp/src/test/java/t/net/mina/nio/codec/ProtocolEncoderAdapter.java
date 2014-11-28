package t.net.mina.nio.codec;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import t.net.mina.nio.msg.IMsg;

/**
 * 编码器适配器.
 * 
 * @author lishuisheng
 *
 */
public class ProtocolEncoderAdapter implements MessageEncoder {
	
	private static Logger log=Logger.getLogger(ProtocolEncoderAdapter.class);
	
	private IEncoder encoder;
	
	private Set<Class<?>> types;
	
	public ProtocolEncoderAdapter(IEncoder encoder){
		this.encoder=encoder;
	}

	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out)
			throws Exception {
		log.info("..ready to encode message...");
		if(obj==null){
			log.debug("message is NULL encoder will be return");
			return;
		}
		if(!(obj instanceof IMsg)){
			log.debug("message obj not instanceof AbstractMessage encoder will be return");
			return;
		}
		IMsg msg=(IMsg)obj;
		ByteBuffer buff=encoder.encode(session, msg);
		out.write(buff);
	}

	public Set<Class<?>> getMessageTypes() {
		Class<?>[] classes=encoder.getMessageTypes();
		Set<Class<?>> ts=new HashSet<Class<?>>();
		for(int i=0;i<classes.length;i++){
			ts.add(classes[i]);
		}
		types=Collections.unmodifiableSet(ts);
		return types;
	}

}
