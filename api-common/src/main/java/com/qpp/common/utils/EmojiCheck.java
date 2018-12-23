package com.qpp.common.utils;

public class EmojiCheck {

	private EmojiCheck(){}

	/**
	 * @Author qipengpai
	 * @Description //TODO 判别是否包含Emoji表情
	 * @Date 2018/12/23 21:59
	 * @Param [str]
	 * @return boolean
	 * @throws
	 **/
	public static boolean containsEmoji(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (isEmojiCharacter(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmojiCharacter(char codePoint) {
		return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}
}
