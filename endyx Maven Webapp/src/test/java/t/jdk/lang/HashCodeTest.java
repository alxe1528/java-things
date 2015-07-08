package t.jdk.lang;
public class HashCodeTest
{
    public static void main(String[] args)
    {
        String s = "hashCode";
        StringBuilder sb = new StringBuilder(s);
        System.out.println("hashCode1: " + s.hashCode() + " " + sb.hashCode());
         
        String s1 = new String("hashCode");
        StringBuilder sb1 = new StringBuilder(s1);
        System.out.println("hashCode2: " + s1.hashCode() + " " + sb1.hashCode());
         
        // are they equals?
        System.out.println("s  s1 : " + s.equals(s1));
        System.out.println("sb sb1: " + sb.equals(sb1));
    }
}
