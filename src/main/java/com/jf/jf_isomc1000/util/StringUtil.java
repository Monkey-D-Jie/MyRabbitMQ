package com.jf.jf_isomc1000.util;

import com.sun.istack.internal.NotNull;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


	private static  Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	private static final String[] CHINESE_NUMBER = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	private static final String[] UNIT = {"", "十", "百", "千"};
	private static final String[] MORE_THAN_TEN_THOUSAND = {"", "万", "亿"};
	private static final Pattern zeroPattern = Pattern.compile("[\u96f6]+");

	/**
	 * 将星期几转成汉字：第5周/星期3/第12节->第5周/星期三/第12节
	 *
	 * @param name
	 * @return
	 */
	public static String changWeekName(String name) {
		String[] split = name.split("期");
		String[] split2 = split[1].split("/");
		Integer valueOf = Integer.valueOf(split2[0]);
		StringBuffer bu = new StringBuffer();
		bu.append(split[0] + "期");
		switch (valueOf) {
		case 1:
			bu.append("一");
			break;
		case 2:
			bu.append("二");
			break;
		case 3:
			bu.append("三");
			break;
		case 4:
			bu.append("四");
			break;
		case 5:
			bu.append("五");
			break;
		case 6:
			bu.append("六");
			break;
		case 7:
			bu.append("日");
			break;
		default:
			break;
		}
		bu.append("/" + dealOrder(Integer.valueOf(split2[1])));
		return bu.toString();
	}

	/**
	 * 中文转小写拼音，拼音ü用字母v表示，不能转换的中文直接删除
	 *
	 * @param chineseStr
	 *            中文字符串
	 * @return 中文转成拼音后的小写字母，若转换出错返回null
	 */
	public static String Chinese2LowerCasePinyin(String chineseStr) {
		return Chinese2Pinyin(chineseStr, HanyuPinyinCaseType.LOWERCASE);
	}

	/**
	 * 中文转拼音，拼音ü用字母v表示，不能转换的中文直接删除
	 *
	 * @param chineseStr
	 *            中文字符串，不能为null
	 * @param pinyinCaseType
	 *            拼音大小写类型
	 * @return 指定大小写的拼音字母，若转换出错返回null
	 */
	private static String Chinese2Pinyin(String chineseStr,
			HanyuPinyinCaseType pinyinCaseType) {
		HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
		pinyinFormat.setCaseType(pinyinCaseType);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 无音标
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V); // 用v表示ü
		String pinyinStr = null;
		try {
			pinyinStr = PinyinHelper.toHanYuPinyinString(chineseStr,
					// false表示遇到不能转换的中文直接删除，true表示不能转换的中文以中文保留
					pinyinFormat, "", false);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyinStr;
	}

	/**
	 * 获取名字大写拼音首字母
	 *
	 * @param name
	 * @return
	 */
	public static String getChineseHeadPinyinWithUpperCase(String name) {

		if (StringUtils.isNotBlank(name)) {
			String regex = "^[a-zA-Z].*";

			String trim = name.trim();
			if (trim.matches(regex)) {
				return trim.substring(0,1).toUpperCase();
			}

			String pinyin = Chinese2Pinyin(trim, HanyuPinyinCaseType.UPPERCASE);
			if (StringUtils.isNotBlank(pinyin)) {
				return pinyin.substring(0, 1);
			}
		}

		return null;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
//			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String dealOrder(Integer order){
		return "第" + convertArabNumToChineseNum(order) + "节";
	}

    /**
     * 将阿拉伯数字转为中文数字
     *
     * @param arabNum 阿拉伯数字
     * @return 中文数字
     */
	public static String convertArabNumToChineseNum(@NotNull Integer arabNum) {

		String[] split = arabNum.toString().split("");

		/**
		 * step 1:第一次处理，只需要将数字转为汉字即可
		 */
		StringBuffer dealNum = new StringBuffer();
		for (int i = 0; i < split.length; i++) {
			dealNum.append(CHINESE_NUMBER[Integer.parseInt(split[i])]);
		}

		/**
		 * step 2:将第一次处理后的数字保存下来，并清空stringBuffer以供后面使用，也可以重新声明一个
		 */
		String firstDealNum = dealNum.toString();
		dealNum.setLength(0);

		/**
		 * step 3:将数字从后往前面取，每四位取一次，然后转换成千百十的数字，比如1234就转换为一千二百三十四
		 * step 4:将数字拼接起来，顺带加上万亿等单位 count为是否已经达到万或者亿的数量级
		 */
		int count = 0;
		for (int i = firstDealNum.length(); i > 0; i -= 4) {
			String substring = firstDealNum.substring(i - 4 < 0 ? 0 : i - 4, i);

			String[] numbers = substring.split("");

			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < numbers.length; j++) {
				String num = numbers[j];
				String unit = UNIT[numbers.length - 1 - j];

				if (CHINESE_NUMBER[0].equals(num)) {
					sb.append(num);
				}else{
					sb.append(num).append(unit);
				}
			}

			dealNum.insert(0, MORE_THAN_TEN_THOUSAND[count]);
			dealNum.insert(0, sb.toString());
			count++;
		}

		/**
		 * step 5:个位是零的话给他干掉
		 * step 6:将数字中的【零】【零...】替换为【零】
		 */
		if (dealNum.length() - 1 == dealNum.lastIndexOf("零")) {
			dealNum.deleteCharAt(dealNum.length() - 1);
		}
		Matcher matcher = zeroPattern.matcher(dealNum.toString());
		String s = matcher.replaceAll("零");
		StringBuffer buffer = new StringBuffer(s);
		if (buffer.indexOf("一十") != -1) {
			buffer.deleteCharAt(0);
		}
		return buffer.toString();
	}

}
