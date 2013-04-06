package com.github.generator.utils;

public class Util {
	public static String getModelClassName(String name) {
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

	public static String getCamelName(String name) {
		name = getModelClassName(name);
		name = name.substring(0, 1).toLowerCase()
				+ name.substring(1, name.length());
		return name;
	}
	public static void main(String [] args){
		System.out.println(Util.getModelClassName("aaaBaaa"));
	}
}
