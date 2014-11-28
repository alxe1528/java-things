package t.net.mina.ssap.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import t.net.mina.nio.codec.IDecoder;
import t.net.mina.nio.msg.IMsg;
import t.net.mina.ssap.msg.SSAPMsg;
import t.net.mina.ssap.msg.SSAPMsgHead;
import t.net.mina.ssap.util.Constance;



/**
 * SSAP protocol 解码器.
 * 
 * @author lishuisheng
 *
 */
public class SSAPDefaultDecoder implements IDecoder {
	
	CharsetDecoder decoder=Charset.defaultCharset().newDecoder();

	/**
	 * 暂时返回OK，以后加上验证.
	 */
	public MessageDecoderResult decodeable(IoSession session, ByteBuffer buffer) {
		return MessageDecoderResult.OK;
	}

	/**
	 * 消息解码.
	 */
	public IMsg decode(IoSession session, ByteBuffer buffer) {
		SSAPMsg msg=new SSAPMsg();
		SSAPMsgHead head=decodeHead(session,buffer);
		msg.setHead(head);
		
		return null;
	}
	
	/**
	 * 头部解码.
	 * 
	 * @param session
	 * @param buffer
	 * @return
	 */
	public SSAPMsgHead decodeHead(IoSession session,ByteBuffer buffer){
		SSAPMsgHead head=new SSAPMsgHead();
		//解码首部.
		byte version=buffer.get();
		byte serice=buffer.get();
		byte type=buffer.get();
		byte[] codebyte=new byte[Constance.CODE_LENGTH];
		buffer.get(codebyte);
		String code=new String(codebyte);
		//设置消息头对象相应值.
		head.setVersion(version);
		head.setSerice(serice);
		head.setType(type);
		head.setCode(code);
		return head;
	}
	
	/**
	 * 消息体解码.
	 * 
	 * @param msg
	 * @param buffer
	 * @return
	 */
	public IMsg decodeBody(IMsg msg,ByteBuffer buffer){
		byte[] testbodybyte=new byte[Constance.TEST_BODY_LENGTH];
		buffer.get(testbodybyte);
		String test=new String(testbodybyte);
		System.out.println(test);
		return null;
	}
	
	

}
