package x.web.http.header;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETagContentFilter implements Filter {
	private static final Logger logger = LoggerFactory
			.getLogger(ETagContentFilter.class);

	public void destroy() {
	}

	/**
	 * public void doFilter2(ServletRequest req, ServletResponse res,
	 * FilterChain chain) throws IOException, ServletException {
	 * HttpServletRequest servletRequest = (HttpServletRequest) req;
	 * HttpServletResponse servletResponse = (HttpServletResponse) res;
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * //ETagResponseWrapper wrappedResponse = new
	 * ETagResponseWrapper(servletResponse, baos);
	 * chain.doFilter(servletRequest, wrappedResponse);
	 * 
	 * byte[] bytes = baos.toByteArray(); String token = '"' +
	 * ETagComputeUtils.getMd5Digest(bytes) + '"';
	 * servletResponse.setHeader("ETag", token); // always store the ETag in the
	 * header
	 * 
	 * String previousToken = servletRequest.getHeader("If-None-Match"); if
	 * (previousToken != null && previousToken.equals(token)) { // compare
	 * previous token with current one
	 * logger.debug("ETag match: returning 304 Not Modified");
	 * servletResponse.sendError(HttpServletResponse.SC_NOT_MODIFIED); // use
	 * the same date we sent when we created the ETag the first time through
	 * servletResponse.setHeader("Last-Modified",
	 * servletRequest.getHeader("If-Modified-Since")); } else { // first time
	 * through - set last modified time to now Calendar cal =
	 * Calendar.getInstance(); cal.set(Calendar.MILLISECOND, 0); Date
	 * lastModified = cal.getTime();
	 * servletResponse.setDateHeader("Last-Modified", lastModified.getTime());
	 * 
	 * logger.debug("Writing body content");
	 * servletResponse.setContentLength(bytes.length); ServletOutputStream sos =
	 * servletResponse.getOutputStream(); sos.write(bytes); sos.flush();
	 * sos.close(); } }
	 **/

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponseWrapper hsrw = new MyHttpResponseWrapper(
				servletResponse, baos);

		chain.doFilter(request, hsrw);

		hsrw.flushBuffer();

		byte[] bytes = baos.toByteArray();

		CRC32 crc = new CRC32();
		crc.update(bytes);

		String token = "w/\"" + crc.getValue() + '"';
		servletResponse.setHeader("ETag", token);
		// always store the ETag in the header
		String previousToken = servletRequest.getHeader("If-None-Match");
		if (previousToken != null && previousToken.equals(token)) {
			// compare previous token with current one

			System.out.println("ETag match: returning 304 Not Modified");
			servletResponse.sendError(HttpServletResponse.SC_NOT_MODIFIED);
			// use the same date we sent when we created the ETag the first time
			// through
			servletResponse.setHeader("Last-Modified",
					servletRequest.getHeader("If-Modified-Since"));
		} else {
			// first time through - set last modified time to now
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			Date lastModified = cal.getTime();
			servletResponse.setDateHeader("Last-Modified",
					lastModified.getTime());
			System.out.println("Writing body content");
			servletResponse.setContentLength(bytes.length);
			ServletOutputStream sos = servletResponse.getOutputStream();
			sos.write(bytes);
			sos.flush();
			sos.close();
		}

	}

	private static class MyHttpResponseWrapper extends
			HttpServletResponseWrapper {

		ByteServletOutputStream servletOutputStream;
		PrintWriter printWriter;

		public MyHttpResponseWrapper(HttpServletResponse response,
				ByteArrayOutputStream buffer) {
			super(response);
			servletOutputStream = new ByteServletOutputStream(buffer);
		}

		public ServletOutputStream getOutputStream() throws IOException {
			return servletOutputStream;
		}

		public PrintWriter getWriter() throws IOException {
			if (printWriter == null) {
				printWriter = new PrintWriter(servletOutputStream);
			}
			return printWriter;
		}

		public void flushBuffer() throws IOException {
			servletOutputStream.flush();
			if (printWriter != null) {
				printWriter.flush();
			}
		}
	}

	private static class ByteServletOutputStream extends ServletOutputStream {

		ByteArrayOutputStream baos;

		public ByteServletOutputStream(ByteArrayOutputStream baos) {
			super();
			this.baos = baos;
		}

		public void write(int b) throws IOException {
			baos.write(b);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
