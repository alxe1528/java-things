package t.jdk.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compiler {

	public static void main(String[] args) throws IOException {
		
		String l="\r\n";
		String src="package t.jdk.compiler;"+l+

		"import javax.tools.JavaCompiler;"+l+
		"import javax.tools.ToolProvider;"+l+

		"public class Compiler1 {"+l+
		"}";
		String fileName=System.getProperty("user.dir")+"/src/test/java/t/jdk/compiler/Compiler1.java";
		System.out.println(fileName);
		FileWriter f= new FileWriter(new File(fileName));
		f.write(src);
		f.flush();
		f.close();
		
		JavaCompiler jc=ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager jfMgr= jc.getStandardFileManager(null, null, null);
		Iterable it=jfMgr.getJavaFileObjects(fileName);
		CompilationTask ct=jc.getTask(null, jfMgr, null, null, null, it);
		ct.call();
		jfMgr.close();
		
		
		URL[] urls= new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/src")};
		URLClassLoader clsLoader= new URLClassLoader(urls);
		
		Class c;
		try {
			c = clsLoader.loadClass("t.jdk.compiler.Compiler1");
			System.out.println(c);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
