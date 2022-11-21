package com.noetic.gwpartner.timwe.delayed;

import com.noetic.gwpartner.timwe.Model.HttpRequestHandler;
import com.noetic.gwpartner.timwe.Model.Sms;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class StartDelayedSms extends Thread {

//    @Autowired
//    private SecurityRepo securityRepo;



    private static Logger log = LoggerFactory.getLogger(StartDelayedSms.class);
    Queue<Sms> queue = null;
    HttpRequestHandler requestHandler = null;
    Response httpResponse = null;
    private static final int UFONE_ID = 71; // do not change this
    private static final int TELENOR_ID = 77; // do not change this
    private static final int ZONG_ID = 10;
    private static final int MOBILINK_ID = 3;

//    public Sms sms(){
//
//       return securityRepo.findAllSms();
//    }
//
//    public int id(boolean bool, long smsId){
//
//        return securityRepo.updateSms(bool,smsId);
//    }




//    public void run(){
//
//        while(true){
//
//            try{
//                queue= (Queue<Sms>) securityRepo.findAllSms();
//
//                if(queue!=null){
//                    log.info("NOT-ACCEPTED-SMS-SENDING-STARTED");
//                    while(!queue.isEmpty())
//                    {
//                        Sms sms = queue.poll();
//
//                        requestHandler = new HttpRequestHandler(
//                                sms.getMsisdn(),
//                                sms.getShortcode(),
//                                sms.getSmsData(),
//                                String.valueOf(getAppropriateConnectionPointId(sms.getConnectionPointId())),
//                                String.valueOf(sms.getSmsId()));
//
//                        httpResponse = requestHandler.sendRequest(false); // Sends data to timwe using "timwe mo url" defined in Constants.java
//                        if(httpResponse.isSuccessful()){
//                            log.info("NOT-ACCEPTED-SMS-ID "+sms.getSmsId()+" SENT-SUCCESSFULLY");
//                            securityRepo.updateSms(Boolean.parseBoolean("1"),2);
//                        }
//                    }
//                }
//                log.info("WAITING-FOR-NOT-DELIVERED-SMS");
//                Thread.sleep(1000 * 60 * 60 * 1);     //pause for 1 hour
//
//            } catch (Exception ex) {
//                log.error(String.valueOf(ex), ex);
//            }
//        }
//    }

//   @Override
//    public void run() {
//        while (true) {
//            try {
//                queue = SecurityRepo.getNotSubmittedSmsToTimWe();
//                if(queue!=null){
//                    log.info("NOT-ACCEPTED-SMS-SENDING-STARTED");
//                    while(!queue.isEmpty())
//                    {
//                        Sms sms = queue.poll();
//
//                        requestHandler = new HttpRequestHandler(
//                                sms.getMsisdn(),
//                                sms.getShortcode(),
//                                sms.getSmsData(),
//                                String.valueOf(getAppropriateConnectionPointId(sms.getConnectionPointId())),
//                                String.valueOf(sms.getSmsId()));
//
//                        httpResponse = requestHandler.sendRequest(false); // Sends data to timwe using "timwe mo url" defined in Constants.java
//                        if(httpResponse.isSuccessful()){
//                            log.info("NOT-ACCEPTED-SMS-ID "+sms.getSmsId()+" SENT-SUCCESSFULLY");
//                            SecurityDAO.setStatusForNotAcceptedSms(true, sms.getSmsId());
//                        }
//                    }
//                }
//                log.info("WAITING-FOR-NOT-DELIVERED-SMS");
//                Thread.sleep(1000 * 60 * 60 * 1);     //pause for 1 hour
//            } catch (Exception ex) {
//                log.error(ex, ex);
//            }
//        }
//    }


    private int getAppropriateConnectionPointId(int connectivityPointId) {

        if (connectivityPointId == 71 || connectivityPointId == 5   //all these connectivityPointId relates to UFONE
                || connectivityPointId == 6
                || connectivityPointId == 74) {
            connectivityPointId = this.UFONE_ID;

        } else if (connectivityPointId == 77              //all these connectivityPointId relates to TELENOR
                || connectivityPointId == 78
                || connectivityPointId == 79) {
            connectivityPointId = this.TELENOR_ID;

        } else if (connectivityPointId == 10                    //all these connectivityPointId relates to ZONG
                || connectivityPointId == 30) {
            connectivityPointId = this.ZONG_ID;

        } else if (connectivityPointId == 3                  //all these connectivityPointId relates to MOBILINK
                || connectivityPointId == 90
                || connectivityPointId == 63
                || connectivityPointId == 17) {
            connectivityPointId = this.MOBILINK_ID;

        } else {                                            // it is of some other tp, could be of warid etc.
            connectivityPointId = connectivityPointId;
        }

        return connectivityPointId;
    }
}
