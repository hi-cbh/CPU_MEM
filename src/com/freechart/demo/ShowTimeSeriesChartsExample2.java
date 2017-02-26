package com.freechart.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
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

import com.free.ui.PrintCPUAndMen;
/**
 * 不使用线程的方式，获取并显示CPU内存，
 * 在139在后台运行时，出现CPU占用率正常，但在运行时，获取的精度受到影响，导致获取进度较低
 * @author Administrator
 *
 */
public class ShowTimeSeriesChartsExample2  extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double MaxValue = 300.0;  //显示数据轴最大值
	
	private static final double MinValue = 0.0;    //显示数据轴最小值
	private static TimeSeries series1; 
	private static TimeSeries series2; 
	private TimeSeries series3; 
	private TimeSeries series4; 
	
	public ShowTimeSeriesChartsExample2(){
		
	}
	
	

    public static void main(String[] args) {
    	ShowTimeSeriesChartsExample2 example = new ShowTimeSeriesChartsExample2();
    	example.setTitle("时序图");
    	example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	example.setBounds(100,100,1250,650);
    	example.getContentPane().add(example.createUI());
    	example.setVisible(true);
    	example.dynamicRun();
    }
    
    
    
    /**
     * 创建图形面板
     * @return
     */
	public JPanel createUI() {
		 //TimeSeries series = new TimeSeries("时序表标题", Millisecond.class); 
		/**
		 * 解决中文问题
		 */
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 20));
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        ChartFactory.setChartTheme(standardChartTheme);
         
         
        JPanel mainPanel = new JPanel();
       
        series1 = new TimeSeries("CPU", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		
        JFreeChart chart = createChart(dataset, "CPU(%)","time","",MinValue, 100);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        panel.add(chartPanel, BorderLayout.CENTER);
        mainPanel.add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(20, 20, 10, 10), 0, 0));
        
        
        
        
        series2 = new TimeSeries("PrivateDirty", Millisecond.class);
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(series2);
		JFreeChart chart2 = createChart(dataset2,"内存(M)","time","",MinValue, MaxValue);
        panel = new JPanel();
        ChartPanel chartPanel2 = new ChartPanel(chart2);
        chartPanel2.setPreferredSize(new Dimension(700, 300));
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel2, BorderLayout.CENTER);
        mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
         
//        chart = createChart(dataset,"内存(M)","time","M",MinValue, MaxValue);
//        panel = new JPanel();
//        ChartPanel chartPanel3 = new ChartPanel(chart);
//        chartPanel3.setPreferredSize(new Dimension(500, 300));
//        panel.setLayout(new BorderLayout());
//        panel.add(chartPanel3, BorderLayout.CENTER);
//        mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
//                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(10, 10, 10, 10), 0, 0));
//         
//        chart = createChart(dataset,"内存(M)","time","M",MinValue, MaxValue);
//        panel = new JPanel();
//        ChartPanel chartPanel4 = new ChartPanel(chart);
//        chartPanel4.setPreferredSize(new Dimension(500, 300));
//        panel.setLayout(new BorderLayout());
//        panel.add(chartPanel4, BorderLayout.CENTER);
//        mainPanel.add(panel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
//                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(10, 10, 20, 20), 0, 0));
//        
        /**
         * gridx = 2; // X2
			gridy = 0; // Y0
			gridwidth = 1; // 横占一个单元格
			gridheight = 1; // 列占一个单元格
			weightx = 0.0; // 当窗口放大时，长度不变
			weighty = 0.0; // 当窗口放大时，高度不变
			anchor = GridBagConstraints.NORTH; // 当组件没有空间大时，使组件处在北部
			fill = GridBagConstraints.BOTH; // 当格子有剩余空间时，填充空间
			insert = new Insets(0, 0, 0, 0); // 组件彼此的间距
			ipadx = 0; // 组件内部填充空间，即给组件的最小宽度添加多大的空间
			ipady = 0; // 组件内部填充空间，即给组件的最小高度添加多大的空间
			new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insert, ipadx, ipady);
         */

         return mainPanel;
	}
    
	/**
	 * 动态运行
	 */
	public void dynamicRun() {
		
		while (true) {
    		series1.add(new Millisecond(), getCPUValue()); 

    		series2.add(new Millisecond(), getMemValue());
    		
			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}




	/**
	 * 返回处理后的结果
	 * @return
	 */
	public static double getCPUValue(){
		String cpu = PrintCPUAndMen.getCPU("cn.cj.pe");
		String s[] = cpu.split("%");
		System.out.println("getCPUValue:"+Double.parseDouble(s[0]));
		return Double.parseDouble(s[0]);
		
	}
	
	/**
	 * 返回处理后的结果
	 * @return
	 */
	public static double getMemValue(){
		DecimalFormat df = new DecimalFormat("#");
		double men = PrintCPUAndMen.getMemoryPrivateDirty("cn.cj.pe")/1024;
		String memory = df.format(men);
		System.out.println("getMemValue:"+Double.parseDouble(memory));
		return Double.parseDouble(memory);
	}
	
	
    
    
	/**
	 * 根据结果集构造JFreechart报表对象
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createChart(XYDataset dataset, String titlename, String xname, String yname, double min, double max) {
		//创建JFreeChart对象
		JFreeChart result = ChartFactory.createTimeSeriesChart("", xname,
				yname, dataset, true, false, false);
		
		result.setBackgroundPaint(Color.white);
		// 边框可见
		result.setBorderVisible(true);
		TextTitle title = new TextTitle(titlename, new Font("宋体", Font.BOLD, 20));
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
		axis.setFixedAutoRange(20000D); ///点与点之间的间距
		//数据轴固定数据范围
		axis = plot.getRangeAxis();
		//设置是否显示数据轴
		axis.setRange(min, max);

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
	
	
	
	
	static class MyThread implements Runnable {
        public void run() {
        	while (true) {
        		series1.add(new Millisecond(), getCPUValue()); 
    			try {
    				Thread.currentThread();
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
        	
        	
              
        }
    }
	
	static class MyThread2 implements Runnable {
        public void run() {
        	series2.add(new Millisecond(), getMemValue());
        }
    }

}