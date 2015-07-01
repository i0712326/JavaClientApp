package org.com.app.core.resource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ApplicationQueue {
	private static final int SIZE = 500;
	private static BlockingQueue<byte[]> queue = new ArrayBlockingQueue<byte[]>(SIZE);
	public static BlockingQueue<byte[]> getQueue(){
		return queue;
	}

}
