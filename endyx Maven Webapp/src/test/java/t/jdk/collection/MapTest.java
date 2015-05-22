package t.jdk.collection;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MapTest {
	public static void main(String[] args) {
		Map<Object, Object> m = new Hashtable<Object, Object>();
		m.put("dd", "sdsd");
		//System.out.println(m.get("dd"));
		
		
		Set<Object> s= new TreeSet();
		s.add(new Object());
	}
}