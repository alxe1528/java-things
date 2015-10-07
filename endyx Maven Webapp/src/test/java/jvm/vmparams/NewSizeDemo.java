package jvm.vmparams;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xmx20m -Xms20m -Xmn1m -XX:SurvivorRaio=2 -XX:+PrintGCDetails
 * @author Endy
 *
 */
public class NewSizeDemo {

	public static void main(String[] args) {
		byte[] b= null;
		List l = new ArrayList();
		for(int i=0;i<19;i++){
			b=new byte[1*1024*1024];
			l.add(b);
		}
		/*for(int i = 0 ; i<24;i++){
			System.out.println(l.get(i));
		}*/
	}
	
}
