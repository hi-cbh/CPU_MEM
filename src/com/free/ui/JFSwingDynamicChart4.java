package com.free.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

public class JFSwingDynamicChart4 extends JFrame implements ActionListener {
	private TimeSeries series;
	private TimeSeries series2;
	private TimeSeries series3;
	private double lastValue = 0.0;
	private static final double MaxValue = 300.0;  //显示数据轴最大值
	private static final double MIxValue = 0.0;    //显示数据轴最小值
	private static double heapgrowthlimit = 0.0; //查看单个应用程序最大内存限制
	
	/**
	 * 构造
	 */
	public JFSwingDynamicChart4() {
		getContentPane().setBackground(Color.green);
		
		//heapgrowthlimit = PrintCPUAndMen.getHeapgrowthlimit()/1024;
		
	}

	/**
	 * 创建应用程序界面
	 */
	@SuppressWarnings("deprecation")
	public void createUI() {
		this.series = new TimeSeries("PrivateDirty", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(this.series);
		ChartPanel panel1 = new ChartPanel(createChart(dataset));
		panel1.setPreferredSize(new java.awt.Dimension(500, 270));
	
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(panel1);

		add(buttonPanel, BorderLayout.SOUTH);
	
		
	}

	/**
	 * 根据结果集构造JFreechart报表对象
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createChart(XYDataset dataset) {
		//创建JFreeChart对象
		JFreeChart result = ChartFactory.createTimeSeriesChart("", "time",
				"%", dataset, true, false, false);
		
		result.setBackgroundPaint(Color.white);
		// 边框可见
		result.setBorderVisible(true);
		TextTitle title = new TextTitle("内存(M)时间序列图", new Font("宋体", Font.BOLD, 20));
		// 解决曲线图片标题中文乱码问题
		result.setTitle(title);		
		
		//设置图表样式
		XYPlot plot = (XYPlot) result.getPlot();
		ValueAxis axis = plot.getDomainAxis();
		//数据区的背景图片背景色
		plot.setBackgroundPaint(Color.lightGray);
		//分类轴网格线条颜色
		plot.setDomainGridlinePaint(Color.white);
		//数据轴网格线条颜色
		plot.setRangeGridlinePaint(Color.white);
		//坐标轴到数据区的间距
		plot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		
		
		//自动设置数据轴数据范围
		axis.setAutoRange(true);
		//设置时间轴显示的数据
		axis.setFixedAutoRange(60000D);
		//数据轴固定数据范围
		axis = plot.getRangeAxis();
		//设置是否显示数据轴
		axis.setRange(MIxValue, MaxValue);

		// Y轴
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		setNumberAxis(numberaxis);
		
		// x轴
		ValueAxis domainAxis = plot.getDomainAxis();
		setDomainAxis(domainAxis);
		
		
		XYItemRenderer xyitemrenderer = plot.getRenderer();
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
		
		return result;
	}

	/**
	 * 设置X轴
	 * @param domainAxis
	 */
	private static void setDomainAxis(ValueAxis domainAxis){
		domainAxis.setVisible(false);
		domainAxis.setAutoTickUnitSelection(true);
		domainAxis.setTickMarksVisible(false);
	}
	
	/**
	 * 设置Y轴
	 * @param numberAxis
	 */
	private static void setNumberAxis(NumberAxis numberaxis){
//		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		// 是否显示零点
//		numberaxis.setAutoRangeIncludesZero(true);
//		numberaxis.setAutoTickUnitSelection(false);
//		// 解决Y轴标题中文乱码
//		numberaxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 14));
//		// numberaxis.setTickUnit(new NumberTickUnit(10000));//Y轴数据间隔
	}
	
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * 动态运行
	 */
	public void dynamicRun() {
		while (true) {
			double men = PrintCPUAndMen.getMemoryPrivateDirty("cn.cj.pe");

			DecimalFormat df = new DecimalFormat("#");
			String memory = df.format(men/1024);
			System.out.println(memory);
			double factor = Double.parseDouble(memory);
			//System.out.println("factor:" +factor);
			this.lastValue = factor;
			this.series.add(new Millisecond(), this.lastValue);
			
			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 主函数入口
	public static void main(String[] args) {
	
		JFSwingDynamicChart4 jsdChart = new JFSwingDynamicChart4();
		jsdChart.setTitle("Swing动态折线图");
		jsdChart.createUI();
		jsdChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jsdChart.setBounds(100, 100, 900, 600);
		jsdChart.setVisible(true);
//		Color c=new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)); 
		
		jsdChart.dynamicRun();
	}

}

