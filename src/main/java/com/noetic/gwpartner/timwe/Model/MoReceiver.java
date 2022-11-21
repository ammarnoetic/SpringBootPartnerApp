package com.noetic.gwpartner.timwe.Model;

import com.google.gson.Gson;
import com.noetic.gwpartner.timwe.Entity.Charging;
import com.noetic.gwpartner.timwe.Entity.blacklist;
import com.noetic.gwpartner.timwe.Repository.BlacklistRepo;
import com.noetic.gwpartner.timwe.Service.tbl_charging_Service;
import com.noetic.gwpartner.timwe.constants.Constants;
import okhttp3.*;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import pk.com.digitania.httpapi.message.MoMessageObject;
import org.apache.log4j.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.sql.Time;
//import java.util.logging.Logger;

@Configuration
public class MoReceiver {




//    @Autowired
//    private AbstractMessageReceiver abstractMessageReceiver;



//    @Autowired
//    private ZongMMLRequest zongMMLRequest;
    @Autowired
    private BlacklistRepo blacklistRepo;

//    @Autowired
//    private SmsController smsController;

    @Autowired
   private tbl_charging_Service tbl_charging_service;
//    @Autowired
//     public Charging charging;

    public  static final Logger log = Logger.getLogger(String.valueOf(MoReceiver.class));

    //For PCom
    private static final int MOBILINK_ID = 10;
    private static final int TELENOR_ID = 20;
    private static final int UFONE_ID = 30;
    private static final int WARID_ID = 40;
    private static final int ZONG_ID = 50;

    private static Charging mydao = null;

    private static int partnerid= 18;

//    protected MoReceiver(int port, String context) {
//        super(port, context);
//    }

//    protected MoReceiver(int port, String context) {
//        super(port, context);
//    }

//    @PostMapping("/show")
//    public void getMcg(@org.springframework.web.bind.annotation.RequestBody Sms sms){
//
//        System.out.println(sms);
//
//    }


    protected MoMessageObject processMMSRequest(MoMessageObject moMessageObject) {
        return null;
    }

    //blacklist function for repo
    public blacklist checkBlackList(String msisdn){

        return blacklistRepo.checkBlackList(msisdn);
    }


    public MoMessageObject processSMSRequest(Sms incomingPacket) {


       // smsController.getProductAsJson(new Sms());

        System.out.println("processsms");

         MoMessageObject moMessageObj = new MoMessageObject();
        ZongMMLRequest zongMMLRequest = new ZongMMLRequest();
        int charging_mechanism = 1;
        int isPostPaid = 0;
        JazzWaridChargeRequest dot_handler = null;
        PostPaidOrPrePaid postPaidOrPrePaid = null;
        HttpResponse httpDotRes = null; //Get Response from Dot Request
        HttpRequestHandler requestHandler = null; //Set to Partner Request
        Response response = null; //Get Partner Response

        //return null;


    try {
        int connectivityPointId = incomingPacket.getConnectionPointId();

        if ((connectivityPointId == 71) || (connectivityPointId == 5) || (connectivityPointId == 6) || (connectivityPointId == 74)) {
            connectivityPointId = this.UFONE_ID;
            requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                    incomingPacket.getShortcode(),
                    incomingPacket.getSmsData(),
                    String.valueOf(connectivityPointId),
                    String.valueOf(incomingPacket.getSmsId()), //Used as Transaction ID
                    String.valueOf(incomingPacket.getConnectionPointId()));


            response = requestHandler.sendRequest(false);

            requestHandler = null;

            log.info("MSISDN: " + incomingPacket.getMsisdn() + " OperatorId: " + connectivityPointId);


            if (response.isSuccessful()) {
                String replymt = response.body().string();
                HTTPSDPMT mthttp = new HTTPSDPMT(incomingPacket.getMsisdn(),
                        incomingPacket.getShortcode(), replymt);
                HttpResponse mtResponse = null;
                mtResponse = mthttp.sendRequest(false);
                // System.out.println(replymt);

                mthttp = null;
                log.info("The SMS Delivery Status" + mtResponse.getStatusLine().getStatusCode()
                        + mtResponse.getStatusLine().getReasonPhrase());
                log.info("SMS-ID| " + incomingPacket.getSmsId() + " | ACCEPTED");
                moMessageObj.setIsAccepted(Boolean.valueOf(true));

            }

        } else if ((connectivityPointId == 77) || (connectivityPointId == 78) || (connectivityPointId == 79) || (connectivityPointId == 106)) {

            System.out.println("tel request");
            connectivityPointId = this.TELENOR_ID;
            requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                    incomingPacket.getShortcode(),
                    incomingPacket.getSmsData(),
                    String.valueOf(connectivityPointId),
                    String.valueOf(incomingPacket.getSmsId()),
                    String.valueOf(incomingPacket.getConnectionPointId()));


            response = requestHandler.sendRequest(false);

            requestHandler = null;

            log.info("MSISDN: " + incomingPacket.getMsisdn() + " OperatorId: " + connectivityPointId);

            if (response.isSuccessful()) {
                String replymt = response.body().string();
                HTTPSDPMT mthttp = new HTTPSDPMT(incomingPacket.getMsisdn(),
                        incomingPacket.getShortcode(), replymt);
                HttpResponse mtResponse = null;
                System.out.println("sending mt");
                mtResponse = mthttp.sendRequest(false);
                //   System.out.println(replymt);

                mthttp = null;
                log.info("The SMS Delivery Status" + mtResponse.getStatusLine().getStatusCode()
                        + mtResponse.getStatusLine().getReasonPhrase());
                log.info("SMS-ID| " + incomingPacket.getSmsId() + " | ACCEPTED");
                moMessageObj.setIsAccepted(Boolean.valueOf(true));

            }
        } else if ((connectivityPointId == 10) || (connectivityPointId == 30) || (connectivityPointId == 142)) {

            System.out.println("142");
            connectivityPointId = this.ZONG_ID;
            requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                    incomingPacket.getShortcode(),
                    incomingPacket.getSmsData(),
                    String.valueOf(connectivityPointId),
                    String.valueOf(incomingPacket.getSmsId()),
                    String.valueOf(incomingPacket.getConnectionPointId()));

            String msisdn = incomingPacket.getMsisdn();


            System.out.println("start checking blacklist");

            long blid = 0;
            int status = 0;
            blacklist bl = null;
            String serviceId = null;
            bl = blacklistRepo.checkBlackList("1");

            if (bl != null) {
                System.out.println("checking blacklist");
                blid = bl.get_id();
                status = bl.get_statuscode();
                log.info(incomingPacket.getMsisdn() + " is Blacklisted. Exiting Application");
            } else {
                log.info(incomingPacket.getMsisdn() + " is not Blacklisted. Sending Charging Request");
                // log.info("status = "+ status + ", Blacklist ID = "+blid);
                if (status == 0 && blid == 0) {
                    String new_subscriberNumber = incomingPacket.getMsisdn().substring(2);
                    String shortcode = String.valueOf(incomingPacket.getShortcode());
                    Integer adjustmentAmountRelative = 0;
                    Integer amount = 0;

                    Integer shortcode1 = Integer.valueOf(shortcode);

                    if (shortcode1 == 3441) {

                        adjustmentAmountRelative = 200;
                        amount = 239;
                        serviceId = "Noet01";
                    } else if (shortcode1 == 3443) {
                        adjustmentAmountRelative = 500;
                        serviceId = "Noet05";
                        amount = 598;
                    } else if (shortcode1 == 3444) {
                        System.out.println("3444");
                        adjustmentAmountRelative = 1000;
                        serviceId = "Noet10";
                        amount = 1159;
                    } else if (shortcode1 == 3445) {
                        adjustmentAmountRelative = 2500;
                        serviceId = "Noet25";
                        amount = 2988;
                    }
                    String number = "";
                    if (msisdn.startsWith("92")) {
                        number = msisdn;
                    } else if (msisdn.startsWith("03")) {
                        number = msisdn.replaceFirst("03", "92");
                    } else if (msisdn.startsWith("3")) {
                        number = "92" + msisdn;
                    }


//                        zongMMLRequest.logIn();
//                        String mmlResponse = zongMMLRequest.deductBalance(number, Integer.toString(adjustmentAmountRelative), serviceId);
//
//                        log.info("ZONG RESPONSE | " + mmlResponse);
//                        String[] res = mmlResponse.split("RETN=");
//                        String[] codeArr = res[1].split(",");
//                        String code = codeArr[0];
//                        log.info("ZONG MML RESPONSE CODE | " + code);


                    String scKeyword = null;
                    String[] splited = incomingPacket.getSmsData().split("\\s+");
                    scKeyword = splited[0];
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\n    \"msisdn\":"+number+"," +
                            "\n    \"amount\":"+adjustmentAmountRelative+",\n    \"operatorId\":"+connectivityPointId+",\n    \"transactionid\":\"+serviceId+\"\n}");
                    System.out.println("zong request");
                    Request request = new Request.Builder()
//                            .url("http://192.168.127.57:9080/chargezong")
                            //for testing
                            .url("localhost:9080/chargezong")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response resp= client.newCall(request).execute();
                    String resStr = resp.body().string();
                    JSONObject json = new JSONObject(resStr);
                    MoResponse data = new Gson().fromJson(String.valueOf(json), MoResponse.class);

                    String code=Integer.toString(data.getCode());
                    log.info("CHARGING | ZONGCHARGING CLASS | ZONG MML RESPONSE CODE | " + code);

                    if (code.equalsIgnoreCase("0000") || code.equalsIgnoreCase("0")|| code.equalsIgnoreCase("000")) {
                        // Insert into Charging DB
                        // For TPay
//                        mydao = new charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
//                                200, incomingPacket.getShortcode(),
//                                scKeyword, 1, partnerid , 0, 0, amount,
//                                incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 0);
//                        mydao.insertRecord();
                        Charging  charging= new Charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                500, incomingPacket.getShortcode(),
                                scKeyword, 0, partnerid, Integer.parseInt(code), 0, amount,
                                incomingPacket.getConnectionPointId(), incomingPacket.getSmsData(), charging_mechanism, 0,1, Time.valueOf("16:08:01"));;
                      //  charging.setOriginal_sms_id();
                        mydao=tbl_charging_service.insert(charging);


                        requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                                incomingPacket.getShortcode(), incomingPacket.getSmsData(),
                                String.valueOf(connectivityPointId), String.valueOf(incomingPacket.getSmsId()),
                                String.valueOf(incomingPacket.getConnectionPointId()));
                        response= requestHandler.sendRequest(false);

                        requestHandler = null;

                        log.info("MSISDN: " + incomingPacket.getMsisdn() + " OperatorId: " + connectivityPointId);

                        if (response.code()==200 || response.isSuccessful()) {
                            //HttpEntity entity = httpResponse.getEntity();
                            //String xmlResponse = EntityUtils.toString(entity);

                            //String[] resp1 = xmlResponse1.split("&");
                            // String replymt = xmlResponse; // resp1[2].substring(5);
                            String replymt = response.body().string().replace("{", "").replace("}", "").replace("[", "").replace("]", "");

                            HTTPSDPMT mthttp = new HTTPSDPMT(incomingPacket.getMsisdn(),
                                    incomingPacket.getShortcode(),
                                    replymt.replaceAll("^\"|\"$", ""));
                            HttpResponse mtResponse = null;
                            mtResponse = mthttp.sendRequest(false);
                            mthttp = null;
                            log.info("The SMS Delivery Status" + mtResponse.getStatusLine().getStatusCode() + mtResponse.getStatusLine().getReasonPhrase());
                            log.info("SMS-ID| " + incomingPacket.getSmsId() + " | ACCEPTED");
                            moMessageObj.setIsAccepted(Boolean.valueOf(true));

                        }
                    } else {
//                        mydao =  charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
//                                500, incomingPacket.getShortcode(),
//                                scKeyword, 0, partnerid, Integer.parseInt(code), 0, amount,
//                                incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 0);
//                        mydao.insertRecord();
                        mydao=tbl_charging_service.insert(new Charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                7000, incomingPacket.getShortcode(),
                                scKeyword, 0, partnerid, 7000, 0, adjustmentAmountRelative,
                                incomingPacket.getConnectionPointId(), incomingPacket.getSmsData(), charging_mechanism, 0,1, Time.valueOf("16:08:01")));


                        if (code == null)
                            log.info("Null Value Received in Zong Response");
                        else
                            log.info("The Zong Response=" + code);
                    }
                       /* byte[] array = new byte[7]; // length is bounded by 7
                        new Random().nextBytes(array);
                        String TransID = new String(array, Charset.forName("UTF-8"));
                        if (code.equalsIgnoreCase("0000")) {
                            // Insert into Charging DB
                            // For TPay
                            mydao = new charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                    200, incomingPacket.getShortcode(),
                                    scKeyword, 1, 4, 0, 0, amount,
                                    incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 0);
                            mydao.insertRecord();

                            requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                                    incomingPacket.getShortcode(), incomingPacket.getSmsText(),
                                    String.valueOf(connectivityPointId), String.valueOf(incomingPacket.getSmsId()),
                                    String.valueOf(incomingPacket.getConnectivityPointId()));
                            response = requestHandler.sendRequest(false);

                            requestHandler = null;

                            log.info("MSISDN: " + incomingPacket.getMsisdn() + " OperatorId: " + connectivityPointId);

                            if (response.isSuccessful()) {
                                String replymt = response.body().string();
                                HTTPSDPMT mthttp = new HTTPSDPMT(incomingPacket.getMsisdn(),
                                        incomingPacket.getShortcode(), replymt);
                                HttpResponse mtResponse = null;
                                mtResponse = mthttp.sendRequest(false);
                                System.out.println(replymt);

                                mthttp = null;
                                log.info("The SMS Delivery Status" + mtResponse.getStatusLine().getStatusCode()
                                        + mtResponse.getStatusLine().getReasonPhrase());
                                log.info("SMS-ID| " + incomingPacket.getSmsId() + " | ACCEPTED");
                                moMessageObj.setIsAccepted(Boolean.valueOf(true));

                            }
                        } else {
                            mydao = new charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                    500, incomingPacket.getShortcode(),
                                    scKeyword, 0, 4, Integer.parseInt(code), 0, amount,
                                    incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 0);
                            mydao.insertRecord();

                            if (code == null)
                                log.info("Null Value Received in Zong Response");
                            else
                                log.info("The Zong Response=" + code);
                        }*/
                    dot_handler = null;
                    mydao = null;
                }
            }
        }
        //code start
        else if (connectivityPointId == 100 || connectivityPointId == 101 || connectivityPointId == 94 || connectivityPointId == 140 || connectivityPointId == 141 || connectivityPointId == 146) {

            if (connectivityPointId == 140 || connectivityPointId == 141 || connectivityPointId == 146) {

                System.out.println("146");
                connectivityPointId = this.WARID_ID;
            } else {
                connectivityPointId = this.MOBILINK_ID;
            }

            long blid = 0;
            int status = 0;

//            blacklist bl = blacklist.selectByMsisdn(incomingPacket.getMsisdn());
            blacklist bl=blacklistRepo.checkBlackList("1");


            if (bl != null) {
                blid = bl.get_id();
                status = bl.get_statuscode();
                log.info(incomingPacket.getMsisdn() + " is Blacklisted. Exiting Application");
            } else {
                log.info(incomingPacket.getMsisdn() + " is not Blacklisted. Sending Charging Request");
                // log.info("status = "+ status + ", Blacklist ID = "+blid);
                if (status == 0 && blid == 0) {
                    String new_subscriberNumber = incomingPacket.getMsisdn().substring(2);
                    String shortcode = String.valueOf(incomingPacket.getShortcode());
                    double adjustmentAmountRelative = 0;

                    Integer shortcode1 = Integer.valueOf(shortcode);

                    if (shortcode1 == 3441)
                        adjustmentAmountRelative = 239;
                    if (shortcode1 == 3443)
                        adjustmentAmountRelative = 598;
                    if (shortcode1 == 3444)
                        adjustmentAmountRelative = 1195;
                    if (shortcode1 == 3445)
                        adjustmentAmountRelative = 2988;

                    if(new_subscriberNumber.equalsIgnoreCase("3033889888")){
                        adjustmentAmountRelative = 100;
                    }

                    // log.info("Deducted Amt = " + adjustmentAmountRelative);
                    if(new_subscriberNumber.equalsIgnoreCase("3020527300")){
                        adjustmentAmountRelative = 1195;
                    }

                    long startTime = System.nanoTime();// this line before you call UCIP API

                    log.info("Start Time before UCIP Call - "+ startTime + " Thread Name " + Thread.currentThread().getName());

                    dot_handler = new JazzWaridChargeRequest();
                    ChargeRequestDTO chargeRequestDTO = getChargeRequestBody(adjustmentAmountRelative,new_subscriberNumber, (int) incomingPacket.getSmsId(),connectivityPointId);
                    Response ucipResponse = dot_handler.sendChargingRequest(chargeRequestDTO);

                    long endTime = System.nanoTime();// this after you receive any response whether success failure etc


                    Gson gson = new Gson();
                    String jsonResponse = ucipResponse.body().string();
                    //  System.out.println("ucipResponse = " + jsonResponse);
                    AppResponse appResponse = gson.fromJson(jsonResponse,AppResponse.class);

                    log.info("End Time After UCIP Call - "+ endTime + " Thread Name " + Thread.currentThread().getName());
                    if(appResponse == null){
                        //System.out.println("chargeRequestDTO = " + gson.toJson(chargeRequestDTO));
                    }

                    log.info("Running Time Of  UCIP Call - "+ ((endTime-startTime)/1000) + " Thread Name " + Thread.currentThread().getName());

                    if(appResponse.getCode()== Constants.IS_POSTPAID && ucipResponse.isSuccessful()) {

                        String[] splited = incomingPacket.getSmsData().split("\\s+");
                        String SCkeyword = splited[0];

//                        mydao = tbl_charging_service.insertInTo((incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
//                                7000, incomingPacket.getShortcode(),
//                                SCkeyword, 0, partnerid, 7000, 0, adjustmentAmountRelative,
//                                incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 1));

                        mydao=tbl_charging_service.insert(new Charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                7000, incomingPacket.getShortcode(),
                                SCkeyword, 0, partnerid, 7000, 0, adjustmentAmountRelative,
                                incomingPacket.getConnectionPointId(), incomingPacket.getSmsData(), charging_mechanism, 0,1, Time.valueOf("16:08:01")));

                        if (ucipResponse == null)
                            log.info("Null Value Received in UCIP Response");
                        else
                            log.info("The UCIP Response=" + appResponse.getCode());
                    }else {

                        int responseCode = -1;
                        String TransID = "";
                        if (appResponse != null) {
                            responseCode = appResponse.getCode(); // ResponseCode
                            TransID = appResponse.getTransID();
                        }

                        log.info("subscribeNumber : " + new_subscriberNumber + ",  SC : " + shortcode + ", amount : "
                                + adjustmentAmountRelative + ", StatusCode : "
                                + appResponse.getCode() + ", TransactionID : " + TransID
                                + ", ResponseCode : " + responseCode);

                        String[] splited = incomingPacket.getSmsData().split("\\s+");
                        String SCkeyword = splited[0];


                        if (responseCode == 0 && ucipResponse.isSuccessful()) {
                            // Insert into Charging DB
                            // For PCom
//                            mydao = new charging(incomingPacket.getSmsId(), new_subscriberNumber, TransID,
//                                    responseCode, incomingPacket.getShortcode(),
//                                    SCkeyword, 1, partnerid, responseCode, 0, adjustmentAmountRelative,
//                                    incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism,0);
                            // For Centili
                            // mydao = new charging(incomingPacket.getSmsId(),new_subscriberNumber,TransID,
                            // httpDotRes.getStatusLine().getStatusCode(),incomingPacket.getShortcode(),
                            // SCkeyword,1,2,responseCode,0,adjustmentAmountRelative,incomingPacket.getConnectivityPointId(),incomingPacket.getSmsText());
                            mydao=tbl_charging_service.insert(new Charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                    7000, incomingPacket.getShortcode(),
                                    SCkeyword, 0, partnerid, 7000, 0, adjustmentAmountRelative,
                                    incomingPacket.getConnectionPointId(), incomingPacket.getSmsData(), charging_mechanism, 0,1, Time.valueOf("16:08:01")));

                            log.info("SMS-ID:  " + incomingPacket.getSmsId() + ", MSISDN: " + incomingPacket.getMsisdn()
                                    + " UCIP Transaction of " + (adjustmentAmountRelative / 100) + " is Charged");
                            requestHandler = new HttpRequestHandler(incomingPacket.getMsisdn(),
                                    incomingPacket.getShortcode(), incomingPacket.getSmsData(),
                                    String.valueOf(connectivityPointId), String.valueOf(incomingPacket.getSmsId()),
                                    String.valueOf(incomingPacket.getConnectionPointId()));
                            response = requestHandler.sendRequest(false);
                            requestHandler = null;

                            if (response.isSuccessful()) {
                                String replymt = response.body().string();
                                HTTPSDPMT mthttp = new HTTPSDPMT(incomingPacket.getMsisdn(),
                                        incomingPacket.getShortcode(), replymt);
                                HttpResponse mtResponse = null;
                                mtResponse = mthttp.sendRequest(false);
                                // System.out.println(replymt);

                                mthttp = null;
                                log.info("The SMS Delivery Status" + mtResponse.getStatusLine().getStatusCode()
                                        + mtResponse.getStatusLine().getReasonPhrase());
                                log.info("SMS-ID| " + incomingPacket.getSmsId() + " | ACCEPTED");
                                moMessageObj.setIsAccepted(Boolean.valueOf(true));

                            }
                        } else {
                            // Insert into Charging DB
                            // For TPay
//                            mydao = new charging(incomingPacket.getSmsId(), new_subscriberNumber, TransID,
//                                    responseCode, incomingPacket.getShortcode(),
//                                    SCkeyword, 1, partnerid, responseCode, 0, adjustmentAmountRelative,
//                                    incomingPacket.getConnectivityPointId(), incomingPacket.getSmsText(), charging_mechanism, 0);
                            // For Centili
                            // mydao = new charging(incomingPacket.getSmsId(),new_subscriberNumber,TransID,
                            // httpDotRes.getStatusLine().getStatusCode(),incomingPacket.getShortcode(),
                            // SCkeyword,1,2,responseCode,0,adjustmentAmountRelative,incomingPacket.getConnectivityPointId(),incomingPacket.getSmsText());
                            mydao=tbl_charging_service.insert(new Charging(incomingPacket.getSmsId(), new_subscriberNumber, String.valueOf(incomingPacket.getSmsId()),
                                    7000, incomingPacket.getShortcode(),
                                    SCkeyword, 0, partnerid, 7000, 0, adjustmentAmountRelative,
                                    incomingPacket.getConnectionPointId(), incomingPacket.getSmsData(), charging_mechanism, 0,1, Time.valueOf("16:08:01")));



                            if (appResponse == null)
                                log.info("Null Value Received in UCIP Response");
                            else
                                log.info("The UCIP Response=" + appResponse.getCode());
                        }
                    }


                    dot_handler = null;
                    mydao = null;

                }
            }
        }

        else {
            connectivityPointId = incomingPacket.getConnectionPointId();
            log.info("Invalid Connection Point ID");
        }

        return moMessageObj;

    } catch (Throwable e) {
        log.error(e, e);

    }

        moMessageObj.setIsAccepted(Boolean.valueOf(false));
        return moMessageObj;
    }

    private static ChargeRequestDTO getChargeRequestBody(double adjustmentAmountRelative, String new_subscriberNumber, Integer smsId, int connectivityPointId) {
        ChargeRequestDTO chargeRequestDTO = new ChargeRequestDTO();
        chargeRequestDTO.setAmount(String.valueOf(adjustmentAmountRelative));
        chargeRequestDTO.setMsisdn(new_subscriberNumber);
        chargeRequestDTO.setOperatorId(connectivityPointId);
        chargeRequestDTO.setTransactionId(String.valueOf(smsId));
        return chargeRequestDTO;
    }


    protected String[] xmlConversion(String xml) {
        String[] retArray = new String[2];
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));

            Document doc = docBuilder.parse(src);

            // normalize text representation
            doc.getDocumentElement().normalize();

            NodeList listOfPersons = doc.getElementsByTagName("member");

            System.out.println(listOfPersons.getLength());

            if (listOfPersons.getLength() == 2) {

                Node firstPersonNode11 = listOfPersons.item(0);

                if (firstPersonNode11.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) firstPersonNode11;

                    retArray[0] = eElement.getElementsByTagName("value").item(0).getTextContent();
                }    //end of if clause

                // Return Response Code
                Node firstPersonNode22 = listOfPersons.item(1);
                if (firstPersonNode22.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstPersonElement22 = (Element) firstPersonNode22;

                    retArray[1] = firstPersonElement22.getElementsByTagName("value").item(0).getTextContent();
                } //end of if clause

            } else {
                //Return Transaction ID
                Node firstPersonNode = listOfPersons.item(16);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstPersonElement = (Element) firstPersonNode;

                    //-------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("string");
                    Element lastNameElement = (Element) lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    System.out.println("Para 1 Value : " + ((Node) textLNList.item(0)).getNodeValue().trim());
                    retArray[0] = ((Node) textLNList.item(0)).getNodeValue().trim();

                } //End Transaction IF
                //Return Response Code
                Node firstPersonNode1 = listOfPersons.item(17);
                if (firstPersonNode1.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstPersonElement1 = (Element) firstPersonNode1;

                    NodeList lastNameList = firstPersonElement1.getElementsByTagName("i4");
                    Element lastNameElement = (Element) lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    retArray[1] = ((Node) textLNList.item(0)).getNodeValue().trim();

                } //End Response Code IF
            }
        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return retArray;

    }

}
