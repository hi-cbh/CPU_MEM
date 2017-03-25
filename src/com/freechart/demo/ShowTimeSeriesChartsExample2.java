package com.freechart.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

import com.until.info.PrintCPUAndMen;



/**
 * 不使用线程的方式，获取并显示CPU内存， 在139在后台运行时，出现CPU占用率正常，但在运行时，获取的精度受到影响，导致获取进度较低
 * 
 * @author Administrator
 * 
 */
public class ShowTimeSeriesChartsExample2 extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double MaxValue = 350.0; // 显示数据轴最大值
	private static final int WIGTH = 600;
	private static final int HIGHT = 300;
	private static boolean isRun = false;
	DynamicResource dr  = null;
	
	
	private static final double MinValue = 0.0; // 显示数据轴最小值
	private static TimeSeries series1;
	private static TimeSeries series2;
	private static TimeSeries series3;
	private static TimeSeries series31;
	private static TimeSeries series32;
	private static TimeSeries series4;

	public ShowTimeSeriesChartsExample2() {

	}

	public static void main(String[] args) {
		ShowTimeSeriesChartsExample2 example = new ShowTimeSeriesChartsExample2();
		example.setTitle("时序图");
		example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		example.setBounds(100, 100, 1250, 750);
		example.getContentPane().add(example.createUI());
		example.setVisible(true);
		//example.dynamicRun();
		
		
		
	}

	/**
	 * 创建图形面板
	 * 
	 * @return
	 */
	public JPanel createUI() {
		// TimeSeries series = new TimeSeries("时序表标题", Millisecond.class);
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

		JFreeChart chart = createChart(dataset, "CPU(%)", "time", "", MinValue,
				100);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(WIGTH, HIGHT));
		panel.add(chartPanel, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						20, 20, 10, 10), 0, 0));

		series2 = new TimeSeries("PrivateDirty", Millisecond.class);
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(series2);
		JFreeChart chart2 = createChart(dataset2, "内存(M)", "time", "",
				MinValue, MaxValue);
		panel = new JPanel();
		ChartPanel chartPanel2 = new ChartPanel(chart2);
		chartPanel2.setPreferredSize(new Dimension(WIGTH, HIGHT));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel2, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));

		series3 = new TimeSeries("上传下载流量", Millisecond.class);
		//series31 = new TimeSeries("下载流量", Millisecond.class);
		//series32 = new TimeSeries("上传流量", Millisecond.class);
		TimeSeriesCollection dataset3 = new TimeSeriesCollection();
		dataset3.addSeries(series3);
//		dataset3.addSeries(series31);
//		dataset3.addSeries(series32);
		JFreeChart chart3 = createChart(dataset3, "WIFI流量(M)", "time", "",
				MinValue, 100);
		panel = new JPanel();
		ChartPanel chartPanel3 = new ChartPanel(chart3);
		chartPanel3.setPreferredSize(new Dimension(WIGTH, HIGHT));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel3, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));

		
		
		series4 = new TimeSeries("上传下载流量", Millisecond.class);
		TimeSeriesCollection dataset4 = new TimeSeriesCollection();
		dataset4.addSeries(series4);
		JFreeChart chart4 = createChart(dataset4, "4G流量(M)", "time", "", MinValue, 200);
		panel = new JPanel();
		ChartPanel chartPanel4 = new ChartPanel(chart4);
		chartPanel4.setPreferredSize(new Dimension(WIGTH, HIGHT));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel4, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 20, 20), 0, 0));

		/**
		 * gridx = 2; // X2 gridy = 0; // Y0 gridwidth = 1; // 横占一个单元格
		 * gridheight = 1; // 列占一个单元格 weightx = 0.0; // 当窗口放大时，长度不变 weighty =
		 * 0.0; // 当窗口放大时，高度不变 anchor = GridBagConstraints.NORTH; //
		 * 当组件没有空间大时，使组件处在北部 fill = GridBagConstraints.BOTH; // 当格子有剩余空间时，填充空间
		 * insert = new Insets(0, 0, 0, 0); // 组件彼此的间距 ipadx = 0; //
		 * 组件内部填充空间，即给组件的最小宽度添加多大的空间 ipady = 0; // 组件内部填充空间，即给组件的最小高度添加多大的空间 new
		 * GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,
		 * weighty, anchor, fill, insert, ipadx, ipady);
		 */

		JPanel jp = new JPanel();
		
		JLabel jlpackage = new JLabel("APK包名：");
		jlpackage.setFont(new Font("宋体",Font.BOLD, 20));
		
		JTextField jtf1 = new JTextField(20);
		jtf1.setFont(new Font("宋体",Font.BOLD, 16));
		jtf1.setBounds(10,10,50,15);
		
	
		JButton jb = new JButton(" 开始 ");
		jb.setFont(new Font("宋体",Font.BOLD, 18));
		
		JButton jb2 = new JButton(" 停止 ");
		jb2.setFont(new Font("宋体",Font.BOLD, 18));
		
		jp.add(jlpackage);
		jp.add(jtf1);
		jp.add(jb);
		jp.add(jb2);
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainPanel.add(jp);
		jtf1.setText("cn.cj.pe");
		jtf1.setEditable(true);
		
		jb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!isRun){
					isRun = true;
					dr = new DynamicResource();
					dr.start();
				}
					
				System.out.println("isRun:" + isRun);
			}
		});
		
		jb2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(isRun){
					isRun = false;
					dr.stop();
					dr = null;
				}
					
				System.out.println("isRun:" + isRun);
			}
		});
		
		return mainPanel;
	}

	
	public class DynamicResource extends Thread{

		@Override
		public void run() {
			dynamicRun();
		}
	
	}
	
	
	/**
	 * 动态运行
	 */
	public void dynamicRun() {
		double cpu = 0.0;
		double mem = 0.0;
		double flow4G = 0.0;
		double flowWIFI = 0.0;
		
		System.out.println("isRun:" + isRun);
			while (isRun) {
				cpu = getCPUValue();
				mem = getMemValue();
				flow4G = getFlowValue4G();
				flowWIFI = getFlowValue();
				
				
				if(cpu == -1 || mem == -1 || flow4G == -1|| flowWIFI == -1){
					JOptionPane.showMessageDialog(new JFrame(), "APP是否启动，或设备是否连接！");
					break;
				}
				
				series1.add(new Millisecond(), cpu);

				series2.add(new Millisecond(), mem);

				series3.add(new Millisecond(), flow4G);

				series4.add(new Millisecond(), flowWIFI);
				
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
	 * 
	 * @return
	 */
	public static double getFlowValue() {

		String flow = PrintCPUAndMen.GetFlow("cn.cj.pe","rmnet_data0");
		if(flow.equals("")){
			return -1;
		}
		String s[] = flow.split("#");
		double down = Double.parseDouble(s[0].trim())/1024;
		double up = Double.parseDouble(s[1].trim())/1024;
		double all = (down + up)/1024; //M
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(Double.parseDouble(df.format(all)));
		return Double.parseDouble(df.format(all));

	}
	
	/**
	 * 返回处理后的结果
	 * 
	 * @return
	 */
	public static double getFlowValue4G() {

		String flow = PrintCPUAndMen.GetFlow("cn.cj.pe","wlan0");
		if(flow.equals("")){
			return -1;
		}
		String s[] = flow.split("#");
		double down = Double.parseDouble(s[0].trim())/1024;
		double up = Double.parseDouble(s[1].trim())/1024;
		double all = (down + up)/1024; //M
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(Double.parseDouble(df.format(all)));
		return Double.parseDouble(df.format(all));

	}
	
	/**
	 * 返回处理后的结果
	 * 
	 * @return
	 */
	public static double getCPUValue() {
		String cpu = PrintCPUAndMen.getCPU("cn.cj.pe");
		if(!cpu.contains("%")){
			return -1;
		}
		String s[] = cpu.split("%");
		System.out.println("getCPUValue:" + Double.parseDouble(s[0]));
		return Double.parseDouble(s[0]);

	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return
	 */
	public static double getMemValue() {
		DecimalFormat df = new DecimalFormat("#");
		double mem = PrintCPUAndMen.getMemoryPrivateDirty("cn.cj.pe");
		if(mem == -1){
			return -1;
		}
		mem = mem / 1024;
		
		String memory = df.format(mem);
		System.out.println("getMemValue:" + Double.parseDouble(memory));
		return Double.parseDouble(memory);
	}

	/**
	 * 根据结果集构造JFreechart报表对象
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createChart(XYDataset dataset, String titlename,
			String xname, String yname, double min, double max) {
		// 创建JFreeChart对象
		JFreeChart result = ChartFactory.createTimeSeriesChart("", xname,
				yname, dataset, true, false, false);

		result.setBackgroundPaint(Color.white);
		// 边框可见
		result.setBorderVisible(true);
		TextTitle title = new TextTitle(titlename,
				new Font("宋体", Font.BOLD, 20));
		// 解决曲线图片标题中文乱码问题
		result.setTitle(title);

		// 设置图表样式
		XYPlot plot = (XYPlot) result.getPlot();
		ValueAxis axis = plot.getDomainAxis();
		// 数据区的背景图片背景色
		plot.setBackgroundPaint(Color.lightGray);
		// 分类轴网格线条颜色
		plot.setDomainGridlinePaint(Color.white);
		// 数据轴网格线条颜色
		plot.setRangeGridlinePaint(Color.white);
		// 坐标轴到数据区的间距
		plot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		// 自动设置数据轴数据范围
		axis.setAutoRange(true);

		// 设置时间轴显示的数据
		axis.setFixedAutoRange(20000D); // /点与点之间的间距
		// 数据轴固定数据范围
		axis = plot.getRangeAxis();
		// 设置是否显示数据轴
		axis.setRange(min, max);

		// Y轴
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		setNumberAxis(numberaxis);

		// x轴
		ValueAxis domainAxis = plot.getDomainAxis();
		setDomainAxis(domainAxis);

		XYItemRenderer xyitemrenderer = plot.getRenderer();
		if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
			// 数据点可见
			xylineandshaperenderer.setBaseShapesVisible(true);
			// 数据点是实心点
			xylineandshaperenderer.setBaseShapesFilled(true);
			// 数据点显示数据
			xylineandshaperenderer
					.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
			xylineandshaperenderer.setBaseItemLabelsVisible(true);
		}

		return result;
	}

	/**
	 * 设置X轴
	 * 
	 * @param domainAxis
	 */
	private static void setDomainAxis(ValueAxis domainAxis) {
		domainAxis.setVisible(false);
		domainAxis.setAutoTickUnitSelection(true);
		domainAxis.setTickMarksVisible(false);
	}

	/**
	 * 设置Y轴
	 * 
	 * @param numberAxis
	 */
	private static void setNumberAxis(NumberAxis numberaxis) {
		// numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// // 是否显示零点
		// numberaxis.setAutoRangeIncludesZero(true);
		// numberaxis.setAutoTickUnitSelection(false);
		// // 解决Y轴标题中文乱码
		// numberaxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 14));
		// // numberaxis.setTickUnit(new NumberTickUnit(10000));//Y轴数据间隔
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}