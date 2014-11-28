package t.net.mina.ssap.msg;

/**
 * SSAP 消息首部.
 * 
 * @author lishuisheng
 *
 */
public class SSAPMsgHead {
	
	private byte version;
	
	private byte serice;
	
	private byte type;
	
	private String code;
	
	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getSerice() {
		return serice;
	}

	public void setSerice(byte serice) {
		this.serice = serice;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
