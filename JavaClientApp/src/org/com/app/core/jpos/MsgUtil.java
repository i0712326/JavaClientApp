package org.com.app.core.jpos;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class MsgUtil {
	private static Logger logger = Logger.getLogger(MsgUtil.class);
	private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	public static byte[] appendHeader(byte[] data){
		int len = data.length;
		String strLen = String.format("%04d", len);
		byte[] header = strLen.getBytes();
		return ArrayUtils.addAll(header, data);
	}
	
	public static byte[] extractMsg(byte[] data){
		byte[] header = {data[0], data[1], data[2], data[3]};
		int len = Integer.parseInt(new String(header));
		byte[] raw = new byte[len];
		for(int i=4;i<data.length;i++)
			raw[i-4] = data[i];
		return raw;
	}
	
	public static String str2Hex(String str) throws UnsupportedEncodingException{
		if(str==null) throw new NullPointerException();
		return asHex(str.getBytes());
	}
	
	public static String asHex(byte[] buf){
		char[] chars = new char[2*buf.length];
		for(int i=0;i<buf.length;++i){
			chars[2*i] = HEX_CHARS[(buf[i]&0xF0)>>>4];
			chars[2*i+1] = HEX_CHARS[buf[i]&0x0F];
		}
		return new String(chars);
	}
	
	public static void printLogger(ISOMsg isoMsg) throws ISOException{
		logger.debug("");
		logger.debug("------------- ISO Message Detail -------------");
		logger.debug("+   No.  +  Len.  +          Val.            +");
		logger.debug("----------------------------------------------");
		logger.debug("[      1][      4]["+String.format("%s", isoMsg.getMTI())+"]");
		for(int i=1;i<=isoMsg.getMaxField();i++){
			if(isoMsg.hasField(i)){
				String field = isoMsg.getString(i);
				String num = String.format("%7d", i);
				String len = String.format("%7d", field.length());
				String val = String.format("%s", field);
				logger.debug("["+num+"]"+"["+len+"]"+"["+val+"]");
			}
		}
		logger.debug("----------------------------------------------");
		logger.debug("");
	}
}
