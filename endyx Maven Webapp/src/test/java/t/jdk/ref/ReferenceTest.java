package t.jdk.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

public class ReferenceTest {

	/**
	 * 堆内存溢出。因为我们不断的在堆上分配一个 1M 大小的 byte[]对象，并且将该引用加入到 list 中，循环1024次，需要占用 1G 的堆内存，从而导致 heap space OutOfMemory.
	 */
	@Test
	public void testGeneration() {
		LinkedList<byte[]> list = new LinkedList<>();
		for (int i = 0; i < 1024; i++) {
			list.add(new byte[1024 * 1024]);
		}
	}

	/**
	 * 我们发现堆内存溢出的错误没有了。这是什么原因呢。
	 * 因为我们使用了 弱引用WeekReference 来引用堆上的 1M 的byte[]对象， 而弱引用WeekReference引用的对象，
	 * 如果仅仅只被弱引用，而没有被强引用的话，在下一次GC时，就会回收该对象占用的内存 ，所以不会内存溢出。
	 */
	@Test
	public void testWeak() {
		long beginTime = System.nanoTime();
		LinkedList<WeakReference<byte[]>> list = new LinkedList<>();

		System.out.println(list.size());
		for (int i = 0; i < 1024; i++) {
			list.add(new WeakReference<>(new byte[1024 * 1024]));
		}
		long endTime = System.nanoTime();

		Set set = new HashSet();
		for (int i = 0; i < 1024; i++) {
			set.add(list.get(i));
			System.out.println(list.get(i));
		}
		System.out.println(set.size());
	}

	
	/**
	 *  我们发现堆内存溢出的错误也没有了。
	 *  因为我们使用了 软引用SoftReference 来引用堆上的 1M 的byte[]对象， 而软引用SoftReference引用的对象，
	 *  如果仅仅只被软引用，而没有被强引用的话，在内存空间不足时，GC 就会回收该对象占用的内存 ，所以不会内存溢出。
	 *
	 *	但是我们注意到 采用WeekReference和采用SoftReference所花费的时间，有接近10被的差距。
	 *  原因应该是，SoftReference只有在内存空间不足时，GC才会回收对象占用的空间，
	 *  而这时进行的是 full GC，full GC会导致 STW 程序暂停，所以花费的时间过多。
	 */
	@Test
	public void testSoft() {
		long beginTime = System.nanoTime();
		LinkedList<SoftReference<byte[]>> list = new LinkedList<>();
		for (int i = 0; i < 1024; i++) {
			list.add(new SoftReference<>(new byte[1024 * 1024]));
		}
		long endTime = System.nanoTime();
		System.out.println(endTime - beginTime);
	}
}