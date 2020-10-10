package com.jf.jf_isomc1000.util.pdf;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 * @Title:
 * @Description:
 * @author LuoZhigang
 * @Date 2018年9月10日
 * @version 0.0.1
 *
 */
public class AsianFontProvider extends XMLWorkerFontProvider {
	/**
	 * 解决iText 转换pdf的时候中午乱码的问题
	 */
	public Font getFont(final String fontname, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Font font = new Font(bf, size, style, color);
		font.setColor(color);
		return font;
	}

}
