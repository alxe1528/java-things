package t.net.mina.ssap.codec;

import t.net.mina.nio.codec.ICodecFactory;
import t.net.mina.nio.codec.IDecoder;
import t.net.mina.nio.codec.IEncoder;

/**
 * SSAP Protocol 编解码工厂.
 * 
 * @author ibm t42
 *
 */
public class SSAPCodecFactory implements ICodecFactory {

	public IDecoder[] getDecoders() {
		
		return new IDecoder[]{
				new SSAPDefaultDecoder()
		};
	}

	public IEncoder[] getEncoders() {
		return null;
	}

}
