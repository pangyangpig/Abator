package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 生成表对应的Model
 * 
 * @author xiaog
 * 
 */
public class ModelClassBuilder extends ClassBuilder {
    private static String NAME_SUFFIX = "Model";
    private String _package;
    private String tab;
    private static final String OUTPUT = System.getProperty("user.dir")
	    + File.separator + "output";
    private Table table;
    private StringBuffer sb;
    private Map<String, String> fields;

    public ModelClassBuilder(String _package) {
	tab = "    ";
	if (_package == null || _package.equals("")) {
	    System.out.println("WARN: package must be setting. ");
	}
	this._package = _package;
	File outputDir = new File(OUTPUT);
	if (!outputDir.exists()) {
	    outputDir.mkdir();
	} else {
	    FileUtils.deleteDirectory(OUTPUT);
	}
    }

    public ModelClassBuilder from(Table table) {
	sb = new StringBuffer();
	fields = new HashMap<String, String>();
	if (table == null || table.getName().equals("")) {
	    throw new java.lang.IllegalArgumentException();
	}
	this.table = table;
	return this;
    }

    protected void buildPackage() {
	if (this._package != null) {
	    sb.append("package " + this._package + ";");
	    sb.append("\n");
	}
    }

    protected void buildImport() {
	sb.append("\n");
	if (table.getColumns() == null) {
	    return;
	} else {
	    for (Column c : table.getColumns()) {
		if (c.getDataType().equals(DataType.TIME)
			|| c.getDataType().equals(DataType.YEAR)
			|| c.getDataType().equals(DataType.TIMESTAMP)
			|| c.getDataType().equals(DataType.DATETIME)) {
		    sb.append("import java.util.Date;\n");
		    break;
		}
	    }
	}
	sb.append("\n");
    }

    protected void buildAnnotate() {
	sb.append("\n");
	sb.append("/**\n");
	sb.append("*" + table.getComment() + "\n");
	sb.append("*/");
	sb.append("\n");
    }

    protected void buildClassName() {
	sb.append("\n");
	sb.append("public class "
		+ this.getModelClassName(table.getName() + NAME_SUFFIX) + "{\n");
    }

    protected void buildField() {
	sb.append("\n");
	Set<Column> columns = table.getColumns();
	if (columns == null) {
	    System.err.println("WARN: Table=" + table.getName()
		    + " has no column.");
	    return;
	} else {
	    for (Column c : columns) {
		String name = getCamelName(this.getCamelName(c.getName()));
		if (JavaKeyWord.isJavaKeyWord(name)) {
		    name = "_" + name;
		}
		switch (c.getDataType()) {
		case TIME:
		    sb.append(tab + "protected Date " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "Date");
		    break;
		case YEAR:
		    sb.append(tab + "protected Date " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "Date");
		    break;
		case TIMESTAMP:
		    sb.append(tab + "protected Date " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "Date");
		    break;
		case DATETIME:
		    sb.append(tab + "protected Date " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "Date");
		    break;
		case DOUBLE:
		    sb.append(tab + "protected double " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "double");
		    break;
		case FLOAT:
		    sb.append(tab + "protected float " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "float");
		    break;
		case CHAR:
		    sb.append(tab + "protected String " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "String");
		    break;
		case VARCHAR:
		    sb.append(tab + "protected String " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "String");
		    break;
		case INT:
		    sb.append(tab + "protected int " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "int");
		    break;
		case TINYINT:
		    sb.append(tab + "protected int " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "int");
		    break;
		case BIGINT:
		    sb.append(tab + "protected int " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "int");
		    break;
		case TEXT:
		    sb.append(tab + "protected String " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "String");
		    break;
		case TINYTEXT:
		    sb.append(tab + "protected String " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "String");
		    break;
		case LONGTEXT:
		    sb.append(tab + "protected String " + name + ";//"
			    + c.getComment() + "\n");
		    fields.put(this.getCamelName(c.getName()), "String");
		    break;
		}
	    }
	}
    }

    protected void buildSetterGetter() {
	sb.append("\n");
	Iterator<String> keys = fields.keySet().iterator();
	while (keys.hasNext()) {
	    String field = (String) keys.next();
	    String _field = field;
	    if (JavaKeyWord.isJavaKeyWord(field)) {
		_field = "_" + field;
	    }
	    sb.append("\n");
	    sb.append(
		    tab + "public void set"
			    + field.substring(0, 1).toUpperCase()
			    + field.substring(1, field.length()) + "("
			    + fields.get(field) + " " + _field + ")")
		    .append("{\n")
		    .append(tab + tab + "this." + _field + " = " + _field
			    + ";\n").append(tab + "}\n");
	    sb.append("\n");
	    sb.append(
		    tab + "public " + fields.get(field) + " get"
			    + field.substring(0, 1).toUpperCase()
			    + field.substring(1, field.length()) + "()")
		    .append("{\n").append(tab)
		    .append(tab + "return this." + _field + ";\n")
		    .append(tab + "}\n");
	}

    }

    protected void buildClassEnd() {
	sb.append("\n}");
    }

    public void create() {
	super.build();
	String dirPath = _package == null ? "" : _package;
	if (_package != null && !_package.equals("")) {
	    dirPath = dirPath.replace(".", File.separator);
	    File dirs = new File(OUTPUT + File.separator + dirPath);
	    if (!dirs.exists()) {
		dirs.mkdirs();
	    }
	}
	String modelClassName = getModelClassName(table.getName())
		+ NAME_SUFFIX;
	File modelFile = new File(OUTPUT + File.separator + dirPath
		+ File.separator + modelClassName + ".java");
	try {
	    if (!modelFile.exists()) {
		modelFile.createNewFile();
	    }
	    FileOutputStream out = new FileOutputStream(modelFile);
	    out.write(sb.toString().getBytes());
	    out.flush();
	    out.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// Thread thread = new Thread(new CreateFiles());
	// thread.start();
    }

    private String getModelClassName(String name) {
	if (name.indexOf("-") != -1 && name.indexOf("_") != -1) {
	    name.replaceAll("-", "_");
	}
	String[] t = name.split("_");
	StringBuffer modelClassName = new StringBuffer();
	for (String s : t) {
	    modelClassName.append(s.substring(0, 1).toUpperCase()).append(
		    s.substring(1, s.length()));
	}
	return modelClassName.toString();
    }

    private String getCamelName(String name) {
	name = getModelClassName(name);
	name = name.substring(0, 1).toLowerCase()
		+ name.substring(1, name.length());
	return name;
    }

    class CreateFiles implements Runnable {

	public void run() {
	    synchronized (sb) {
		String dirPath = _package == null ? "" : _package;
		if (_package != null && !_package.equals("")) {
		    dirPath = dirPath.replace(".", File.separator);
		    File dirs = new File(OUTPUT + File.separator + dirPath);
		    if (!dirs.exists()) {
			dirs.mkdirs();
		    }
		}
		String modelClassName = getModelClassName(table.getName())
			+ NAME_SUFFIX;
		File modelFile = new File(OUTPUT + File.separator + dirPath
			+ File.separator + modelClassName + ".java");
		try {
		    if (!modelFile.exists()) {
			modelFile.createNewFile();
		    }
		    FileOutputStream out = new FileOutputStream(modelFile);
		    out.write(sb.toString().getBytes());
		    out.flush();
		    out.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		sb = new StringBuffer();
	    }
	}
    }
}
