package com.jf.jf_isomc1000.util.pdf;
/**
 * 图表实体
 * @author zhongwei
 *
 */
public class ChartData<T,Y> {
	/**
	 * X轴数据
	 */
	private T xData;
	/**
	 * Y轴数据
	 */
	private Y yData;
	
	private Integer totalNum;
	public T getxData() {
		return xData;
	}
	public void setxData(T xData) {
		this.xData = xData;
	}
	public Y getyData() {
		return yData;
	}
	public void setyData(Y yData) {
		this.yData = yData;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
}
