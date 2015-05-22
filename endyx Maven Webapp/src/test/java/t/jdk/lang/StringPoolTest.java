package t.jdk.lang;
public class StringPoolTest {
 
	//使用visualVM  看看 分别在jdk6、7中 内存的变化 
    public void testStringPoolWithLongString(){
        long i=0;
        while(true){
            String longString = "This is a very long string, very very long string to test the gc behavior of the string constant pool"+i;
            longString.intern();
            i++;
        }
    }
 
    public static void main(String[] args){
        StringPoolTest stringPoolTest = new StringPoolTest();
        stringPoolTest.testStringPoolWithLongString();
    }
}
