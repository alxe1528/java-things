package t.net.jdkSocket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(44544,0,InetAddress.getByName("192.168.128.1"));
			Socket sc=s.accept();
			InputStream ip=sc.getInputStream();
			OutputStream os=sc.getOutputStream();
			BufferedReader bis = new BufferedReader(new InputStreamReader(ip));
			BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os));
			
			
			while(true){
				String msg=bis.readLine();
				System.out.println(msg);
				bw.write("<html></html>");
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
