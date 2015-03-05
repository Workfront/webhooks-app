/**
 * Copyright 2015 Workfront, Inc.
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
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.regex.*;

/**
 */
public class FileFinder extends SimpleFileVisitor<Path> {

	private int max;
	private int offset;
	private ArrayList<File> files;
	Pattern pattern;

	public FileFinder(String query, int max,  int offset) {
		pattern = Pattern.compile(query);
		this.max = max;
		this.offset = offset;
		files = new ArrayList<File>();
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ArrayList<File> getMatched() {
		return files;
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

		if (files.size() == max) {
			return FileVisitResult.TERMINATE;
		}

		File file = path.toFile();
		if (!file.isHidden()) {
			String name = file.getName();
			Matcher matcher = pattern.matcher(name);
			if (matcher.find()) {
				if (files.size() < max){
					files.add(file);
				}
			}
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (dir.toFile().isHidden()) {
			return FileVisitResult.SKIP_SUBTREE;
		}
		return FileVisitResult.CONTINUE;
	}
}
