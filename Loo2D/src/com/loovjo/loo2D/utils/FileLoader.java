package com.loovjo.loo2D.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {
	static Class loaderClass;

	public static Class getLoaderClass() {
		return loaderClass;
	}

	public static void setLoaderClass(Class loaderClass) {
		FileLoader.loaderClass = loaderClass;
	}
	
	public static InputStream getInputStream(String path) {
		return loaderClass.getResourceAsStream(path);
	}
	public static String readFile(String path) {
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(path)));
		String all = "";
		try {
			for (String line;(line=br.readLine())!=null;all+=line+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return all.substring(0,all.length());
	}
}
