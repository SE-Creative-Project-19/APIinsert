package tools;

import java.util.List;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class URLGenerator {
	private WebDriver driver;

	// ChromeDriver Setting for using Selenium
	String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Set ChromeDriver ID
	String WEB_DRIVER_PATH = "C:\\Users\\qarkc\\AppData\\Local\\SeleniumBasic\\chromedriver.exe"; // Set ChromeDriver PATH

	private String url = "https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do"; // URL that contains Program No.
	
	private int page;
	
	private Vector<String> v = new Vector<String>();

	public URLGenerator() {
		super();
		// Selenium Setting
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		ChromeOptions options = new ChromeOptions();
		options.setCapability("ignoreProtectedModeSettings", true);
		options.addArguments("headless");
		options.addArguments("no-sandbox");
		options.addArguments("disable-setuid-sandbox");
		options.addArguments("disable-dev-shm-usage");
		options.addArguments("lang=ko");
		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);
		
		// crawl
		crawl();
	}
	
	public Vector<String> getVector() {
		return v;
	}

	private void crawl() {
		try {
			driver.get(url);
			
			// Create JavaScriptExecutor for Using JavaScript in Selenium
			JavascriptExecutor js = (JavascriptExecutor)driver;
			
			try {
				Thread.sleep(1000); // Waiting For Loading
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			WebElement pageNum = driver.findElement(By.className("search_result"));
			String[] tmpArr = pageNum.getAttribute("innerHTML").split("/");
			String tmp = tmpArr[3].replace("]<", "");
			
			page = Integer.parseInt(tmp); // Total Page
			
			for(int i = 1; i < 5; i++) {
				if(i != 1) {
					js.executeScript("fnPage(" + i + ")"); // Next Page Script Function
				}
				
				WebElement progUl = driver.findElement(By.className("list_wrap")); // Get ul Element
				List<WebElement> progLi = progUl.findElements(By.tagName("li")); // Get li Elements
				
				for(WebElement item : progLi) {
					WebElement inputTag = item.findElement(By.tagName("input")); // Get input Tag
					System.out.println(inputTag.getAttribute("value")); 
					v.add(inputTag.getAttribute("value")); // Add Program No. to Vector
				}
				
				System.out.println();
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}