package t.net.mina.http.codec;

import t.net.mina.nio.codec.ICodecFactory;
import t.net.mina.nio.codec.IDecoder;
import t.net.mina.nio.codec.IEncoder;

/**
 * Http 协议编解码工厂.
 * 
 * @author lishuisheng
 *
 */
public class HttpCodecFactory implements ICodecFactory {

	public IDecoder[] getDecoders() {
		return new IDecoder[]{
				new HttpRequestDecoder()
		};
	}

	public IEncoder[] getEncoders() {
		return null;
	}

}
