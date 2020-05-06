package e621_inline_botTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class e621_inline_botInstance extends Thread {
	WebDriver driver;
	int numberTests;
	int instance;
	String url;
	
	public e621_inline_botInstance(int numberTests, int instance, String url) {
		this.numberTests=numberTests;
		this.instance=instance;
		this.url = url;
	}
	
	
	public void run() {
		startBot();
	}

	public void startBot() {
		String path = "";
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			System.setProperty("webdriver.chrome.driver", path + File.separator + "chromedriverWin.exe");
			System.out.println(path + File.separator + "chromedriverWin.exe");

			System.setProperty("webdriver.gecko.driver", path + File.separator + "geckodriverWin.exe");

		} else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {
			System.setProperty("webdriver.chrome.driver", path + File.separator + "chromedriverLinux");
			System.out.println(path + File.separator + "chromedriverLinux");
		}
		// Optionen für userdata
		ChromeOptions chrome_options = new ChromeOptions();
		chrome_options.addArguments("user-data-dir=UserDataAndStuff" + instance);

		System.out.println("The bot " + instance + " has started!");

		

		// URL aufrufen
		driver = new ChromeDriver(chrome_options);
		driver.get(url);
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("composer_rich_textarea")));

		int count = 0;
		while (count < numberTests) {
			// open search button find and click
			List<WebElement> text = driver.findElements(By.xpath("//*[text()='open search']"));
			if (text.size() > 0) {
				text.get(0).click();
			}

			// picture find and click
			WebDriverWait waitpicture = new WebDriverWait(driver, 500);
			WebElement picture = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//li[@class='inline_result_wrap inline_result_photo']")));
			picture.click();

			count++;
			System.out.println("Run " + count + " finished!");

		}
		System.out.println("Finished! Runs: " + count);
		driver.close();

	}

}
