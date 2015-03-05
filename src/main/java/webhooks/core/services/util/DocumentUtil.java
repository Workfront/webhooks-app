/**
 * Copyright 2014 AtTask, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
