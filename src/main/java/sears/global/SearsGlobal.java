package sears.global;

//msonar
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.saf.global.LoadProperties;
import com.saf.global.TestStepType;
import com.saf.util.AppElement.AppElements;

public class SearsGlobal extends PageFactory {
	public final String hubUrlString = "http://localhost:4444/wd/hub";
	public static int Wait = 120;
	public String WinhandleBefore;
	public int second, minute, hour, day, month, year;
	public static WebDriverWait wait = null;
	public static final String Xpath = "Xpath", Id = "Id",
			LinkText = "LinkText", ClassName = "ClassName", Css = "Css",
			PARTIALLINKTEXT = "PARTIALLINKTEXT", NAME = "name";
	public static final int BUTTON = 1, BUTTON1 = 26, TEXTBOX = 2,
			VERIFY_TEXT = 3, NEW_WINDOW = 4, MOUSE_HOVER = 5,
			MOUSE_HOVER1 = 27, VERIFYURL = 6, GET_TITLE = 7, DROPDOWN = 8,
			VERIFY_ELEMENT = 9, VERIFY_LINK = 10, BODY_TEXT = 11,
			BODY_TEXT_NEGATIVE = 12, SELECT = 13, INPUT = 14,
			TAGNAME_SELECT = 15, VERIFYELEMENTTEXT = 16, VERIFYELEMENT = 17,
			NEWTAB = 18, HOVER = 19, CHECKBOX = 20, BUTTON_SENDKEYS = 21,
			VERIFY_INPUT_TEXT = 22, DROPDOWN_VALUE = 23, TEXTBOX_READONLY = 24,
			DROPDOWN_DISABLED = 25, CHECKBOX1 = 27;
	public Date date = null;
	public static final int textbox_id = 1, textbox_xpath = 2;
	public Properties CONFIG = null;

	/**
	 * Reads Objects from Objects Class files
	 * 
	 * @throws Exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteMethod() throws Exception {
		super.beforeSuiteMethod();
	}

	// ActionMethods am;
	/**
	 * This method initialize web driver before starting each test.
	 * 
	 * @throws IOException
	 *             In case of any error.
	 */

	@BeforeMethod(alwaysRun = true)
	public void beforeTest() throws IOException {
		initialize(LoadProperties.BROWSER, LoadProperties.VERSION,
				LoadProperties.PLATFORM);
	}

	@AfterMethod(alwaysRun = true)
	public void afterTestMethod() {
		browserClose();
		getAssertList().clear();
	}

	/**
	 * @author veena.nadipalli This method is used to load FilePaths properties
	 *         file
	 */
	Properties prop = new Properties();

	public Properties loadProperties() {
		try {
			// load a properties file
			prop.load(new FileInputStream("filePaths.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}

	public static boolean explicitWait_text(String sObjType, String sObjProp,
			String sText, EventFiringWebDriver driver) {
		WebDriverWait wait;
		boolean waitFlag = false;
		wait = new WebDriverWait(driver, Wait);
		try {
			switch (AppElements.valueOf(sObjType.toUpperCase())) {
			case XPATH:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.xpath(sObjProp), sText));
				waitFlag = true;
				break;
			case LINKTEXT:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.linkText(sObjProp), sText));
				waitFlag = true;
				break;
			case ID:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.id(sObjProp), sText));
				waitFlag = true;
				break;
			case CSS:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.cssSelector(sObjProp), sText));
				waitFlag = true;
				break;
			case NAME:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.name(sObjProp), sText));
				waitFlag = true;
				break;
			case TAGNAME:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.tagName(sObjProp), sText));
				waitFlag = true;
				break;
			case CLASSNAME:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.className(sObjProp), sText));
				waitFlag = true;
				break;
			case PARTIALLINKTEXT:
				wait.until(ExpectedConditions.textToBePresentInElementValue(
						By.partialLinkText(sObjProp), sText));
				waitFlag = true;
				break;
			default:
				log.info("Object Type Not Found in Switch");
				break;
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			log.error("Object Not Found on Screen After Waiting for " + Wait
					+ " Sec");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			log.error("Object Not Found on Screen After Waiting for " + Wait
					+ " Sec");
		}
		return waitFlag;
	}

	/**
	 * should be called for every test before execution. it initialises the
	 * properties files and the defines the constants User name, Password and
	 * the browser
	 * 
	 * @throws IOException
	 */
	public void initialize(String browser, String version, String platform)
			throws IOException {
		// LOGGER.info("Entering initialize function .... ");
		browser = LoadProperties.BROWSER;

		if (browser.contains("default")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String str[] = dateFormat.format(cal.getTime()).toString()
					.split(" ");
			int date = Integer.parseInt(str[0].substring(8, str[0].length()));
			String IPAddress[] = InetAddress.getLocalHost().toString()
					.split("/");

			int ip = Integer.parseInt(IPAddress[1].substring((IPAddress[1]
					.lastIndexOf(".") + 1)));

			if (ip == 75 && (date % 2 == 0)) {
				browser = "firefox";
			} else {
				browser = "chrome";
			}
		}
		dr = defineBrowser(browser, version, platform);
		// driver = new EventFiringWebDriver(dr);
		setWebDriver(dr);
		// getDriver().manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		getDriver().manage().timeouts()
				.pageLoadTimeout(timeout, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
		open(url);
		AcceptCertificateInIE();
		if (browser.contains("explorer")) {
			// getDriver().manage().deleteAllCookies();
			// open(url);
		}
		wait(2);
	}

	/**
	 * This method is used to select the browser based on the value declare in
	 * global variable.
	 * 
	 * @param browser
	 * @return
	 */
	public synchronized WebDriver defineBrowser(String browser, String version,
			String platform) {
		URL hubUrl = null;
		String pathPropFiles = "", finalPropPath = "";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if (platform.equalsIgnoreCase("WINDOWS")) {
			// capabilities.setPlatform(Platform.WINDOWS);
		} else if (platform.equalsIgnoreCase("LINUX")) {
			capabilities.setPlatform(Platform.LINUX);
		}
		loadProperties();
		log("under initialize --- " + browser, TestStepType.DATA_CAPTURE);

		try {
			// hubUrl = new
			// URL("http://"+System.getenv("SELENIUM_HUB_HOST")+"/wd/hub");
			// hubUrl = new URL("http://10.0.0.5:4444/wd/hub");
			// hubUrl = new
			// URL("http://"+System.getenv("SELENIUM_HUB_HOST")+"/wd/hub");
			// hubUrl = new URL("http://localhost:4444/wd/hub");
			hubUrl = new URL("http://" + LoadProperties.HOST + ":"
					+ LoadProperties.PORT + "/wd/hub");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (os.contains("Windows")) {
			if (browser.equalsIgnoreCase("Firefox")) {
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("browser.download.folderList", 2);
				capabilities.setBrowserName(DesiredCapabilities.firefox()
						.getBrowserName());
				// capabilities.setVersion(version);
				// System.out.println(System.getProperty("os.arch"));
				try {
					return new RemoteWebDriver(hubUrl, capabilities);
				} catch (WebDriverException e) {
					if (e.getMessage().contains("Unable to bind")) {
						try {
							Thread.sleep(90000);
							return new RemoteWebDriver(hubUrl, capabilities);
						} catch (Exception e1) {
							System.out
									.println("Error while retrying to intialize webdriver");
							e1.printStackTrace();
						}
					} else {
						e.printStackTrace();
					}
				}
			} else if (browser.toLowerCase().contains("explorer")) {
				if (System.getProperty("os.arch").contains("86")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x32");
					finalPropPath = appendProjName(pathPropFiles);
					System.setProperty("webdriver.ie.driver", finalPropPath);
				} else if (System.getProperty("os.arch").contains("64")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x64");
					finalPropPath = appendProjName(pathPropFiles);
					// System.setProperty("webdriver.ie.driver", finalPropPath);
				}
				capabilities.setBrowserName(DesiredCapabilities
						.internetExplorer().getBrowserName());
				// capabilities.setVersion(version);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				try {
					return new RemoteWebDriver(hubUrl, capabilities);
				} catch (WebDriverException e) {
					if (e.getMessage().contains("Unable to bind")) {
						try {
							Thread.sleep(90000);
							return new RemoteWebDriver(hubUrl, capabilities);
						} catch (Exception e1) {
							System.out
									.println("Error while retrying to intialize webdriver");
							e1.printStackTrace();
						}
					} else {
						e.printStackTrace();
					}
				}
			} else if (browser.toLowerCase().contains("chrome")) {
				pathPropFiles = prop.getProperty("chrome_windows");
				finalPropPath = appendProjName(pathPropFiles);
				// System.setProperty("webdriver.chrome.driver", finalPropPath);
				log.info("Setting a Chrome Driver.");
				capabilities.setBrowserName(DesiredCapabilities.chrome()
						.getBrowserName());
				// capabilities.setVersion(version);
				try {
					return new RemoteWebDriver(hubUrl, capabilities);
				} catch (WebDriverException e) {
					if (e.getMessage().contains("Unable to bind")) {
						try {
							Thread.sleep(90000);
							return new RemoteWebDriver(hubUrl, capabilities);
						} catch (Exception e1) {
							System.out
									.println("Error while retrying to intialize webdriver");
							e1.printStackTrace();
						}
					} else {
						e.printStackTrace();
					}
				}
			} else if (browser.equalsIgnoreCase("Safari")) {
				capabilities.setBrowserName(DesiredCapabilities.safari()
						.getBrowserName());
				capabilities.setVersion(version);
				try {
					return new RemoteWebDriver(hubUrl, capabilities);
				} catch (WebDriverException e) {
					if (e.getMessage().contains("Unable to bind")) {
						try {
							Thread.sleep(90000);
							return new RemoteWebDriver(hubUrl, capabilities);
						} catch (Exception e1) {
							System.out
									.println("Error while retrying to intialize webdriver");
							e1.printStackTrace();
						}
					} else {
						e.printStackTrace();
					}
				}
			} else if (browser.toLowerCase().contains("explorer")) {
				if (System.getProperty("os.arch").contains("86")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x32");
					finalPropPath = appendProjName(pathPropFiles);
					System.setProperty("webdriver.ie.driver", finalPropPath);
				} else if (System.getProperty("os.arch").contains("64")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x64");
					finalPropPath = appendProjName(pathPropFiles);
					// System.setProperty("webdriver.ie.driver", finalPropPath);
					// System.setProperty("webdriver.ie.driver",
					// "C:/data/SelJar/IEDriverServer.exe");
				}
				capabilities.setBrowserName(DesiredCapabilities
						.internetExplorer().getBrowserName());
				// capabilities.setVersion(version);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				try {
					return new RemoteWebDriver(hubUrl, capabilities);
				} catch (WebDriverException e) {
					if (e.getMessage().contains("Unable to bind")) {
						try {
							Thread.sleep(90000);
							return new RemoteWebDriver(hubUrl, capabilities);
						} catch (Exception e1) {
							System.out
									.println("Error while retrying to intialize webdriver");
							e1.printStackTrace();
						}
					} else {
						e.printStackTrace();
					}
				}
			} else if (browser.toLowerCase().contains("chrome")) {
				pathPropFiles = prop.getProperty("chrome_windows");
				finalPropPath = appendProjName(pathPropFiles);
				// System.setProperty("webdriver.chrome.driver", finalPropPath);
				System.setProperty("webdriver.chrome.driver",
						"C:/data/SelJar/chromedriver.exe");
				log.info("Setting a Chrome Driver.");
				capabilities.setBrowserName(DesiredCapabilities.chrome()
						.getBrowserName());
				capabilities.setVersion(version);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else if (browser.equalsIgnoreCase("Safari")) {
				capabilities.setBrowserName(DesiredCapabilities.safari()
						.getBrowserName());
				// capabilities.setVersion(version);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else
				log.info("ERROR - didn't find a browser!!");
		} else if (os.contains("Linux")) {
			if (browser.equalsIgnoreCase("FireFox")) {
				capabilities.setBrowserName(browser);
				// capabilities.setVersion(version);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else if (browser.toLowerCase().contains("explorer")) {
				if (System.getProperty("os.arch").contains("86")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x32");
					finalPropPath = appendProjName(pathPropFiles);
					System.setProperty("webdriver.ie.driver", finalPropPath);
				} else if (System.getProperty("os.arch").contains("64")) {
					pathPropFiles = prop.getProperty("IEDriverServer_x64");
					finalPropPath = appendProjName(pathPropFiles);
					System.setProperty("webdriver.ie.driver", finalPropPath);
				}
				capabilities.setBrowserName(DesiredCapabilities
						.internetExplorer().getBrowserName());
				// capabilities.setVersion(version);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else if (browser.toLowerCase().contains("chrome")) {
				log.info("Setting a Chrome Driver.");
				capabilities.setBrowserName(DesiredCapabilities.chrome()
						.getBrowserName());
				// capabilities.setVersion(version);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else if (browser.equalsIgnoreCase("Safari")) {
				capabilities.setBrowserName(DesiredCapabilities.safari()
						.getBrowserName());
				// capabilities.setVersion(version);
				return new RemoteWebDriver(hubUrl, capabilities);
			} else
				log.info("ERROR - didn't find a browser!!");
		}
		return null;
	}

	/**
	 * Takes the Screen shots whenever required and save it in screenshots
	 * folder in the project
	 * 
	 * @param fileName
	 *            - with which the screenshot is named while saving.
	 */
	public void takeScreenShot(String fileName) {
		logWithScreenshot("");
	}

	public static boolean explicitWait(EventFiringWebDriver driver,
			String sObjType, String sObjProp) {
		boolean waitFlag = false;
		wait = new WebDriverWait(driver, Wait);
		try {
			switch (AppElements.valueOf(sObjType.toUpperCase())) {
			case XPATH:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(sObjProp)));
				log.info("Object Identified with Xpath is Present::"
						+ driver.findElement(By.xpath(sObjProp)).getText());
				waitFlag = true;
				break;
			case LINKTEXT:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.linkText(sObjProp)));
				log.info("Object Identified with LinkText is Present::"
						+ driver.findElement(By.linkText(sObjProp)).getText());
				waitFlag = true;
				break;
			case ID:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.id(sObjProp)));
				log.info("Object Identified with Id is Present::"
						+ driver.findElement(By.id(sObjProp)).getText());
				waitFlag = true;
				break;
			case CSS:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(sObjProp)));
				log.info("Object Identified with Css is Present::"
						+ driver.findElement(By.cssSelector(sObjProp))
								.getText());
				waitFlag = true;
				break;
			case NAME:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.name(sObjProp)));
				log.info("Object Identified with Name is Present::"
						+ driver.findElement(By.name(sObjProp)).getText());
				waitFlag = true;
				break;
			case TAGNAME:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.tagName(sObjProp)));
				log.info("Object Identified with TagName is Present::"
						+ driver.findElement(By.tagName(sObjProp)).getText());
				waitFlag = true;
				break;
			case CLASSNAME:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.className(sObjProp)));
				log.info("Object Identified with ClassName is Present::"
						+ driver.findElement(By.className(sObjProp)).getText());
				waitFlag = true;
				break;
			case PARTIALLINKTEXT:
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText(sObjProp)));
				log.info("Object Identified with PartialLinkText is Present::"
						+ driver.findElement(By.partialLinkText(sObjProp))
								.getText());
				waitFlag = true;
				break;
			default:
				log.info("Object Type Not Found in Switch");
				break;
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			log.error("Object Not Found on Screen After Waiting for " + Wait
					+ " Sec");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			log.error("Object Not Found on Screen After Waiting for " + Wait
					+ " Sec");
		}
		return waitFlag;
	}
}
