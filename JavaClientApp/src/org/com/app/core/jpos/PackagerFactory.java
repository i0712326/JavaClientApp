package org.com.app.core.jpos;

import java.io.InputStream;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

public class PackagerFactory {
	public static ISOPackager getPackager() {
        ISOPackager packager = null;
        try {
            String filename = "messageDefinition.xml";
            InputStream is = PackagerFactory.class.getResourceAsStream(filename);
            packager = new GenericPackager(is);
        } 
        catch (ISOException e) {
            e.printStackTrace();
        }
        return packager;
    }
}
