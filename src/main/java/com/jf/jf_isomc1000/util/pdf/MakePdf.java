package com.jf.jf_isomc1000.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jf.jf_isomc1000.util.UUIDUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title:创建pdf
 * @Description:
 * @author LuoZhigang
 * @Date 2018年9月6日
 * @version 0.0.1
 *
 */
public class MakePdf {
	/**
	 * 创建html[日报]
	 * 
	 * @param dataMap
	 *        参数
	 * @param classUrl
	 *        路径
	 * @throws Exception
	 */
	public static void makePdf(Map<String, Object> map, String classUrl, String name, String temp) throws Exception {
		Configuration configuration = new Configuration(Configuration.getVersion());//配置freemaker
		String templateUrl = classUrl + "makePdf/template/";
		File filePath = new File(templateUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		configuration.setDirectoryForTemplateLoading(filePath);//模板位置
		configuration.setDefaultEncoding("utf-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Template template = configuration.getTemplate(temp);
		String htmlName = UUIDUtil.getUUID().replace("-", "") + ".html";
		String outUrl = classUrl + "/template/" + htmlName;
		Writer out = new FileWriter(new File(outUrl));
		template.process(map, out);//写入数据,生成html
		out.close();

		String pdfUrl = "C:/pdf/" + name;
		File file = new File(pdfUrl);//pdf存放位置
		File fileParent = file.getParentFile();
		if (!fileParent.exists()) {//判断文件夹是否存在
			fileParent.mkdirs();
		}
		Document document = new Document(PageSize.LETTER);//页面大小
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfUrl));//输出流
		document.open();
		document.addAuthor("JF");
		document.addTitle("Day");

		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();//iText
		InputStream is = new FileInputStream(new File(outUrl));//输入流
		worker.parseXHtml(pdfWriter, document, is, null, new AsianFontProvider());//生成pdf
		document.close();
		pdfWriter.close();
		is.close();

		/*
		 *删除html 
		 */
		List<String> deleteUrl = new ArrayList<String>();
		deleteUrl.add(outUrl);
		UtilForString.deleteFile(deleteUrl);
	}
}
