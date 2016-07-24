/**
 * Product  : Hiperium Project
 * Architect: Andres Solorzano.
 * Created  : 08-05-2009 - 23:30:00
 * 
 * The contents of this file are copyrighted by Andres Solorzano 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.hiperium.com/about/licence.html
 * 
 * Copyright 2014 Andres Solorzano. All rights reserved.
 * 
 */
package com.hiperium.identity.test.mail;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hiperium.commons.services.logger.HiperiumLogger;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 
 * @author Andres Solorzano
 */
@RunWith(Arquillian.class)
public class SendEmailTest {

	/**
	 * 
	 * @return
	 */
	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "mail-service-test.war").addClasses(
        		HiperiumLogger.class
        );
    }
	
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(SendEmailTest.class);
	
	private static final String USERNAME = "cloud.service@hiperium.com";
	private static final String PASSWORD = "SmtpCloud2@16";
	
	private Integer port = 465;
	private String host = "base.hiperium.com";
	private String from = "cloud.service@hiperium.com";
	private String to = "andres.solorzano@hiperium.com";
	private Boolean auth = true;
	private Boolean debug = true;
	
	private Properties props;
	private Authenticator authenticator;
	
	/**
	 * Initial test initialization.
	 */
    @Before
    public void setUp() {
    	LOGGER.info("setUp() - START");
    	this.props = new Properties();
    	this.props.put("mail.smtp.host", this.host);
    	this.props.put("mail.smtp.port", this.port);
    	MailSSLSocketFactory socketFactory;
		try {
			socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustedHosts(new String[] { this.host });
			this.props.put("mail.smtp.ssl.enable", true);
			this.props.put("mail.smtp.ssl.checkserveridentity", "true");
			this.props.put("mail.smtp.ssl.socketFactory", socketFactory);
		} catch (GeneralSecurityException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		if (this.auth) {
    		this.props.put("mail.smtp.auth", true);
    	    this.authenticator = new Authenticator() {
    	        private PasswordAuthentication pa = new PasswordAuthentication(USERNAME, PASSWORD);
    	        @Override
    	        public PasswordAuthentication getPasswordAuthentication() {
    	            return pa;
    	        }
    	    };
    	}
        LOGGER.info("setUp() - END");
    }
    
    /**
     * Test of get a list of existing registered services.
     */
    @Test
    public void sendMail() throws Exception {
    	LOGGER.info("sendMail() - START");
    	Session session = Session.getInstance(this.props, this.authenticator);
    	session.setDebug(this.debug);
    	MimeMessage message = new MimeMessage(session);
    	try {
    	    message.setFrom(new InternetAddress(this.from));
    	    InternetAddress[] address = {new InternetAddress(this.to)};
    	    message.setRecipients(Message.RecipientType.TO, address);
    	    message.setSubject("Hiperium Cloud Service");
    	    message.setSentDate(new Date());
    	    message.setText("Hiperium Identity Service test.");
    	    Transport.send(message);
    	} catch (MessagingException ex) {
    	    LOGGER.error(ex.getMessage(), ex);
    	}
    	LOGGER.info("sendMail() - END");
    }
    
}
