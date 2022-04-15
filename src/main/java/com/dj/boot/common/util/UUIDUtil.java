package com.dj.boot.common.util;

import java.util.UUID;

public class UUIDUtil {

	private static final String STR_LINK = "-";
	private static final String STR_NULL = "";

	private UUIDUtil() {
	}

	public static String getOriginUuid() {
		return UUID.randomUUID().toString();
	}

	public static String getTerseUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String code() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String code(String prefix) {
		return prefix + UUID.randomUUID().toString().replaceAll("-", "");
	}
}
