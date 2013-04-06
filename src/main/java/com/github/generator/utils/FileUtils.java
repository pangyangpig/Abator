package com.github.generator.utils;

import java.io.File;

public final class FileUtils {
    public static void deleteDirectory(String filepath) {
	File f = new File(filepath);
	if (f.exists() && f.isDirectory()) {
	    if (f.listFiles().length == 0) {
		f.delete();
	    } else {
		File delFile[] = f.listFiles();
		int i = f.listFiles().length;
		for (int j = 0; j < i; j++) {
		    if (delFile[j].isDirectory()) {
			deleteDirectory(delFile[j].getAbsolutePath());
		    }
		    delFile[j].delete();
		}
	    }
	}
    }
}
