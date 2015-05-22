package t.jdk.hook;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/*
 * @(#)TestSignalHandler.java 2008-5-22
 *
 * Copyright (c) 2008
 * All rights reserved.
 * $
 */
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * <strong>Purpose:This Class only work for sun's jvm.</strong><br>
 * When you run this program, it will print dots onto the console and if you try
 * to stop it with Ctrl+C, it will give you the following output and merrily
 * carry on printing dots. You can either stop it by waiting for the 100 seconds
 * or to kill it via the task manager. (Kill sends a SIGTERM signal, which is
 * stronger than SIGINT).Inside the signal handler you can do anything you want
 * to, including calling System.exit(0) or Runtime.getRuntime().halt(0) if you
 * don't want shutdown hooks to be called.
 * 
 * 
 * What signals can you catch? The answer to that question depends on the
 * operating system you are running on. I managed to dig out a list for Windows
 * and Solaris Sun JDK 1.2.2: # Windows: ABRT, FPE, ILL, INT, SEGV, TERM #
 * Solaris: ABRT, ALRM, BUS, CHLD, CONT, EMT, FPE, HUP, ILL, INT, IO, KILL,
 * PIPE, POLL, PROF, PWR, QUIT, SEGV, STOP, SYS, TERM, TRAP, TSTP TTIN, TTOU,
 * URG, USR1, USR2, VTALRM, WINCH, XCPU, XFSZ
 * 
 * @version 1.0.1 2008-5-22<br>
 * @author Xiaoyi Lu<br>
 * @email ddjoke@163.com
 */
public class SignalMain {

	public static void main(String args[]) throws Exception {

		SignalHandler handler = new SignalHandler() {

			public void handle(Signal signal) {
				System.out.println(signal.getName());
				System.exit(-1);
			}

		};

		// Signal.handle(new Signal("QUIT"),handler);

		// Signal.handle(new Signal("KILL"), handler); //相当于kill -9

		Signal.handle(new Signal("TERM"), handler);// 相当于kill -15

		Signal.handle(new Signal("INT"), handler);// 相当于Ctrl+C

		for (;;) {
			Thread.sleep(1000);
		}
	}
}