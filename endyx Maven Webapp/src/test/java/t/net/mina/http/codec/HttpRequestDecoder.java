package t.net.mina.http.codec;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import t.net.mina.http.msg.HttpRequestHead;
import t.net.mina.http.msg.HttpRequestMsg;
import t.net.mina.nio.codec.IDecoder;
import t.net.mina.nio.msg.IMsg;

/**
 * Http 请求解码器.
 * 
 * @author lishuisheng
 * 
 */
public class HttpRequestDecoder implements IDecoder {

	private static final byte[] CONTENT_LENGTH = new String("Content-Length:")
			.getBytes();
	
	private CharsetDecoder cd=Charset.defaultCharset().newDecoder();

	public IMsg decode(IoSession session, ByteBuffer buffer) {
		HttpRequestMsg msg=parseRequest(buffer);
		return msg;
	}
	
	/**
	 * 解码HTTP请求.
	 * 
	 * @param in
	 * @return
	 */
	public HttpRequestMsg parseRequest(ByteBuffer in){
		HttpRequestMsg msg=new HttpRequestMsg();
		HttpRequestHead head=new HttpRequestHead();
		Map<String,String[]> map=new HashMap<String,String[]>();
		try{
			StringReader sr=new StringReader(in.getString(cd));
			BufferedReader br=new BufferedReader(sr);
			String line=br.readLine();
			String[] url=line.split(" ");
			if(url.length<3){
				msg.setHead(head);
				return msg;
			}
			map.put("URL", new String[]{line});
			map.put("Method", new String[]{url[0].toUpperCase()});
			map.put("Context", new String[]{url[1].substring(1)});
			map.put("Protocol", new String[]{url[2]});
			//读取请求头各字段并放到映射表里.
			while((line=br.readLine())!=null&&line.length()>0){
				String[] tokens=line.split(": ");
				map.put(tokens[0], new String[]{tokens[1]});
			}
			//判断请求方法"POST"或者"GET"分别截取请求内容.
			if(url[0].equals("POST")){
				int len=Integer.parseInt(map.get("Content-Length")[0]);
				char[] buf=new char[len];
				if(br.read(buf)==len){
					line=String.copyValueOf(buf);
				}
			}else if(url[0].equalsIgnoreCase("GET")){
				int idx=url[1].indexOf("?");
				if(idx!=-1){
					map.put("Context", new String[]{url[1].substring(1, idx)});
					line=url[1].substring(idx+1);
				}else{
					line=null;
				}
			}
			//获取请求内容.
			if(line!=null){
				String[] match=line.split("&");
				for(int i=0;i<match.length;i++){
					String[] tokens=match[i].split("=");
					String[] params=new String[1];
					switch(tokens.length){
					case 0:
						map.put("#".concat(match[i]), new String[]{});
						break;
					case 1:
						map.put("#".concat(tokens[0]), new String[]{});
						break;
					default:
						String key="#".concat(tokens[0]);
						if(map.containsKey(key)){
							params=map.get(key);
							String[] tmp=new String[params.length+1];
							for(int j=0;j<params.length;j++){
								tmp[j]=params[j];
							}
							params=null;
							params=tmp;
						}
						params[params.length-1]=tokens[1].trim();
						map.put(key, params);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//把请求头的各字段值映射表设置到头部对象.
		head.setHeads(map);
		msg.setHead(head);
		return msg;
	}

	/**
	 * 验证消息是否接收完整.
	 */
	public MessageDecoderResult decodeable(IoSession session, ByteBuffer buffer) {
		try {
			return messageComplete(buffer) ? MessageDecoderResult.OK
					: MessageDecoderResult.NEED_DATA;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageDecoderResult.NOT_OK;
	}

	public boolean messageComplete(ByteBuffer in) {
		int last = in.remaining() - 1;
		if (in.remaining() < 4) {
			return false;
		}
		// 判断请求类型.
		if (in.get(0) == (byte) 'G' && in.get(1) == (byte) 'E'
				&& in.get(2) == (byte) 'T') {
			return (in.get(last) == (byte) 0x0A
					&& in.get(last - 1) == (byte) 0x0D
					&& in.get(last - 2) == (byte) 0x0A && in.get(last - 3) == (byte) 0x0D);
		} else if (in.get(0) == (byte) 'P' && in.get(1) == (byte) 'O'
				&& in.get(2) == (byte) 'S' && in.get(3) == (byte) 'T') {
			int pos = -1;
			// 找到空行的位置.
			for (int i = last; i > 2; i--) {
				if (in.get(i) == (byte) 0x0A && in.get(i - 1) == (byte) 0x0D
						&& in.get(i - 2) == (byte) 0x0A
						&& in.get(i - 3) == (byte) 0x0D) {
					pos = i + 1;
					break;
				}
			}
			if (pos == -1) {
				return false;
			}
			for (int i = 0; i < last; i++) {
				boolean found = false;
				for (int j = 0; j < CONTENT_LENGTH.length; j++) {
					if (in.get(i + j) != CONTENT_LENGTH[j]) {
						found = false;
						break;
					}
					found = true;
				}
				if (found) {
					StringBuilder content_length = new StringBuilder();
					for (int j = i + CONTENT_LENGTH.length; j < last; j++) {
						if (in.get(j) == (byte) 0x0D) {
							break;
						}
						content_length.append(new String(
								new byte[] { in.get(j) }));
					}
					return Integer.parseInt(content_length.toString().trim())
							+ pos == in.remaining();
				}
			}
		}
		return false;
	}
	
	

}
