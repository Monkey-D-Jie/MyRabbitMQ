package com.jf.jf_isomc1000.util.pdf;

import com.jf.jf_isomc1000.util.UUIDUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @Title:生成报表图片
 * @Description:
 * @author LuoZhigang
 * @Date 2018年9月6日
 * @version 0.0.1
 *
 */
public class JfreeChart {

	/**
	 * 生成柱状图
	 * 
	 * @param List<Map>
	 *        参数[teacher;value]
	 * @param classUrl
	 *        项目地址
	 * @param name
	 *        图片名称
	 * @return String
	 *         name: 图片名称
	 * @throws Exception
	 */
	public static String makeBar(DefaultCategoryDataset dataset, String classUrl, String name) throws Exception {
		JFreeChart chart = ChartFactory.createBarChart(name, "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
		chart.setTextAntiAlias(false);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 15));
		//有关柱状的设置
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(null);//背景
		plot.setOutlinePaint(null);//边框
		//高级设置
		BarRenderer brender = (BarRenderer) plot.getRenderer();
		//柱状设置
		brender.setBarPainter(new StandardBarPainter());
		//第一柱状的颜色
		brender.setSeriesPaint(0, Color.decode("#0099FF"));
		brender.setShadowVisible(false);
		// 关闭阴影
		brender.setMaximumBarWidth(0.1);
		//显示柱状的数据值
		brender.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		brender.setDefaultItemLabelsVisible(true);
		//X轴设置
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
		domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
		//换行显示[最多4行]
		domainAxis.setMaximumCategoryLabelLines(4);
		//Y轴设置
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 11));
		numberaxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
		numberaxis.setAutoTickUnitSelection(false);
		/*
		 * 用于Y轴刻度设置
		 */
		int a = dataset.getColumnCount();
		double rs = 0;
		if (a > 0) {
			//只有倒叙和正序,所有只需要一头一尾比较
			double one = Double.valueOf(String.valueOf(dataset.getValue(0, 0)));
			double tow = Double.valueOf(String.valueOf(dataset.getValue(0, a - 1)));
			if (one >= tow)
				rs = one;
			else
				rs = tow;
		}
		//第一刻度
		NumberTickUnit ntu = new NumberTickUnit(Math.floor(rs / 5));
		numberaxis.setTickUnit(ntu);//设置刻度
		numberaxis.setUpperMargin(0.1);//设置上边距
		//图片名
		String imgName = UUIDUtil.getUUID().replace("-", "") + ".jpg";
		File filePath = new File(classUrl,"template");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(classUrl + "/template/" + imgName);
		ChartUtils.writeChartAsJPEG(out, 1, chart, 500, 320, null);
		out.close();
		return imgName;
	}

	/**
	 * 生成饼图
	 * 
	 * @param map
	 *        参数[type;value]
	 * @param classUrl
	 *        项目地址
	 * @param name
	 *        图片名称
	 * @return String
	 *         name: 图片名称
	 * @throws Exception
	 */
	public static String makePie(DefaultPieDataset dataset, String classUrl, String name) throws Exception {
		JFreeChart chart = ChartFactory.createPieChart(name, dataset, true, false, false);
		chart.setTextAntiAlias(false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setShadowPaint(null);
		plot.setBackgroundPaint(null);
		//		plot.setOutlinePaint(null);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));//1为具体数据,2为百分号
		LegendTitle legend = chart.getLegend();
		legend.setBorder(0, 0, 0, 0);
		legend.setPosition(RectangleEdge.RIGHT);
		//这句代码解决了底部汉字乱码的问题
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 12));
		//这句代码解决了标题汉字乱码的问题	
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 15));
		String imgName = UUIDUtil.getUUID().replace("-", "") + ".jpg";
		FileOutputStream out = new FileOutputStream(classUrl + "/template/" + imgName);
		ChartUtils.writeChartAsJPEG(out, 1, chart, 500, 320, null);
		out.close();
		return imgName;
	}

	/**
	 * 生成蜘蛛图
	 * 
	 * @param map
	 *        参数[type;value]
	 * @param classUrl
	 *        项目地址
	 * @param name
	 *        图片名称
	 * @return String
	 *         name: 图片名称
	 * @throws Exception
	 */
	public static String makeSpider(DefaultCategoryDataset dataset, String classUrl, String name) throws Exception {
		SpiderWebPlot plot = new SpiderWebPlot(dataset);
		plot.setSeriesPaint(0, Color.BLUE);
		plot.setLabelFont(new Font("宋体", Font.BOLD, 12));
		JFreeChart chart = new JFreeChart(name, new Font("宋体", Font.BOLD, 15), plot, false);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.white);
		String imgName = UUIDUtil.getUUID().replace("-", "") + ".jpg";
		FileOutputStream out = new FileOutputStream(classUrl + "/template/" + imgName);
		ChartUtils.writeChartAsJPEG(out, 1, chart, 500, 320, null);
		out.close();
		return imgName;
	}
	
	public static void main(String[] args) {
		JfreeChart jfreeChart = new JfreeChart();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<ChartData<String, Integer>> list = new ArrayList<>();
		ChartData data1 = new ChartData<>();
		data1.setyData(0.25);
		data1.setxData("学院1");
		ChartData data2 = new ChartData<>();
		data2.setyData(0.25);
		data2.setxData("学院2");
		ChartData data3 = new ChartData<>();
		data3.setyData(0.25);
		data3.setxData("学院3");
		list.add(data1);
		list.add(data2);
		list.add(data3);
		//double[] data = new double[] {0.25, 0.30, 0.04, 0.58, 0.69,0.37,0.45,0.24,0.56,0.65,0.45,0.55,0.36,0.65,0.45,0.55,0.36 };
        //String[] rowKeys = { ""};
        //String[] columnKeys = { "学院1", "学院2", "学院3", "学院4", "学院5","学院6","学院7","学院8","学院9","学院10","学院11","学院12","学院13","学院14","学院15","学院16","学院17" };  
		for(int i=0;i<list.size();i++){
			ChartData<String, Integer> tableData = list.get(i);
			dataset.addValue(Double.parseDouble(String.valueOf(tableData.getyData())), "", tableData.getxData());
		}
		try {
			makeBar(dataset, "F:/test", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//jfreeChart.makeLineAndShapeChart(); 
		
	}
}
