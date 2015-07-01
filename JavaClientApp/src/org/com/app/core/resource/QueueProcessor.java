package org.com.app.core.resource;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class QueueProcessor {
	private Logger logger = Logger.getLogger(getClass());
	public synchronized void put(byte[] data){
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		try {
			queue.put(data);
		} catch (InterruptedException e) {
			logger.debug("Exception occurred due to queue is full", e);
		}
	}
	public synchronized byte[] take(){
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		try {
			return queue.take();
		} catch (InterruptedException e) {
			logger.debug("Excepiton occured due to queue is empty", e);
			return null;
		}		
	}
	
	public synchronized boolean isEmpty(){
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		return queue.isEmpty();
	}
}
