import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 27/11/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */

public class MailScreenshot {
    public static final String SMTP_HOST_NAME = "smtp.gmail.com";
    public static final String SMTP_HOST_PORT = "587";
    public static final String SMTP_AUTH_USER = "healthkarttest@gmail.com";
    public static final char[] SMTP_AUTH_PWD  = new char[]{'h','e','a','l','t','h','k','a','r','t'};
    public static final String emailToAddress = "utsav.chanda@healthkart.com";
    public static String[] emailIdList = {"tech.reporting@healthkart.com"/*,"nitin.wadhawan@healthkart.com","pratham@healthkart.com"*/};

    private static void addAttachment(MimeMultipart mimeMultipart,File file,BodyPart messageBodyPart) throws MessagingException {
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(file.getName());
        mimeMultipart.addBodyPart(messageBodyPart);

    }

    public void mailExcel(File file2){

        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.EnableSSL.enable","true");
        properties.put("mail.smtp.host", SMTP_HOST_NAME);
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.user", SMTP_AUTH_USER);
        properties.setProperty("mail.password", new String(SMTP_AUTH_PWD));
        properties.setProperty("mail.smtp.port", SMTP_HOST_PORT);
        properties.put("mail.smtp.starttls.enable", "true");

        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(properties, auth);
        try{
            String computerName= InetAddress.getLocalHost().getHostName();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_AUTH_USER));
            InternetAddress[] addressTo = new InternetAddress[emailIdList.length];
            for(int i=0;i<emailIdList.length;i++){
                addressTo[i] = new InternetAddress(emailIdList[i]);
            }
            message.setRecipients(Message.RecipientType.TO,addressTo);
            message.setSubject("Machine name: " + computerName + " PG Performance ScreenShot");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            addAttachment(multipart, file2, messageBodyPart);

            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, new String(SMTP_AUTH_PWD));
            transport.send(message);
            message.saveChanges();
            transport.close();

        }catch (MessagingException mex) {
            mex.printStackTrace();
        }catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    private class SMTPAuthenticator extends javax.mail.Authenticator
    {

        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = SMTP_AUTH_USER;
            String password = new String(SMTP_AUTH_PWD);
            return new PasswordAuthentication(username, password);
        }
    }

    }
