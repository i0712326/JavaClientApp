package org.com.app.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.MsgUtil;
import org.com.app.core.jpos.PackagerFactory;
import org.com.app.core.resource.QueueProcessor;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

public class SenderThread implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	private static ISOPackager packager = PackagerFactory.getPackager();
	private String host;
	private int port;
	public SenderThread(String host, int port){
		this.host = host;
		this.port = port;
	}
	@Override
	public void run() {
		Socket socket = null;
		DataOutputStream dos = null;
		try {
			socket = new Socket(host,port);
			dos = new DataOutputStream(socket.getOutputStream());
			QueueProcessor queueProcessor = new QueueProcessor();
			logger.debug("Sender start up");
			while(true){
				if(!queueProcessor.isEmpty()){
					byte[] data = queueProcessor.take();
					ISOMsg isoMsg = new ISOMsg();
					isoMsg.setPackager(packager);
					isoMsg.unpack(data);
					isoMsg.setMTI("0210");
					isoMsg.set(39,"00");
					byte[] msg = MsgUtil.appendHeader(isoMsg.pack());
					dos.write(msg);
				}
			}
			
		} catch (IOException e) {
			logger.debug("Exception occured due to IO access",e);
		} catch (ISOException e) {
			logger.debug("Exception occured while try to process ISO message",e);
		}
		finally{
			try {
				dos.close();
				socket.close();
			} catch (IOException e) {
				logger.debug("Exeption occured while try to close socket", e);
			}
		}
	}

}
