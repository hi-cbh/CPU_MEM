package com.free.ui;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

public class JFSwingDynamicChart2 extends JFrame implements ActionListener {
	private TimeSeries series;
	private double lastValue = 0.0;
	
	/**
	 * 构造
	 */
	public JFSwingDynamicChart2() {
		getContentPane().setBackground(Color.green);
	}

	/**
	 * 创建应用程序界面
	 */
	public void createUI() {
		this.series = new TimeSeries("CPU", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
		ChartPanel chartPanel = new ChartPanel(createChart(dataset));
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(chartPanel);
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
		TextTitle title = new TextTitle("CPU(%)时间序列图", new Font("宋体", Font.BOLD, 20));
		// 解决曲线图片标题中文乱码问题
		result.setTitle(title);
		//解决图表底部中文乱码问题
		//result.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		
		
		//设置图表样式
		CategoryPlot  plot = (CategoryPlot ) result.getPlot();
		CategoryAxis  axis = plot.getDomainAxis();
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
		
		
        // 设置X轴
        CategoryAxis domainAxis = plot.getDomainAxis();   
        domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45

        // 设置Y轴
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); 
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数
        rangeAxis.setAutoRangeMinimumSize(1);   //最小跨度
        rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。   
        rangeAxis.setLowerBound(0);   //最小值显示0
        rangeAxis.setAutoRange(false);   //不自动分配Y轴数据
        rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));     // 设置坐标标记大小
        rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色
        rangeAxis.setTickUnit(new NumberTickUnit(10));//每10个刻度显示一个刻度值


		
		CategoryItemRenderer xyitemrenderer = plot.getRenderer();
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

	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * 动态运行
	 */
	public void dynamicRun() {
		while (true) {
			//double factor = 0.90 + 0.2 * Math.random();
			String cpu = PrintCPUAndMen.getCPU("cn.cj.pe");
			String s[] = cpu.split("%");
			
			double factor = Double.parseDouble(s[0]);
			System.out.println("factor:" +factor);
			this.lastValue = factor;
			System.out.println("this.lastValue:" +this.lastValue);
			//Millisecond now = new Millisecond();
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
		JFSwingDynamicChart_cpu jsdChart = new JFSwingDynamicChart_cpu();
		jsdChart.setTitle("Swing动态折线图");
		jsdChart.createUI();
		jsdChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jsdChart.setBounds(100, 100, 900, 600);
		jsdChart.setVisible(true);
//		Color c=new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)); 
		
		jsdChart.dynamicRun();
	}

}

