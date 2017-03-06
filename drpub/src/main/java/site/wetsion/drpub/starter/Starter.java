package site.wetsion.drpub.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import site.wetsion.drpub.mainservice.MainService;
import site.wetsion.drpub.mainservice.TvService;

public class Starter {

	private final static Logger log = LoggerFactory.getLogger(Starter.class);
	
	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 30, 300, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		
		executor.allowCoreThreadTimeOut(false);
		List<String> nums = new ArrayList<String>();
		
		File file = new File("E:\\allaccount.txt");
		FileWriter fileWriter  = null;
		for (int i = 1990;i<2015;i++) {
			for (int j = 1;j<20;j++) {
				if (j<10) {
					StringBuilder builder = new StringBuilder();
					builder.append(i).append("00").append(j).append("\n");
					String num = builder.toString();
					nums.add(num);
					try {
						fileWriter = new FileWriter(file, true);
						fileWriter.write(builder.toString());
						fileWriter.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							fileWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					StringBuilder builder = new StringBuilder();
					builder.append(i).append("0").append(j).append("\n");
					String num = builder.toString();
					nums.add(num);
					try {
						fileWriter = new FileWriter(file, true);
						fileWriter.write(builder.toString());
						fileWriter.close();
					} catch (FileNotFoundException e) {
						log.error(e.getMessage());
					} catch (IOException e) {
						log.error(e.getMessage());
					} finally {
						try {
							fileWriter.close();
						} catch (IOException e) {
							log.error(e.getMessage());
						}
					}
				}
			}
//			StringBuilder builder = new StringBuilder();
//			builder.append(i);
//			nums.add(builder.toString());
		}
//		nums.add("1990003");
		for (String num : nums) {
			TvService service = new TvService();
			service.login(num);
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				log.error(e.getMessage());
//			}
		}
		
//		Queue<String> queue = new ArrayBlockingQueue<String>(1024);
//		queue.addAll(nums);
//		while(!queue.isEmpty()){
//			TvService service = new TvService();
//			service.setAccount(queue.poll());
//			try {
//				executor.execute(service);
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
