package webhooks.core.services.util;

import java.io.*;
import java.util.*;

/**
 */
public class DocumentUtil {

	public static String getLocationFromDocId (String documentId) {
		String ret = null;

		// default to root folder
		if (documentId ==  null || documentId.trim().length() == 0)
			documentId = "/";

		if (documentId.equals("/"))
			ret = System.getProperty("user.home");
		else {
			// decrypt the string
			String decryptedString = CipherUtil.decrypt(documentId);
			// remove the timestamp and delimiter data
			if (decryptedString != null && decryptedString.length() >= 0) {
				int idx = decryptedString.lastIndexOf("#");
				ret = decryptedString.substring(0, idx);
			}
		}

		return ret;
	}

	public static String GenerateDocId(String path) {
		String ret = null;
		if (path != null && path.length() > 0){
			String now = String.valueOf(new Date().getTime());

			// fullpath + # + timestamp(long)
			ret = CipherUtil.encrypt(new File(path).toPath() + "#" + now);
		}

		return ret;
	}

}
