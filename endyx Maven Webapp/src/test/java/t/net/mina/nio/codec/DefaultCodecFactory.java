package t.net.mina.nio.codec;

/**
 * 默认编解码工厂，没有编码器跟解码器.
 * 
 * @author lishuisheng
 *
 */
public class DefaultCodecFactory implements ICodecFactory {

	public IDecoder[] getDecoders() {
		return null;
	}

	public IEncoder[] getEncoders() {
		return null;
	}

}
