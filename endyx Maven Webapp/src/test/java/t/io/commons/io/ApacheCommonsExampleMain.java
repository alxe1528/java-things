package t.io.commons.io;

import java.io.IOException;

import t.io.commons.io.example.ComparatorExample;
import t.io.commons.io.example.FileMonitorExample;
import t.io.commons.io.example.FiltersExample;
import t.io.commons.io.example.InputExample;
import t.io.commons.io.example.OutputExample;
import t.io.commons.io.example.UtilityExample;

public class ApacheCommonsExampleMain {

	/**
	 * required commons-io 2.2
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UtilityExample.runExample();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileMonitorExample.runExample();

		FiltersExample.runExample();

		InputExample.runExample();

		OutputExample.runExample();

		ComparatorExample.runExample();
	}
}