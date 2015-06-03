package x.framework.log4j.appender;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 使用JNDI数据源的 输出器<br>
 * 		由于JNDI数据源在log4j初始化前已经可用，所以可以通过配置的方式实现;
 * 或者在log4j中定义一个独立于程序和容器的数据源，也可以在配置中实现，但这样不利于数据源的统一维护。<br>
 * 相比，通过程序生成的数据源（包括spring配置的数据源）在log4j初始化的时候很可能还未创建，所以不能通过配置的方式定义这类
 * 数据源输出器，而只能在程序中创建Appender。<br>
 * 需要配置的参数:<br>
 * jndiName、 sql、sqlParams 、[bufferSize]<br>
 * @author houfeng-win7
 *
 */
public class JndiDataSourceAppender extends PrepareStatementJdbcAppender {
 
	protected DataSource ds = null;
	protected String jndiName = "java:comp/env/jdbc/mysql_log";
	
	public JndiDataSourceAppender(){  }
	
	public void setJndiName(String name){
		this.jndiName = name;
	}
	
	@Override
	protected boolean checkEntryConditions() { 
		
		if(this.jndiName == null){
			this.errorHandler.error("No 'jndiName' set for the appender named [" + name + "]");
			return  false;
		}
		if(this.sqlStatement == null){
			this.errorHandler.error("No 'sql' set for the appender named [" + name + "]");
			return false;
		}
		if (this.sqlParams == null || this.patternConverter == null) {
			this.errorHandler.error("No 'sqlParams' set for the appender named [" + name + "]");
			return false;
		}
		return true;
	}
	
	protected synchronized void init(){
		if(ds!=null) return;
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup(jndiName); 
		} catch (NamingException e) {
			this.errorHandler.error(e.getMessage());
		}
	}
	
	@Override
	protected Connection getConnection() throws SQLException {
		while(ds==null) init();
		
		Connection conn = ds.getConnection();
		conn.setAutoCommit(true);
		return conn;
	} 
	
	@Override
	protected void closeConnection(Connection conn) {
		try {
			if(conn!=null&&!conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			this.errorHandler.error(e.getMessage());
		}
	}
	
	
}