package org.com.app.core.jpos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

public class MessageHandler {
	private static ISOPackager packager = PackagerFactory.getPackager();
    private Logger logger = Logger.getLogger( getClass() );
    public String process(ISOMsg isomsg) throws Exception {
        logger.info("ISO Message MTI is "+isomsg.getMTI());
        logger.info("Is ISO message a incoming message? "+isomsg.isIncoming());
        logger.info("Is ISO message a outgoing message? "+isomsg.isOutgoing());
        logger.info("Is ISO message a request message? "+isomsg.isRequest());
        logger.info("Is ISO message a response message? "+isomsg.isResponse());
        String message = "";
        for (int i=0;i<128;i++){
            if (isomsg.hasField(i)){
                message += loadXMLProperties().getProperty(Integer.toString(i))+"="+
                    isomsg.getValue(i)+"\n";
            }
        }
        logger.info(message);
        return message;
    }
 
    public ISOMsg unpackRequest(String message) throws ISOException, Exception {
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        isoMsg.unpack(message.getBytes());
        isoMsg.dump(System.out, " ");
        return isoMsg ;
    }
 
    public String packResponse(ISOMsg message) throws ISOException, Exception {
        message.dump(System.out, " ");
        return new String( message.pack() ) ;
    }
    
    public Properties loadXMLProperties(){
        Properties prop = new Properties();
        try{
            FileInputStream input=new FileInputStream("iso87asciiProperties.xml");
            prop.loadFromXML(input);
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return prop;
    }
}
