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
import java.util.regex.*;

/**
 */
public class MatchFilenameFilter implements FilenameFilter {
	private String query = null;

	@Override
	public boolean accept(File dir, String name) {
		if (query == null || query.trim().length() == 0) {
			return false;
		}

		// filter out hidden file
		File target = new File(name);
		if (target.isHidden())
			return false;

		Pattern pattern = Pattern.compile(query);
		Matcher matcher = pattern.matcher(name);

		return matcher.find();
	}

	public String getQuery() {
		return query;
	}

	public MatchFilenameFilter setQuery(String query) {
		this.query = query;
		return this;
	}
}
