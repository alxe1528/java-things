package t.quartz2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("t/quartz2/applicationContext.xml");
	}
}
