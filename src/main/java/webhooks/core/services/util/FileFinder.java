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
