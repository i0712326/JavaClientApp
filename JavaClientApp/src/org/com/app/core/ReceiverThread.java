package org.com.app.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.MsgUtil;
import org.com.app.core.jpos.PackagerFactory;
import org.com.app.core.resource.QueueProcessor;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

public class ReceiverThread implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	private static ISOPackager packager = PackagerFactory.getPackager();
	private int port;
	public ReceiverThread(int port){
		this.port = port;
	}
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			logger.debug("Start listen at port : "+port);
			while(true){
				Socket socket = serverSocket.accept();
				readData(socket);
			} 
		}
		catch(IOException e){
			
		}
		finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				logger.debug("Exception occured while try to close socket", e);
			}
		}
		
	}
	private static final int BUFFSIZE = 4;
	private void readData(Socket socket){
		DataInputStream dis = null;
		QueueProcessor queue = new QueueProcessor();
		try{
			dis = new DataInputStream(socket.getInputStream());
			byte[] buffer = new byte[BUFFSIZE];
			dis.read(buffer);
			int len = Integer.parseInt(new String(buffer));
			byte[] data = new byte[len];
			if(len>0){
				dis.readFully(data);
				ISOMsg isoMsg = new ISOMsg();
				isoMsg.setPackager(packager);
				isoMsg.unpack(data);
				logger.debug(">> Received Data Came from Port : "+socket.getLocalPort());
				MsgUtil.printLogger(isoMsg);
				queue.put(data);
			}
		}
		catch(IOException e){
			logger.debug("IOException occured while try to read data", e);
		} catch (ISOException e) {
			logger.debug("Exception occure while try to read ISO Message", e);
		}
	}
}
