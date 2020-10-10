package com.jf.jf_isomc1000.util.pdf;

import java.io.File;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @author LuoZhigang
 * @Date 2018年9月7日
 * @version 0.0.1
 *
 */
public class UtilForString {
	/**
	 * 删除文件
	 * 
	 * @param url
	 * @throws Exception
	 */
	public static void deleteFile(List<String> url) throws Exception {
		for (String one : url) {
			File file = new File(one);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 添加p标签,处理缩进
	 * 
	 * @param str
	 * @throws Exception
	 */
	public static String addSpan(List<String> str, int index) throws Exception {
		StringBuffer sbf = new StringBuffer();
		for (int i = index; i < str.size(); i++) {
			String oneStr = str.get(i);
			if ("".equals(str.get(i)) || " ".equals(str.get(i))) {
				continue;
			}
			sbf.append("<p style=\"text-indent: 35px;\">");
			sbf.append(oneStr);
			sbf.append("</p>");
		}
		if (sbf.length() <= 0) {
			return "<p style=\"text-indent: 35px;\">无数据</p>";
		}
		return sbf.toString();
	}
}
