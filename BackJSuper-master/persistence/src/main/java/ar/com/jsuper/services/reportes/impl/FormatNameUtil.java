/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yoav Aharoni
 */
public class FormatNameUtil {
	public static Map<String, String> types = new HashMap<String, String>();
	private static final String DEFAULT_FORMAT = "png";

	static {
		types.put("gif", "gif");
		types.put("jpg", "jpg");
		types.put("jpeg", "jpg");
		types.put("png", "png");
	}

	public static String formatForExtension(String extension) {
		final String type = types.get(extension);
		if (type == null) {
			return DEFAULT_FORMAT;
		}
		return type;
	}

	public static String formatForFilename(String fileName) {
		final int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex < 0) {
			return DEFAULT_FORMAT;
		}
		final String ext = fileName.substring(dotIndex + 1);
		return formatForExtension(ext);
	}
}
