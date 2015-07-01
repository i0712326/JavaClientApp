package org.com.app.core;


public class ApplicationCore {
	private int port;
	public String host;
	public ApplicationCore(String host, int port){
		this.host = host;
		this.port = port;
	}
	public void start() {
		ReceiverThread receiverThread = new ReceiverThread(port);
		SenderThread senderThread = new SenderThread(host, port);
		Thread t1 = new Thread(receiverThread);
		Thread t2 = new Thread(senderThread);
		t1.start();
		t2.start();
	}

}
