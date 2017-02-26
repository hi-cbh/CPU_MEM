package com.free.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTick;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * 
 * @ClassName: JFreeChart_TimeSeriesChart
 * @author xialong
 * @date Jan 25, 2011 7:00:25 PM
 * @Description:
 *    JFreeChart生成时序图
 *
 */
public class JFreeChart_TimeSeriesChart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			print();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出图片
	 * @throws IOException
	 */
	public static void print() throws IOException{
		FileOutputStream fos = new  FileOutputStream("D://jfreechart//timeSeriesChart"+System.currentTimeMillis()+".jpg");
		
		ChartUtilities.writeChartAsJPEG(fos,//输出到那个流，
										1, //图片质量，0~1
										getJFreeChart(), //图表对象
										1200,//宽
										600,//高
										null//ChartRenderingInfo信息
										);
		fos.close();
	}
	
	/**
	 * 产生JFreeChart对象
	 * 
	 * @return
	 */
	public static JFreeChart getJFreeChart() {
		JFreeChart imgChart=null;
		// JFreeChart对象 参数：标题，目录轴显示标签，数值轴显示标签，数据集，是否显示图例，是否生成工具，是否生成URL连接
		
		//平面
		imgChart = ChartFactory.createTimeSeriesChart("", "X轴", "Y轴",
				getDataSet(), true, true, false);
		imgChart.setBackgroundPaint(Color.white);
		imgChart.setBorderVisible(true);// 边框可见
		TextTitle title = new TextTitle("时间序列图", new Font("宋体", Font.BOLD, 20));
		// 解决曲线图片标题中文乱码问题
		imgChart.setTitle(title);
		//解决图表底部中文乱码问题
		imgChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		//获得 plot : XYPlot!!
		XYPlot xyplot = (XYPlot)imgChart.getPlot(); 
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		
		//设置x轴坐标值斜着显示
		DateAxis dateAxis = new DateAxis("日期") {  
		     @SuppressWarnings("unchecked")  
		     protected List<DateTick> refreshTicksHorizontal(Graphics2D g2,  
		            Rectangle2D dataArea, RectangleEdge edge) {  
		         List ticks = super.refreshTicksHorizontal(g2, dataArea, edge);  
		         List<DateTick> newTicks = new ArrayList<DateTick>();  
		         for (Iterator it = ticks.iterator(); it.hasNext();) {  
		             DateTick tick = (DateTick) it.next();  
		             newTicks.add(new DateTick(tick.getDate(), tick.getText(),  
		                     TextAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT,  
		                    -Math.PI/3));  
		        }  
		        return newTicks;  
		    }  
		 }; 
		 xyplot.setDomainAxis(dateAxis);
		
		// Y轴
		NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
		setNumberAxis(numberaxis);
		
		// x轴
		ValueAxis domainAxis = xyplot.getDomainAxis();
		setDomainAxis(domainAxis);
		
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		if(xyitemrenderer instanceof XYLineAndShapeRenderer){
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
			//数据点可见
			xylineandshaperenderer.setBaseShapesVisible(true); 
			//数据点是实心点
			xylineandshaperenderer.setBaseShapesFilled(true); 
			//数据点显示数据
			xylineandshaperenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
			xylineandshaperenderer.setBaseItemLabelsVisible(true);
		}
		//对domain 轴上日期显示格式定义
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis(); 
		dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-DD"));
		return imgChart;
	}
	
	/**
	 * 设置X轴
	 * @param domainAxis
	 */
	private static void setDomainAxis(ValueAxis domainAxis){
		// 解决x轴坐标上中文乱码
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		// 解决x轴标题中文乱码
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		// 用于显示X轴刻度
		domainAxis.setTickMarksVisible(true);
	}
	
	/**
	 * 设置Y轴
	 * @param numberAxis
	 */
	private static void setNumberAxis(NumberAxis numberaxis){
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// 是否显示零点
		numberaxis.setAutoRangeIncludesZero(true);
		numberaxis.setAutoTickUnitSelection(false);
		// 解决Y轴标题中文乱码
		numberaxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 14));
		// numberaxis.setTickUnit(new NumberTickUnit(10000));//Y轴数据间隔
	}
	
	/**
	 * 产生数据源
	 * 
	 * @return
	 */
	private static XYDataset getDataSet() {
		Random rand = new Random();
		TimeSeriesCollection dateset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("JFreeChart",Day.class);
		Calendar cal = Calendar.getInstance();
		for(int i=1;i<=cal.get(Calendar.DAY_OF_MONTH);i++){
			timeSeries1.add(new Day(i,1,2010),rand.nextInt(10));
		}
		dateset.addSeries(timeSeries1);
		return dateset;
	}
}
