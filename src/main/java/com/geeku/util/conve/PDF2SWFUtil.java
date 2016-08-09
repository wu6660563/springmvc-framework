package com.geeku.util.conve;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类:将PDF格式文件格式转换为Flash的swf文件
 * @author JiangJi
 */
public class PDF2SWFUtil {
	private static Logger logger = LoggerFactory.getLogger(PDF2SWFUtil.class);

	/**
	 * 利用 SWFTools 工具将 pdf 转换成 swf ，转换完后的 swf 文件与 pdf 同名
	 * 
	 * @param fileDir PDF文件存放路径（包括文件名）
	 * @param outPutPath 生成的SWF文件存放路径
	 * @param swftoolHome SWFTools安装路径
	 * @throws IOException
	 */
	public static synchronized void pdf2swf(String fileDir ,String outPutPath,String swftoolHome)
			throws Exception {
		try {
			Integer dirIndex = fileDir.lastIndexOf("/")==-1?fileDir.lastIndexOf("\\"):fileDir.lastIndexOf("/");
			// 文件路径
			String filePath = fileDir.substring(0,dirIndex);
			// 文件名，不带后缀
			String fileName = fileDir.substring((filePath.length() + 1),fileDir.lastIndexOf("."));;
			
			Process pro = null;
			if (isWindowsSystem()) {// 如果是 windows 系统
				//String cmd = SWFTOOLPATH + " \"" + fileDir + "\" -o \"" + outPutPath + "/" + fileName + ".swf\" -T 9";// 命令行命令(只生成一个总的swf文件)
				//String cmd = swftoolPath + " \"" + fileDir + "\" -o \"" + outPutPath + "/" + fileName + "_%.swf\" -f -T 9 -t -s storeallcharacters ";//每页生成一个swf文件
				//String cmd = swftoolHome +"  "+ fileDir + " -o " + outPutPath + File.separator + fileName + "_%.swf -f -T 9 -t -s storeallcharacters ";//每页生成一个swf文件
				String cmdStr = swftoolHome + " " + fileDir + " -o " + outPutPath + File.separator + fileName + ".swf -f -T 9 -t -s storeallcharacters ";//生成一个swf文件
				System.out.println("---"+cmdStr);
				pro = Runtime.getRuntime().exec(cmdStr);// Runtime 执行后返回创建的进程对象
			} else {
				// 如果是 linux 系统 , 路径不能有空格，而且一定不能用双引号，否则无法创建 进程
				String[] cmd = new String[3];
				cmd[0] = swftoolHome;
				cmd[1] = fileDir;
				cmd[2] = outPutPath + "/" + fileName + ".swf";
				
				pro = Runtime.getRuntime().exec(cmd);// Runtime 执行后返回创建的进程对象
			}
			InputStream stdoutStream = new BufferedInputStream(pro.getInputStream());
			StringBuffer buffer = new StringBuffer();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
			CountDownLatch threadSignal = new CountDownLatch(2);
			for (int ii = 0; ii < 2; ii++) {
				if (ii == 0) {
					Thread t = new convertImportThread(threadSignal,stdoutStream, buffer, br2, "normal");
					t.start();
				} else {
					Thread t = new convertImportThread(threadSignal,stdoutStream, buffer, br2, "error");
					t.start();
				}
			}
			threadSignal.await();
			
			pro.destroy();
			if (pro != null){
				pro = null;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 判断是否是 windows 操作系统
	 * 
	 * @author iori
	 * @return
	 */
	private static boolean isWindowsSystem() {
		String p = System.getProperty("os.name");
		return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
	}

	/**
	 * 多线程内部类
	 * 
	 * 读取转换时 cmd 进程的标准输出流和错误输出流，这样做是因为如果不读取流， 进程将死锁
	 * 
	 */
	private static class convertImportThread extends Thread {
		private InputStream stdoutStream;
		private StringBuffer buffer;
		private BufferedReader br2;
		private CountDownLatch threadSignal;
		private String flag;

		public convertImportThread(CountDownLatch threadSignal,InputStream stdoutStream, StringBuffer buffer,BufferedReader br2, String flag) {
			this.stdoutStream = stdoutStream;
			this.buffer = buffer;
			this.br2 = br2;
			this.threadSignal = threadSignal;
			this.flag = flag;
		}

		public void run() {
			if (this.flag.equals("normal")) {
				try {
					while (true) {
						int c = this.stdoutStream.read();
						if (c == -1) {
							break;
						}
						this.buffer.append((char) c);
					}
					this.stdoutStream.close();
				} catch (IOException e) {
					logger.info("SWF转换途中（正确流）出现了错误!");
					e.printStackTrace();
				}
				logger.info("读取正确流完毕!");
			} else {
				String line = null;
				try {
					while ((line = this.br2.readLine()) != null)
						;
				} catch (IOException e) {
					logger.info("SWF转换途中（读取错误流）出现了错误!");
					e.printStackTrace();
				}
				logger.info("读取错误流完毕!");
			}

			this.threadSignal.countDown();
		}
	}

}
