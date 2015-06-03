package t.spring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class DestructiveBeanWithJSR250 {
	private InputStream is = null;
	public String filePath = null;

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		System.out.println("Initializing Bean");
		if (filePath == null) {
			throw new IllegalArgumentException(
					"You must specify the filePath property of "
							+ DestructiveBeanWithJSR250.class);
		}
		is = new FileInputStream(filePath);
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Destroying Bean");
		if (is != null) {
			try {
				is.close();
				is = null;
			} catch (IOException ex) {
				System.err.println("WARN: An IOException occured"
						+ " trying to close the InputStream");
			}
		}
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}