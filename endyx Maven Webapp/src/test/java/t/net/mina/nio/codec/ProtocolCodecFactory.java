package t.net.mina.nio.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编解码工厂.
 * 
 * @author lishuisheng
 *
 */
public class ProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	
	private static Logger log=LoggerFactory.getLogger(ProtocolCodecFactory.class);
	
	private ICodecFactory codecFactory;
	
	public ProtocolCodecFactory(ICodecFactory codecFactory){
		this.codecFactory=codecFactory;
		regisCodec();
	}
	
	public void regisCodec(){
		IEncoder[] encoders=codecFactory.getEncoders();
		if(encoders==null){
			log.debug("not have encoder...");
		}else{
			for(int i=0;i<encoders.length;i++){
				this.register(new ProtocolEncoderAdapter(encoders[i]));
			}
		}
		IDecoder[] decoders=codecFactory.getDecoders();
		if(decoders==null){
			log.debug("not have decoder...");
		}else{
			for(int j=0;j<decoders.length;j++){
				this.register(new ProtocolDecoderAdapter(decoders[j]));
			}
		}
		
	}
	

}
