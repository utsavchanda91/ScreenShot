/*import com.sun.media.jfxmediaimpl.MediaUtils;*/

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


/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 27/11/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenShot {

    public static void main(String []args){

        try{
            String fileName = "/ScreenCapture" + new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String destination = "/home/utsav/CodeRepository/ScreenShot/outputImage/";
            File file = new File(destination + "/" + dateFormat.format(date)+fileName+".gif");
            file.mkdirs();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            ImageIO.write(capture, "gif", file);
            MailScreenshot mailScreenshot = new MailScreenshot();
            mailScreenshot.mailExcel(file);
        }catch(IOException ex){
            ex.printStackTrace();
        }catch (AWTException ex){
            ex.printStackTrace();
        }
    }
}
