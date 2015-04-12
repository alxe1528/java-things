package t.jdk.classloader;

import java.net.URL;

public class BootStrapLoader {
	public static void main(String[] args) {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}

		System.out.println("============================");
		System.out.println(System.getProperty("sun.boot.class.path"));

		System.out.println("===================================");
		ClassLoader loader = BootStrapLoader.class.getClassLoader(); // 获得加载ClassLoaderTest.class这个类的类加载器
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent(); // 获得父类加载器的引用
		}
		System.out.println(loader);

	}

}
