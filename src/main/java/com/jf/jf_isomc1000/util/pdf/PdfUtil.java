package com.jf.jf_isomc1000.util.pdf;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class PdfUtil {

	/**
	 * 格式化纵向柱状图使用 水平条形图 百分比显示
	 *
	 * @param chart
	 * @returnType: void
	 * @author:
	 *
	 */

	public static String setView(String tiltle, double[][] xdata, String[] ydata, String path, String[] rowKeys,
								 String charName) {

		CategoryDataset dataset = DatasetUtils.createCategoryDataset(rowKeys, ydata, xdata);
		JFreeChart chart = ChartFactory.createBarChart(tiltle, "", "Score (%)", dataset, PlotOrientation.HORIZONTAL,
				true, true, false);
		CategoryPlot plot = chart.getCategoryPlot();
		/*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 底部汉字乱码的问题 
		chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 12));
		// 设置标题字体 
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
		// textTitle.setBackgroundPaint(Color.LIGHT_GRAY);//标题背景色 
		// textTitle.setPaint(Color.cyan);//标题字体颜色 

		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// 设置刻度必须从0开始
		vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("0%");
		vn.setNumberFormatOverride(df);
		// 数据轴数据标签的显示格式
		CategoryAxis domainAxis = plot.getDomainAxis();
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// // 横轴上的
		// Lable
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 22);
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
		// 横轴上的 Lable 是否完整显示
		// domainAxis.setVerticalCategoryLabels(false);
		plot.setDomainAxis(domainAxis);
		// plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 将类型放到上面
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
		// 将默认放到左边的数值放到右边
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		plot.setBackgroundPaint(new Color(255, 255, 255));
		ValueAxis rangeAxis = plot.getRangeAxis();
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.01);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.01);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.5);

		// 是否自适应范围
		// rangeAxis.setAutoRange(true);
		plot.setRangeAxis(rangeAxis);
		BarRenderer renderer = new BarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.01);
		// 设置柱子高度
		renderer.setMinimumBarLength(30);
		renderer.setDefaultOutlinePaint(Color.BLACK);
		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0);
		// 显示每个柱的数值，并修改该数值的字体属性 //在柱上显示百分比
		renderer.setIncludeBaseInRange(true);
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", DecimalFormat.getPercentInstance()));
		//renderer.setItemLabelAnchorOffset(0);
		renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE4, TextAnchor.BASELINE_RIGHT));
		renderer.setItemLabelAnchorOffset(55D);// 设置柱形图上的文字偏离值
		renderer.setDefaultItemLabelsVisible(true);
		// 设置柱子上的百分比字体和大小
		renderer.setDefaultItemLabelFont(new Font("黑体", Font.PLAIN, 18));
		// 设置柱为平面的而不是立体的
		renderer.setBarPainter(new StandardBarPainter());
		// 设置柱的颜色
		renderer.setSeriesPaint(0, new Color(43, 111, 213));
		renderer.setSeriesPaint(1, new Color(255,102,0));
		plot.setRenderer(renderer);
		// 设置柱的透明度
		//plot.setForegroundAlpha(0.6f);
		FileOutputStream fos_jpg = null;
		try {

			isChartPathExist(path);
			String chartName = path + charName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtils.writeChartAsPNG(fos_jpg, chart, 900,1200, true, 100);
			return chartName;
		} catch (IOException e) {
			System.out.println("生成图片错误");
			e.printStackTrace();
			return null;
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 格式化纵向柱状图使用 水平条形图 数值显示
	 *
	 * @param chart
	 * @returnType: void
	 * @author:
	 *
	 */
	public static String setViews(String tiltle, double[][] xdata, String[] ydata, String path, String[] rowKeys,
								  String charName) {

		CategoryDataset dataset = DatasetUtils.createCategoryDataset(rowKeys, ydata, xdata);
		JFreeChart chart = ChartFactory.createBarChart(
				tiltle, //图表上方的标题
				"", // 目录轴的显示标签
				"Score", // 数值轴的显示标签
				dataset,  //图表的数据集合
				PlotOrientation.HORIZONTAL, //显示方向
				true,//是否显示图例如图一的均值、差值
				true, // 是否生成工具
				false); // 是否生成URL链接
		CategoryPlot plot = chart.getCategoryPlot();
		/*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 底部汉字乱码的问题 
		chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 12));
		// 设置标题字体 
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
		// textTitle.setBackgroundPaint(Color.LIGHT_GRAY);//标题背景色 
		// textTitle.setPaint(Color.cyan);//标题字体颜色 

		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// 设置刻度必须从0开始
		vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("0.00");
		vn.setNumberFormatOverride(df);
		// 数据轴数据标签的显示格式
		CategoryAxis domainAxis = plot.getDomainAxis();
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// // 横轴上的
		// Lable
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 22);
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
		// 横轴上的 Lable 是否完整显示
		// domainAxis.setVerticalCategoryLabels(false);
		plot.setDomainAxis(domainAxis);
		// plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 将类型放到上面
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
		// 将默认放到左边的数值放到右边
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		plot.setBackgroundPaint(new Color(255, 255, 255));
		ValueAxis rangeAxis = plot.getRangeAxis();
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.01);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.01);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.01);

		// 是否自适应范围
		// rangeAxis.setAutoRange(true);
		plot.setRangeAxis(rangeAxis);
		BarRenderer renderer = new BarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		renderer.setMinimumBarLength(30);
		renderer.setDefaultOutlinePaint(Color.BLACK);
		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.1);

		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// 设置柱的数值可见
		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_RIGHT));
		renderer.setItemLabelAnchorOffset(55D);// 设置柱形图上的文字偏离值
		//renderer.setDefaultItemLabelsVisible(true);
		// 设置柱子上的百分比字体和大小
		renderer.setDefaultItemLabelFont(new Font("黑体", Font.PLAIN, 18));
		// 设置柱为平面的而不是立体的
		renderer.setBarPainter(new StandardBarPainter());
		// 设置柱的颜色
		renderer.setSeriesPaint(0, new Color(43, 111, 213));

		renderer.setSeriesPaint(1, new Color(255,102,0));
		plot.setRenderer(renderer);
		// 设置柱的透明度(如果是3D的必须设置才能达到立体效果，如果是2D的设置则使颜色变淡)
		// plot.setForegroundAlpha(0.65f);
		FileOutputStream fos_jpg = null;
		try {

			isChartPathExist(path);
			String chartName = path + charName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtils.writeChartAsPNG(fos_jpg, chart, 800, 1000, true, 50);
			return chartName;
		} catch (IOException e) {
			System.out.println("生成图片错误");
			e.printStackTrace();
			return null;
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 柱状图,折线图数据集
	 */
	public static CategoryDataset getBarData(double[][] data, String[] rowKeys, String[] columnKeys) {
		return DatasetUtils.createCategoryDataset(rowKeys, columnKeys, data);
	}

	/**
	 * 堆栈柱状图
	 *
	 * @param dataset
	 * @param xName
	 * @param yName
	 * @param chartTitle
	 * @param charName
	 * @return
	 */
	public static String createStackedBarChart(CategoryDataset dataset, String xdata, String ydata, String chartTitle,
											   String path, String charName) {

		// 2:JFreeChart对象
		JFreeChart chart = ChartFactory.createStackedBarChart(chartTitle, // 图表标题
				xdata, // 目录轴的显示标签
				ydata, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.HORIZONTAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false // 是否生成URL链接
		);
		/*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 底部汉字乱码的问题 
		chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 12));
		// 图例字体清晰
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.WHITE);
		// 2 ．2 主标题对象 主标题对象是 TextTitle 类型
		chart.setTitle(new TextTitle(chartTitle, new Font("隶书", Font.BOLD, 25)));
		// 2 ．2.1:设置中文
		// x,y轴坐标字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 20);
		// 2 ．3 Plot 对象 Plot 对象是图形的绘制结构对象
		CategoryPlot plot = chart.getCategoryPlot();
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		plot.setBackgroundPaint(new Color(255, 255, 255));
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 将默认放到左边的数值放到右边
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// 设置最大值是1
		vn.setUpperBound(1);
		// 设置数据轴坐标从0开始
		// vn.setAutoRangeIncludesZero(true);
		// 数据显示格式是百分比
		DecimalFormat df = new DecimalFormat("0.00%");
		vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
		// DomainAxis （区域轴，相当于 x 轴）， RangeAxis （范围轴，相当于 y 轴）
		CategoryAxis domainAxis = plot.getDomainAxis();

		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值

		// x轴坐标太长，建议设置倾斜，如下两种方式选其一，两种效果相同
		// 倾斜（1）横轴上的 Lable 45度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 倾斜（2）Lable（Math.PI 3.0）度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));

		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示

		plot.setDomainAxis(domainAxis);

		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);

		// Renderer 对象是图形的绘制单元
		StackedBarRenderer renderer = new StackedBarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.1);
		// 设置柱的边框颜色
		//renderer.setDefaultOutlinePaint(Color.BLACK);
		// 设置柱的边框可见
		//renderer.setDrawBarOutline(true);

		// 设置柱的颜色
		renderer.setSeriesPaint(0, new Color(43, 111, 213));
		renderer.setSeriesPaint(1, new Color(255,102,0));
		// 显示每个柱的数值，并修改该数值的字体属性 //在柱上显示百分比
		renderer.setIncludeBaseInRange(true);
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", DecimalFormat.getPercentInstance()));
		renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_RIGHT));
		//renderer.setItemLabelAnchorOffset(-13D);// 设置柱形图上的文字偏离值
		renderer.setDefaultItemLabelsVisible(true);
		// 设置柱子上的百分比字体和大小
		renderer.setDefaultItemLabelFont(new Font("黑体", Font.PLAIN, 18));

		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.0);
		// 设置柱为平面的而不是立体的
		renderer.setBarPainter(new StandardBarPainter());
		plot.setRenderer(renderer);
		// 设置柱的透明度(如果是3D的必须设置才能达到立体效果，如果是2D的设置则使颜色变淡)
		// plot.setForegroundAlpha(0.65f);

		FileOutputStream fos_jpg = null;
		try {
			isChartPathExist(path);
			String chartName = path + charName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtils.writeChartAsPNG(fos_jpg, chart, 800, 1000, true, 10);
			return chartName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void isChartPathExist(String chartPath) {
		File file = new File(chartPath);
		if (!file.exists()) {
			file.mkdirs();
			// log.info("CHART_PATH="+CHART_PATH+"create.");
		}
	}
	public static void main(String[] args) {
		double[][] xData ={{0.25,0.35},{0.75,0.65}};
		String[] yData = {"学院1","学院2"};
		String[] rowKeys={"正常","异常"};
		String title = "教师考勤情况";
		CategoryDataset dataset = PdfUtil.getBarData(xData, rowKeys, yData);
		String path = "G:\\reortTest";
		createStackedBarChart(dataset, "学院列表", "数据占比", title, path, "2.jpg");
	}
}
