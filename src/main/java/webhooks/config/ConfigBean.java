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
package webhooks.config;

import java.util.*;

/**
 * Configuration bean based on webhooks-config.xml
 */
public enum ConfigBean {
	INSTANCE;

	private List<String> apiKeys = new ArrayList<String>();
	private List<String> paths = new ArrayList<String>();
	private boolean authenticationEnabled;

	public static ConfigBean getInstance() {
		return INSTANCE;
	}


	public List<String> addKey(String key) {
		if (key != null && key.trim().length() > 0) {
			apiKeys.add(key);
		}

		return apiKeys;
	}

	public List<String> addPath(String path) {
		if (path != null && path.trim().length() > 0) {
			paths.add(path);
		}

		return paths;
	}

	public boolean isValidKey(String key){
		return apiKeys.contains(key);
	}

	public boolean isValidPath(String path) {
		return paths.contains(path);
	}

	public List<String> getPaths() {
		return paths;
	}

	public boolean isAuthenticationEnabled() {
		return authenticationEnabled;
	}

	public void setAuthenticationFlag(boolean authentication) {
		this.authenticationEnabled = authentication;
	}
}
