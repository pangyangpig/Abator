package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	for (Column c : table.getColumns()) {
	    if (c.getDataType().equalsIgnoreCase("datetime")) {
		sb.append("import java.util.Date;\n");
		break;
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
	List<Column> columns = table.getColumns();
	for (Column c : columns) {
	    if (c.getDataType().equalsIgnoreCase("int")) {
		sb.append(tab + "protected int "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(this.getCamelName(c.getName())),
			"int");
	    } else if (c.getDataType().equalsIgnoreCase("bigint")) {
		sb.append(tab + "protected int "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "int");
	    } else if (c.getDataType().equalsIgnoreCase("double")) {
		sb.append(tab + "protected double "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "double");
	    } else if (c.getDataType().equalsIgnoreCase("float")) {
		sb.append(tab + "protected float "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "float");
	    } else if (c.getDataType().equalsIgnoreCase("tinyint")) {
		sb.append(tab + "protected int "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "int");
	    } else if (c.getDataType().equalsIgnoreCase("varchar")) {
		sb.append(tab + "protected String "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "String");
	    } else if (c.getDataType().equalsIgnoreCase("char")) {
		sb.append(tab + "protected String "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "String");
	    } else if (c.getDataType().equalsIgnoreCase("datetime")) {
		sb.append(tab + "protected Date "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "Date");
	    } else if (c.getDataType().equalsIgnoreCase("text")) {
		sb.append(tab + "protected String "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "String");
	    } else if (c.getDataType().equalsIgnoreCase("tinytext")) {
		sb.append(tab + "protected String "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "String");
	    } else if (c.getDataType().equalsIgnoreCase("longtext")) {
		sb.append(tab + "protected String "
			+ getCamelName(this.getCamelName(c.getName())) + ";//"
			+ c.getComment() + "\n");
		fields.put(this.getCamelName(c.getName()), "String");
	    }
	}
    }

    protected void buildSetterGetter() {
	sb.append("\n");
	Iterator<String> keys = fields.keySet().iterator();
	while (keys.hasNext()) {
	    String field = (String) keys.next();
	    sb.append("\n");
	    sb.append(
		    tab + "public void set"
			    + field.substring(0, 1).toUpperCase()
			    + field.substring(1, field.length()) + "("
			    + fields.get(field) + " " + field + ")")
		    .append("{\n").append("").append(tab + "}\n");
	    sb.append("\n");
	    sb.append(
		    tab + "public " + fields.get(field) + " get"
			    + field.substring(0, 1).toUpperCase()
			    + field.substring(1, field.length()) + "()")
		    .append("{\n").append(tab)
		    .append(tab + "return this." + field + ";\n")
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
	return name.substring(0, 1).toLowerCase()
		+ name.substring(1, name.length());
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
