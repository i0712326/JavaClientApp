package org.com.app;

import org.com.app.core.ApplicationCore;

public class Main {
	private static int port = 1123;
	private static String host = "10.0.31.39";
	public static void main(String[] args) {
		ApplicationCore application = new ApplicationCore(host,port);
		application.start();
	}

}
