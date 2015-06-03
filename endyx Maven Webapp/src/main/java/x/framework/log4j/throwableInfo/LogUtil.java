package x.framework.log4j.throwableInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LocationInfo;

public class LogUtil {

	private static Log log =LogFactory.getLog(LogUtil.class);
	
	public static void main(String []msg){
		
		Exception e = new Exception();
		LocationInfo i=new LocationInfo(e, "java.lang.Integer");
		
	}
	
	
}
