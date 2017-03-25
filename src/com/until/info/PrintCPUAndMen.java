package com.until.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintCPUAndMen {

	/**
	 * 获取PID ,注意139邮箱有两个进程，一个是推送，一个是应用。 adb shell ps | findStr cn.cj.pe, adb
	 * shell top | findStr cn.cj.pe; cn.cj.pe:richpush
	 * 
	 * 当个程序最大内存，manifest设置后，可以超出，注意超出是容易出现OOM adb shell getprop | findStr
	 * heapgrowthlimit
	 * 
	 * 总共使用的内存 adb shell dumpsys meminfo cn.cj.pe | findStr TOTAL
	 * 
	 * 查看单个应用程序最大内存限制 adb shell getprop|grep heapgrowthlimit
	 * 
	 * 应用启动后分配的初始内存 adb shell getprop|grep dalvik.vm.heapstartsize
	 * 
	 * 单个java虚拟机最大的内存限制 adb shell getprop|grep dalvik.vm.heapsize
	 **/

	public static void main(String[] args) {
		// System.out.println("getCPU: " + getCPU("cn.cj.pe"));

		for(int i = 0; i < 20; i++){
			System.out.println("Flow:" + GetFlow("cn.cj.pe","eth0"));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		


	}

	/**
	 * 获取应用PID
	 * 
	 * @param path
	 * @return
	 */
	public static boolean adbPs(String packageName) {
		ProcessBuilder pb1 = new ProcessBuilder("adb", "shell", "ps", "|",
				"grep", packageName);
		StringBuffer result = new StringBuffer();
		boolean isexists = false;

		try {
			Process process = pb1.start();
			Scanner scanner = new Scanner(process.getInputStream());
			if (scanner.hasNextLine()) {
				result.append(scanner.nextLine());
			}
			System.out.println("adbPs:" + result + ";");
			scanner.close();
			if (result.toString().contains(packageName)) {
				isexists = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isexists;
	}

	public static String getPID(String PackageName) {

		Process proc = null;
		String PID = "";
		try {
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec("adb shell ps |grep  " + PackageName);

			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line + " ");

			}
			String str1 = stringBuffer.toString().trim();
			String[] s = str1.split("\\s+"); // 以空格分隔

			PID = s[1];
			//System.out.println("PID:" + PID);
			PID = PID.trim();

			return PID;
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				proc.destroy();
			} catch (Exception e2) {
			}
		}

		return PID;
	}

	public static String PID(String PackageName) {

		Process proc = null;
		String str3 = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec("adb shell ps |grep  " + PackageName);

			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line + " ");

			}
			String str1 = stringBuffer.toString().trim();
			String[] s = str1.split("\\s+"); // 以空格分隔
			System.out.println("s[0]=" + s[0]);
			System.out.println("s[1]=" + s[1]);
			System.out.println("s[2]=" + s[2]);
			System.out.println("s[3]=" + s[3]);
			String str2 = str1.substring(str1.indexOf(" " + PackageName) - 46,
					str1.indexOf(" " + PackageName));
			String PID = str2.substring(0, 7);
			System.out.println("PID:" + PID);
			PID = PID.trim();

			str3 = PID;
			System.out.println("str3:" + str3);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				proc.destroy();
			} catch (Exception e2) {
			}
		}

		return str3;
	}

	/**
	 * 获取CPU占用率 pid或packageName
	 * 
	 * @param PackageName
	 * @return
	 */
	public static String getCPU(String PackageName) {

		Process proc = null;
		String CPU = "";

		try {
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec("adb shell top -n 1 -d 0.5 | grep "
					+ PackageName);

			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line + " ");

			}

			String str1 = stringBuffer.toString();
			str1 = str1.trim();
			// System.out.println("str1:" + str1);
			String[] s = str1.split("\\s+");
			str1.split("\\s+"); // 以空格分隔

			CPU = s[2];
			// System.out.println("PID:" + CPU);
			CPU = CPU.trim();

			return CPU;
		} catch (Exception e) {
			CPU = "-1";
			// System.err.println(e);
		} finally {
			try {
				proc.destroy();
			} catch (Exception e2) {

			}
		}

		return CPU;

	}

	/**
	 * 查看单个应用程序最大内存限制
	 * 
	 * @param
	 * @return
	 */
	public static double getHeapgrowthlimit() {

		double Heap = 0;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime
					.exec("adb shell getprop | grep heapgrowthlimit");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString();
				str1 = str1.trim();

				// 使用正则表达式
				Pattern p = Pattern.compile("\\d*");
				Matcher m = p.matcher(str1);

				// 截取数字
				while (m.find()) {
					if (!"".equals(m.group())) {
						str1 = m.group();
						// System.out.println("str1" + str1);
					}
				}

				// System.out.println("str3:"+str1);
				Heap = Double.parseDouble(str1);
				Heap = Heap * 1024;// 单位 K
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return Heap;
	}

	/**
	 * 单个java虚拟机最大的内存限制；单位 K
	 * 
	 * @param
	 * @return
	 */
	public static double getHeapsizeMax() {

		double Heap = 0;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime
					.exec("adb shell getprop | grep dalvik.vm.heapsize");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString();

				// 使用正则表达式
				Pattern p = Pattern.compile("\\d*");
				Matcher m = p.matcher(str1);

				// 截取数字
				while (m.find()) {
					if (!"".equals(m.group())) {
						str1 = m.group();
						// System.out.println("str1" + str1);
					}
				}

				str1 = str1.trim();
				// System.out.println("str1:"+str1);
				Heap = Double.parseDouble(str1);
				Heap = Heap * 1024;// 单位 K
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return Heap;
	}

	/**
	 * 当前Dalvik Head 大小，如果该值超过heapgrowthlimit，可能出现OOM错误
	 * 
	 * @param PackageName
	 * @return
	 */
	public static double getCurrentDalvikHeadSize(String PackageName) {

		double Heap = 0;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("adb shell dumpsys meminfo "
					+ PackageName + " | grep Dalvik");
			// adb shell dumpsys meminfo cn.cj.pe | findStr TOTAL 总共使用的内存
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString().trim();
				// System.out.println("str1:"+str1);
				String[] s = str1.split("\\s+");
				String str2 = s[6];

				str2 = str2.trim();
				Heap = Double.parseDouble(str2);
				System.out.println("Heap" + Heap);
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return Heap;
	}

	/**
	 * 进程的获取内存的PSS（实际占用内存）
	 * 
	 * @param PackageName
	 * @return
	 */
	public static double getMemoryPSS(String PackageName) {

		double Heap = 0;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("adb shell dumpsys meminfo "
					+ PackageName + " | grep TOTAL");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString();
				// System.out.println("str1:"+str1);
				String[] s = str1.split("\\s+");
				String str2 = s[2];
				str2 = str2.trim();
				Heap = Double.parseDouble(str2);
				// System.out.println("Heap:"+Heap);
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return Heap;
	}

	/**
	 * 进程的获取内存的PrivateDirty（实际占用内存）,单位 K
	 * 
	 * @param PackageName
	 * @return
	 */
	public static double getMemoryPrivateDirty(String PackageName) {

		double Heap = 0;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("adb shell dumpsys meminfo "
					+ PackageName + " | grep TOTAL");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString();
				str1 = str1.trim();
				// System.out.println("str1:"+str1);
				String[] s = str1.split("\\s+");
				String str2 = s[2];
				str2 = str2.trim();
				Heap = Double.parseDouble(str2);
				// System.out.println("Heap:"+Heap);
				// Thread.sleep(100);
			} catch (InterruptedException e) {
				
				System.err.println(e);
				return -1;
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");
			return -1;
		}
		return Heap;
	}

	/**
	 * 进程的获取内存的PSS（实际占用内存）,单位 K
	 * 
	 * @param PackageName
	 * @return
	 */
	public static List<String> getMemory(String PackageName) {

		List<String> ls = new ArrayList<String>();

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("adb shell dumpsys meminfo "
					+ PackageName + " | grep TOTAL");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");

				}

				String str1 = stringBuffer.toString();
				// System.out.println("str1:"+str1);
				String[] s = str1.split("\\s+");

				String str2 = s[2];
				str2 = str2.trim();

				String str3 = s[3];
				str3 = str3.trim();

				ls.add(str2);
				ls.add(str3);

			} catch (InterruptedException e) {
				System.err.println(e);
				return null;
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		}

		catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return ls;
	}

	/**
	 * wifi下载流量
	 * @param PackageName
	 * @return
	 */
	public static double GetDownFlow(String PackageName) {
		double str3 = 0;
		String Pid = getPID(PackageName);
		if(Pid.equals("")){
			return 0.0;
		}
		
		
		try {
			Runtime runtime = Runtime.getRuntime();
			/* Process proc2 = runtime.exec(""); */
			Process proc = runtime.exec("adb shell cat /proc/" + Pid
					+ "/net/dev");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
					// JOptionPane.showMessageDialog(new JFrame(),
					// "哥们抱歉，好像出问题了！关掉重试吧！");
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");
				}

				String str1 = stringBuffer.toString();
				String str2 = str1.substring(str1.indexOf("wlan0:"),
						str1.indexOf("wlan0:") + 90);
				
				
				
				// 接收字节：
				String str4 = str2.substring(7, 16);
				str4 = str4.trim();
				int b = Integer.parseInt(str4);
				str3 = b / 1024;
				// System.out.println(str3);
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		} catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return str3;
	}

	/**
	 * wifi下载流量
	 * @param PackageName
	 * @return
	 */
	public static String GetFlow(String PackageName) {
		String str3 = "";
		String Pid = getPID(PackageName);
		if(Pid.equals("")){
			return "";
		}
		
		
		try {
			Runtime runtime = Runtime.getRuntime();
			/* Process proc2 = runtime.exec(""); */
			Process proc = runtime.exec("adb shell cat /proc/" + Pid
					+ "/net/dev | grep wlan0");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
					// JOptionPane.showMessageDialog(new JFrame(),
					// "哥们抱歉，好像出问题了！关掉重试吧！");
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");
				}

				String str1 = stringBuffer.toString().trim();
				
				System.out.println(str1);
				
				
				String[] s = str1.split("\\s+");
				
				String down = s[1];
				String up = s[9];
				
				//System.out.println("down:" + down);
				//System.out.println("up:" + up);
				str3 = down + "#"+up;
				
//				String str2 = str1.substring(str1.indexOf("wlan0:"),
//						str1.indexOf("wlan0:") + 90);
//				
//				
//				
//				// 接收字节：
//				String str4 = str2.substring(7, 16);
//				str4 = str4.trim();
//				int b = Integer.parseInt(str4);
//				str3 = b / 1024;
//				// System.out.println(str3);
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		} catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return str3;
	}
	/**
	 * 不同手机、Wifi、4G dev都不一样；eth0,eth1;rmnet_data0
	 * @param PackageName
	 * @return
	 */
	public static String GetFlow(String PackageName, String dev) {
		String str3 = "";
		String Pid = getPID(PackageName);
		if(Pid.equals("")){
			return "";
		}
		
		
		try {
			Runtime runtime = Runtime.getRuntime();
			/* Process proc2 = runtime.exec(""); */
			Process proc = runtime.exec("adb shell cat /proc/" + Pid
					+ "/net/dev | grep " + dev);
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
					// JOptionPane.showMessageDialog(new JFrame(),
					// "哥们抱歉，好像出问题了！关掉重试吧！");
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");
				}

				String str1 = stringBuffer.toString().trim();
				
				System.out.println(str1);
				
				
				String[] s = str1.split("\\s+");
				
				String down = s[1];
				String up = s[9];
				
				//System.out.println("down:" + down);
				//System.out.println("up:" + up);
				str3 = down + "#"+up;
				
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		} catch (Exception StringIndexOutOfBoundsException) {
			System.out.print("请检查设备是否连接");

		}
		return str3;
	}
	
}
