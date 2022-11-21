package com.noetic.gwpartner.timwe.Alerts;

import com.noetic.gwpartner.timwe.Entity.Charging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.noetic.gwpartner.timwe.Service.tbl_charging_Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MoAlert {
    @Autowired
    private Charging charging;
    @Autowired
    private tbl_charging_Service service;

    private static final Logger log = Logger.getLogger(MoAlert.class);

    public void checkMo() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                checkMoStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    public void checkMoStatus() throws Exception {
        log.info("Scheduler Started");
        Integer obj1 = service.findLastRecord("00:00:00","23:59:59");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        obj1=obj1/2;
        if (LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.of(00, 00, 00))) && LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(07, 00, 00)))) {

            log.info("Transaction Count "+obj1);
            if (obj1 < 1250) {
                sendEmailAlert(obj1);
            } else {
                log.info("Alert Not Sent");
            }
        } else {

            log.info("Transaction Count "+obj1);
            if (obj1 < 1550) {
                sendEmailAlert(obj1);
            } else {
                log.info("Alert Not Sent :"+obj1);
            }
        }
    }

    public void sendEmailAlert() {
        String userName = "sdp.backend@gmail.com";
        String password = "P@kistan2020";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("SDP Alerts <sdp.backend@gmail.com>"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("support@noeticworld.com")
            );
            message.setSubject("MO Alerts");
            message.setText("SDP traffic is slow.Please check SDP application.");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailAlert(int number) {
        String userName = "sdp.backend@gmail.com";
        String password = "P@kistan2020";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("SDP Alerts <sdp.backend@gmail.com>"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("zabbix@noeticworld.com")
            );
            message.setSubject("MO Alerts");
            message.setText("SDP traffic is slow . Received "+number+" in previous five minutes.Please check SDP application.");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
