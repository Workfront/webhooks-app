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
