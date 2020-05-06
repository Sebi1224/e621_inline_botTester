package e621_inline_botTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class e621_inline_botMain {
	static String url;
	static int numberTests;
	static int numberThreads;

	public static void main(String[] args) {
		// get path
		String path = "";
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Path userdataAndStuffPath = Paths.get(path + File.separator + "UserDataAndStuff1");
		if (Files.notExists(userdataAndStuffPath)) {
			System.out.println("Please log in to Telegram!");
			
			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")) {
				System.setProperty("webdriver.chrome.driver", path + File.separator + "chromedriverWin.exe");
				System.out.println(path + File.separator + "chromedriverWin.exe");

				System.setProperty("webdriver.gecko.driver", path + File.separator + "geckodriverWin.exe");

			} else if (os.contains("osx") || os.contains("mac")) {
				System.setProperty("webdriver.gecko.driver", path + File.separator + "geckodriverMac");
				System.out.println(path + File.separator + "geckodriverMac");

			} else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {
				System.out.println("On Linux: Please make \"chromedriverLinux\" executible!");
				System.setProperty("webdriver.chrome.driver", path + File.separator + "chromedriverLinux");
				System.out.println(path + File.separator + "chromedriverLinux");
			}
			// Optionen für userdata
			ChromeOptions chrome_options = new ChromeOptions();
			chrome_options.addArguments("user-data-dir=UserDataAndStuff1");
			WebDriver driver = new ChromeDriver(chrome_options);
			driver.get("https://web.telegram.org/");
			WebDriverWait wait = new WebDriverWait(driver, 5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("im_dialogs_search")));
			
			driver.get("https://t.me/joinchat/FTHPSxrMFlEGydr1u_9ZKQ");
			
			JavascriptExecutor js = (JavascriptExecutor) driver;  
			js.executeScript("window.confirm(\"ATTENTION!!\\nATTENTION!!\\nATTENTION!!\\nATTENTION!!\\nPlease click on \"Open in Web\", join chat, close the browser and start the programm again!\");");
			System.exit(0);
		}

		Scanner scanner = new Scanner(System.in);

		numberTests = 0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.println("Enter number of Tests: ");
			// If input is number execute this,
			if (scanner.hasNextInt()) {
				numberTests = scanner.nextInt();
				isValid = true;
				System.out.println("OK! " + numberTests + " Tests!\n");
			}
			// If input is not a number execute this block,
			else {
				System.out.println("Error! Invalid number. Try again.\n");
			}
			scanner.nextLine(); // discard any other data
		}

		numberThreads = 1;
//		boolean isValid2 = false;
//		while (isValid2 == false) {
//			System.out.println("Enter number of Thread with " + numberTests + " each: ");
//			// If input is number execute this,
//			if (scanner.hasNextInt()) {
//				numberThreads = scanner.nextInt();
//				isValid2 = true;
//				System.out.println("OK! " + numberThreads + " Thread with " + numberTests + " Tests each!");
//			}
//			// If input is not a number execute this block,
//			else {
//				System.out.println("Error! Invalid number. Try again.");
//			}
//			scanner.nextLine(); // discard any other data
//		}
		
		
		url = "https://web.telegram.org/#/im?p=g449582673";
//		boolean isValid3 = false;
//		while (isValid3 == false) {
//			System.out.println("Enter url for test chat.\nMake sure that this chat contains at least one\nmessage from the e621_inline_bot \nor write TEST to select the test group!");
//			// If input is number execute this,
//			if (scanner.hasNext()) {
//				String str = scanner.next();
//				if (str.startsWith("https://web.telegram.org/#/im?p=")) {
//					url = str;
//					isValid3 = true;
//					System.out.println("OK! URL: " + url);
//				}else if (str.startsWith("TEST")) {
//					url = "https://web.telegram.org/#/im?p=g449582673";
//					isValid3 = true;
//					System.out.println("OK! Test group selectet!");
//				}
//				
//				
//			// If input is not a String execute this block,
//			}else {
//				System.out.println("Error! Invalid url. Try again. Start with: \"https://web.telegram.org/#/im?p=\" ");
//			}
//			scanner.nextLine(); // discard any other data
//		}
		
		scanner.close();
		
		Thread[] t = new Thread[numberThreads];

		// create folders
		for (int b = 0; b < t.length; b++) {
			Path newPath = Paths.get(path + File.separator + "UserDataAndStuff" + (b+1));
			if (Files.notExists(newPath)) {
				System.out.println("Copy new UserDataAndStuff: " + newPath.toString());
				try {
					copyFolder(userdataAndStuffPath.toFile(), newPath.toFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

		}
		
		t[0] = new Thread(new e621_inline_botInstance(numberTests, 1, url));
		t[0].start();
//		t[1] = new Thread(new e621_inline_botInstance(numberTests, 2, "https://web.telegram.org/#/im?p=g360843646"));
//		t[1].start();
//		t[2] = new Thread(new e621_inline_botInstance(numberTests, 3, "https://web.telegram.org/#/im?p=g473596649"));
//		t[2].start();
//		t[3] = new Thread(new e621_inline_botInstance(numberTests, 4, "https://web.telegram.org/#/im?p=g463140123"));
//		t[3].start();
//		t[4] = new Thread(new e621_inline_botInstance(numberTests, 5, "https://web.telegram.org/#/im?p=g449582673"));
//		t[4].start();
		
//		// start threads
//		for (int b = 0; b < t.length; b++) {
//			t[b] = new Thread(new e621_inline_botInstance(numberTests, (b+1), url));
//			t[b].start();
//		}

	}
	
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory()) 
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
               // System.out.println("Directory created: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another 
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
          //  System.out.println("File copied: " + destinationFolder);
        }
    }

}
