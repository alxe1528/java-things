package t.net.mina.nio.codec;

/**
 * 编解码工厂接口.
 * 
 * @author lishuisheng
 *
 */
public interface ICodecFactory {
	
	public IEncoder[] getEncoders();
	
	public IDecoder[] getDecoders();

}
