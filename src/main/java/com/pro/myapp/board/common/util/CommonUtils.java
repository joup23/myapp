package com.pro.myapp.board.common.util;

import java.util.UUID;

public class CommonUtils {
	public static String getRandomString() {
		return UUID.randomUUID().toString().replace("-", "");	//DB에 STORED_FILE_NAME으로 지정하기 위한 랜덤 문자열
	}
}
