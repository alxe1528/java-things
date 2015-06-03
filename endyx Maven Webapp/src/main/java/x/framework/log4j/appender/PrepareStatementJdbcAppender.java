package x.framework.log4j.appender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 支持预编译sql的jdbc输出器<br>
 * 设置参数：<br>
 * URL、driver、user、password、sql、sqlParams 、[bufferSize]<br>
 * 不需要设置layout
 * 
 * @author houfeng-win7
 * 
 */
public class PrepareStatementJdbcAppender extends
		org.apache.log4j.AppenderSkeleton implements org.apache.log4j.Appender {

	protected Connection connection = null;

	protected String driverClass;
	protected String databaseURL = "jdbc:mysql://localhost:3306/test";
	protected String databaseUser = "root";
	protected String databasePassword = "xiaodi";

	/**
	 *  预编译sql //insert into tab values (?,?)
	 */
	protected String sqlStatement = "";
 
	/**
	 * sql参数 有序字符串 <br>
	 * 不要有任何多余字符。
	 * 格式范例：%p%m%c%d{yyyy-MM-dd HH:mm:ss}
	 */
	protected String sqlParams = "";
	/**
	 * 存储当前sql参数值
	 */
	protected ArrayList sqlParamValues ;

	protected int bufferSize = 1;
	protected ArrayList buffer;
	protected ArrayList removes;

	/**
	 * sqlParams模式转换器
	 */
	protected PatternConverter patternConverter;


	public PrepareStatementJdbcAppender() {
		this.buffer = new ArrayList(this.bufferSize);
		this.removes = new ArrayList(this.bufferSize);
	}

	public void append(LoggingEvent event) {

		if (!checkEntryConditions())
			return;

		this.buffer.add(event);

		if (this.buffer.size() >= this.bufferSize)
			flushBuffer();
	}

	/**
	 * 检测必需参数是否设置 
	 * @return
	 */
	protected boolean checkEntryConditions() {
		if(this.driverClass == null){
			this.errorHandler.error("No 'driver' set for the appender named [" + name + "]");
			return false;
		}
		if(this.databaseURL == null){
			this.errorHandler.error("No 'URL' set for the appender named [" + name + "]");
			return false;
		}
		if(this.databaseUser == null){
			this.errorHandler.error("No 'user' set for the appender named [" + name + "]");
			return false;
		}
		if(this.databasePassword == null){
			this.errorHandler.error("No 'password' set for the appender named [" + name + "]");
			return false;
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

	protected void execute()  {
		String sql = this.sqlStatement; 
		ArrayList list = this.sqlParamValues;
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			try {
				con = getConnection(); 
				stmt = con.prepareStatement(sql);
				for(int i=0;i<list.size();i++){
					stmt.setString(i+1, list.get(i).toString());
				} 
				stmt.executeUpdate(); 
			} finally{
				stmt.close();
				closeConnection(con); 
			}
		} catch (SQLException e) {
			errorHandler.error("Error closing connection", e, ErrorCode.GENERIC_FAILURE);
		} 
	}

	protected void closeConnection(Connection con) {
		// 不立即关闭，保持jdbc连接而不立即释放，保证执行效率，在log销毁时通过close方法释放。
		// 该方法在数据源连接的时候可增加关闭连接的代码。
	}

	protected Connection getConnection() throws SQLException {
		if (this.connection == null||this.connection.isClosed()) {
			if (!(DriverManager.getDrivers().hasMoreElements())) {
				setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
			}
			
			this.connection = DriverManager.getConnection(this.databaseURL,
					this.databaseUser, this.databasePassword);
		}

		return this.connection;
	}

	public void close() {
		flushBuffer();
		try {
			if ((this.connection != null) && (!(this.connection.isClosed())))
				this.connection.close();
		} catch (SQLException e) {
			this.errorHandler.error("Error closing connection", e, 0);
		}
		this.closed = true;
	}

	public void flushBuffer() {
		this.removes.ensureCapacity(this.buffer.size());
		for (Iterator i = this.buffer.iterator(); i.hasNext();) {
			 
				LoggingEvent logEvent = (LoggingEvent) i.next();
				StringBuffer tempBuf = new StringBuffer();
				 
				for(PatternConverter c = this.patternConverter;c!=null;c=c.next){
					c.format(tempBuf, logEvent);
					this.sqlParamValues.add(tempBuf.toString());
					tempBuf.setLength(0);
				} 
				execute();
				this.sqlParamValues.clear();
				
				this.removes.add(logEvent);  
		}

		this.buffer.removeAll(this.removes); 
		this.removes.clear();
	}

	public boolean requiresLayout() {
		return false;// 不需要Layout
	}

	public void setSql(String s) {
		 
		this.sqlStatement = s;
		//if (super.getLayout() == null) super.setLayout(new PatternLayout(s));
		//else ((PatternLayout) super.getLayout()).setConversionPattern(s);
	}

	public String getSql() {
		return this.sqlStatement;
	}

	public void setSqlParams(String s) {
	 
		this.sqlParams = s.trim();
		this.patternConverter = new PatternParser(this.sqlParams).parse();
		this.sqlParamValues = new ArrayList();
	}

	public String getSqlParams() {
		return this.sqlParams;
	}

	public void setUser(String user) {
		this.databaseUser = user;
	}

	public void setURL(String url) {
		this.databaseURL = url;
	}

	public void setPassword(String password) {
		this.databasePassword = password;
	}

	public void setBufferSize(int newBufferSize) {
		this.bufferSize = newBufferSize;
		this.buffer.ensureCapacity(this.bufferSize);
		this.removes.ensureCapacity(this.bufferSize);
	}

	public String getUser() {
		return this.databaseUser;
	}

	public String getURL() {
		return this.databaseURL;
	}

	public String getPassword() {
		return this.databasePassword;
	}

	public int getBufferSize() {
		return this.bufferSize;
	}

	public void setDriver(String driverClass) {
		this.driverClass = driverClass;
		try {
			Class.forName(this.driverClass);
		} catch (Exception e) {
			this.errorHandler.error("Failed to load driver", e, 0);
		}
	}
}