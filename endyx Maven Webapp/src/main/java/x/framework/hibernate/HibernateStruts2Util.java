package x.framework.hibernate;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author 郑健成
 * @created 创建时间：2012-4-9 下午10:31:58
 */

public class HibernateStruts2Util {

	private static final Logger LOG = Logger.getLogger(HibernateStruts2Util.class);

	public static final ThreadLocal MAP = new ThreadLocal();
	
	private static final SessionFactory SESSION_FACTORY;


	private HibernateStruts2Util() {
	}

	static {
		try {
			LOG.debug("HibernateUtil.static - loading config");
			ApplicationContext  context= WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			SESSION_FACTORY = (SessionFactory) context.getBean("sessionFactory");
			LOG.debug("HibernateUtil.static - end");
		} catch (HibernateException ex) {
			LOG.debug(ex.getMessage(),ex);
			throw new RuntimeException("Exception building SessionFactory: "+ ex.getMessage(), ex);
		}
	}


	public static Session currentSession() throws HibernateException {
	
		Session s = (Session) MAP.get();
		
		if(s!=null){//调试
			System.err.println(" connnect :"+s.isConnected());
			System.err.println("is open :"+s.isOpen());
		}
		
		if (s == null || !s.isConnected() || !s.isOpen()) {
			System.err.println("open session...");
			s = SESSION_FACTORY.openSession();
			MAP.set(s);
		}
		
		return s;

	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) MAP.get();
		MAP.set(null);
		if (s != null) {
			s.close();
		}
	}
}
