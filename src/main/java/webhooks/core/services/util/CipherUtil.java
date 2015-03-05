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

/**
 */

import org.apache.commons.codec.binary.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.*;

public class CipherUtil {

	private static byte[] key = {
		0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
	};//"thisIsASecretKey";

	public static String decrypt(String encryptedInput) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
			return new String(cipher.doFinal(Base64.decodeBase64(encryptedInput)));
		} catch (Exception ex) {
			Logger.getLogger(CipherUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}


	public static String encrypt(String str) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
			final String encryptedString = Base64.encodeBase64String(cipher.doFinal(str.getBytes()));
			return encryptedString;
		} catch (Exception ex) {
			Logger.getLogger(CipherUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static void main(String[] args) {
		// Encryption
		String encryptedString = CipherUtil.encrypt("/Users/alextan/dev" + "#" + String.valueOf(new Date().getTime()));
		// Before Decryption
		System.out.println("Before Decription : " + encryptedString);
		String s = CipherUtil.decrypt(encryptedString);
		System.out.println("After Decription : " + s);
	}
}

