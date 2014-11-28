package t.net.mina.http.msg;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http 请求头.
 * 
 * @author lishuisheng
 * 
 */
public class HttpRequestHead {

	private Map heads=null;

	

	public Map getHeads() {
		return heads;
	}

	public void setHeads(Map heads) {
		this.heads = heads;
	}

	public String getContext() {
		String[] context = (String[]) heads.get("Context");
		return context == null ? "" : context[0];
	}

	public String getParameter(String name) {
		String[] param = (String[]) heads.get("#".concat(name));
		return param == null ? "" : param[0];
	}

	public String[] getParameters(String name) {
		String[] param = (String[]) heads.get("#".concat(name));
		return param == null ? new String[] {} : param;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		Iterator it = heads.entrySet().iterator();
		while (it.hasNext()) {
			Entry e = (Entry) it.next();
			str.append(e.getKey() + " : "
					+ arrayToString((String[]) e.getValue(), ',') + "\n");
		}
		return str.toString();
	}

	public String arrayToString(String[] s, char sep) {
		if (s == null || s.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if (s != null) {
			for (int i = 0; i < s.length; i++) {
				if (i > 0) {
					sb.append(sep);
				}
				sb.append(s[i]);
			}
		}
		return sb.toString();
	}

}
