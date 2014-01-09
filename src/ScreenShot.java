/*import com.sun.media.jfxmediaimpl.MediaUtils;*/

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 27/11/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenShot {


    public WebDriver openBrowser() throws InterruptedException {
       /*String chromePath = System.getProperty("user.dir")+"/chromedriver";
        System.out.println(chromePath);
        System.setProperty("webdriver.chrome.driver",chromePath);*/
        WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        //webDriver.get("http://205.147.110.151/reporting/payment/payment.jsp");
        webDriver.get("http://perf.healthkart.com/view/viewTemplate.jsp ");
        webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        Thread.sleep(5000);
        return webDriver;
    }

    public File printScreen(){

        try{

            String fileName = "/ScreenCapture" + new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            File file = new File(System.getProperty("user.dir") + "/outputImage/", fileName+".gif");
            file.mkdirs();

            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            ImageIO.write(capture, "gif", file);
            return file;

        }catch(IOException ex){
            ex.printStackTrace();
        }catch (AWTException ex){
            ex.printStackTrace();
        }
        System.out.println("Checking null") ;
        return null;

    }

    public static void main(String []args) throws InterruptedException {

        ScreenShot screenShot = new ScreenShot();
        File file1 = screenShot.printScreen();

        WebDriver webDriver = screenShot.openBrowser();
        File file2 = screenShot.printScreen();
        webDriver.quit();

        File file[] = {file1, file2};

        MailScreenshot mailScreenshot = new MailScreenshot();
        mailScreenshot.mailExcel(file1,file2);


    }
}
