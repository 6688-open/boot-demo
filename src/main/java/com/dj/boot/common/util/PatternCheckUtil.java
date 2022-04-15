package com.dj.boot.common.util;

import java.util.regex.Pattern;

/**
 * PatternCheckUtil正则验证工具类
 * @Author: wangJia
 * @Date: 2021-06-02-11-07
 */
public class PatternCheckUtil {

	/**
	 * 校验年月日
	 */
	public  static  final String REGEX_YEAR_MONTH_DAY="^(^(\\d{4}|\\d{2})(\\-|\\/|\\.)\\d{1,2}\\3\\d{1,2}$)|(^\\d{4}年\\d{1,2}月\\d{1,2}日$)$";
	/***
	 *校验电话补充
	 *
	 */
	 public  static  final String REGEX_PHONE_NUMBER="^(\\d{3,4}-)|(\\d{3,4}-)+\\d{7,8}||[1-9][0-9]{8,11}$";
	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/**
	 * 正则表达式：固话及手机
	 */
	public static final String REGEX_PHOME_NUMBER = "^(\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,6,7,8,9][0-9]\\d{8})|\\d{3}-\\d{8,20}|\\d{4}-\\d{8,20}|\\d{8,20}";
	/**
	 * 正则表达式：验证正整数
	 */
	public static final String REGEX_POSITIVE_INTEGER="^[1-9]\\d*$";
	/**
	 *校验数字  整数  小数  负数  正数
	 */
	public static final String IS_NUMBER = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
	/**
	 * 大于0的数  正数  包含小数
	 */
	public static final String IS_POSITIVE_NUMBER = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";
	/**
	 *  非法特殊字符
	 */
	public static final String IS_SPECIAL_CHARACTER="[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t\"";
	/**
	 * 校验年月日
	 */
	public static boolean isYearMonthDay(String positiveInt) {
		return Pattern.matches(REGEX_YEAR_MONTH_DAY, positiveInt);
	}
	/**
	 * 校验正整数
	 * @param positiveInt
	 * @return
	 */
	public static boolean isPositiveInt(String positiveInt) {
		return Pattern.matches(REGEX_POSITIVE_INTEGER, positiveInt);
	}
	/**
	 *   整数  小数  负数  正数
	 */
	public static boolean isNumeric(String str) {
		return Pattern.matches(IS_NUMBER, str);
	}
	/**
	 * 大于0的数  正数  包含小数
	 */
	public static boolean isPositiveNumber(String str) {
		return Pattern.matches(IS_POSITIVE_NUMBER, str);
	}
	/**
	 * 校验邮箱
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}
	/**
	 * 校验固话及手机
	 * @param phoneNumber
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		return Pattern.matches(REGEX_PHOME_NUMBER, phoneNumber);
	}
	/**
	 * 校验固话及手机
	 * @param phoneNumber
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPhoneNumberTwo(String phoneNumber) {
		return Pattern.matches(REGEX_PHONE_NUMBER, phoneNumber);
	}
	/**
	 * 是否存在特殊字符
	 * 有特殊字符 返回true  否则返回false
	 * @return
	 */
	public static boolean isSpecialCharacter(String str){
		return Pattern.compile(IS_SPECIAL_CHARACTER).matcher(str).find();
	}

	 public static void main(String[] args) {
		//boolean bo= isEmail("aa@qq..111com");
		 //boolean numeric = isNumeric("0.2t342345");
		 boolean positiveNumber = isPositiveNumber("0");


		 boolean specialCharacter = isSpecialCharacter("#1BR3}3311<>");

		 System.out.println(222);

	}
}