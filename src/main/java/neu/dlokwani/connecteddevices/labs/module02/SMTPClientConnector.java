/**
 * 
 */
package neu.dlokwani.connecteddevices.labs.module02;

/**
 * @author deepak
 * NUID:001316769
 * This class contains the SMTP Client and its  properties/configuration details
 *
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import neu.dlokwani.connecteddevices.common.ConfigConst;
import neu.dlokwani.connecteddevices.common.ConfigUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMTPClientConnector {
	
	//Declaring Static Variable for logging
	private static final Logger _Logger = Logger.getLogger(SMTPClientConnector.class.getSimpleName());
	boolean isConnected = false;
	Session smtpSession;
	
	/*
	 * this method tries to connect with the smtp server via configuring the correct properties, if already connected thenproceeds further
	 */
	public void connect()
	{
	if (!isConnected) {
	 
	 _Logger.info("Initializing SMTP gateway...");
	 
	 Properties props = new Properties();
	 String portStr = ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY);
	 String hostStr	= ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY);
	 final String user = ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY);
	 final String password = ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
//	 final String user = "deepakiot114@gmail.com";
//	 final String password = "Chiman123!";
	 props.put(ConfigConst.SMTP_PROP_HOST_KEY, hostStr);
	 props.put(ConfigConst.SMTP_PROP_PORT_KEY, portStr);
	 props.put(ConfigConst.SMTP_PROP_AUTH_KEY, ConfigConst.ENABLE_AUTH_KEY);
	 props.put(ConfigConst.SMTP_PROP_ENABLE_TLS_KEY, ConfigConst.ENABLE_CRYPT_KEY);
	 
	 _Logger.info(props.toString());
	 
	 /*
	  * We are using the  SMTP Client with the password protection
	  */
	 Authenticator authenticator = new Authenticator() {
		 protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
		}
	 }; 
	 this.smtpSession = Session.getInstance(props, authenticator);
	 this.isConnected = true;
	 } 
	else {
	 _Logger.info("SMTP gateway connection already initialized.");
	 }
	}
	
	/*
	 * this method is used to publish/push an email notification whenever the threshold conditions are  met
	 */
	public boolean publishMessage(String subject, byte[] payload)
	{
	 if (! this.isConnected) {
	 connect();
	 }
	 
	 boolean flag = false;
	 String fromAddrStr = ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY);
	 try {
	 Message smtpMsg = new MimeMessage(this.smtpSession);
	 InternetAddress fromAddr = new InternetAddress(fromAddrStr);
	 InternetAddress[] toAddr = InternetAddress.parse(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY));
	 
	 //Setting Sender's address
	 smtpMsg.setFrom(fromAddr);
	 
	 //Setting recipeinnt's  address
	 smtpMsg.setRecipients(Message.RecipientType.TO, toAddr);
	 
	 //setting subject line
	 smtpMsg.setSubject(subject);
	 
	 //Setting up the text body
	 smtpMsg.setText(new String(payload));
	 
	 //Sending the email via smtp
	 Transport.send(smtpMsg);
	 _Logger.info("Email notification Sent");
	 
	 //update the success flag
	 flag = true;
	 } 
	 catch (Exception e) {
	 _Logger.log(Level.WARNING, "Failed to send SMTP message from address: " + fromAddrStr, e);
	 }
	 return flag;
	}
}
