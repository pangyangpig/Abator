package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 生成表对应的Model
 * 
 * @author xiaog
 * 
 */
public class ModelClassBuilder {
    private DbConfig dbConfig;
    private static final String OUTPUT = System.getProperty("user.dir")
	    + File.separator + "output";

    public ModelClassBuilder(DbConfig dbConfig) {
	this.dbConfig = dbConfig;
	File outputDir = new File(OUTPUT);
	if (!outputDir.exists()) {
	    outputDir.mkdir();
	} else {
	    File[] files = outputDir.listFiles();
	    for (File f : files) {
		f.delete();
	    }
	}
    }

    public void build() {
	List<Table> tables = this.dbConfig.getTables();
	try {
	    for (Table table : tables) {
		File modelFile = new File(OUTPUT + File.separator
			+ getModelClassName(table.getName()) + ".java");
		if (!modelFile.exists()) {
		    modelFile.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(modelFile);
		out.write("".getBytes());
		out.flush();
		out.close();
		System.out.println(table.toString());
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private String getModelClassName(String tableName) {
	System.out.println(tableName);
	if (tableName.indexOf("-") != -1 && tableName.indexOf("_") != -1) {
	    tableName.replaceAll("-", "_");
	}
	String[] t = tableName.split("_");
	System.out.println(t.length);
	StringBuffer modelClassName = new StringBuffer();
	for (String s : t) {
	    modelClassName.append(s.substring(0, 1).toUpperCase()).append(
		    s.substring(1, s.length()));
	}
	return modelClassName.toString();
    }

    class CreateFiles implements Runnable {

	public void run() {
	    // TODO Auto-generated method stub

	}
    }

    private void createField(FileOutputStream out) {

    }

    private void createSetterGetter(FileOutputStream out) {

    }
}
