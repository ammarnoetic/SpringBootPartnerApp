package com.noetic.gwpartner.timwe.Model;

import com.noetic.gwpartner.timwe.constants.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HTTPWaridRequest {

    private String methodName ="UpdateBalanceAndDate";
    //private String transactionCurrency="PKR";
    //private String originNodeType="test";
    //private String originHostName="test";
    //private String transactionType="test";
    //private String transactionCode="test";
    //private String externalData1="test_VAS";
    //private String externalData2="test_VAS";

    private String transactionCurrency="PKR";
    private String originNodeType="Noetic";
    private String originHostName="gameinapp";
    private String transactionCode="Gameinapp";


    public static String username="NeoticGame";
    public static String password="jazz123";

    private String subscriberNumber;
    private double adjustmentAmountRelative;
    private String originTransactionID;
    private String originTimeStamp;

    private static final Logger log = Logger.getLogger(HTTPWaridRequest.class);

    public HTTPWaridRequest(String subscriberNumber, double adjustmentAmountRelative2, String originTransactionID){

        this.subscriberNumber = subscriberNumber;
        this.adjustmentAmountRelative = adjustmentAmountRelative2;
        this.originTransactionID = originTransactionID;
    }

    public HttpResponse sendRequest(boolean requiresAuthorization) throws ClientProtocolException, IOException {
		/*//#################################################
		PostMethod postMethod = new PostMethod();
		//#################################################*/
        HttpResponse httpResponse=null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpRequest = getHttpRequest(requiresAuthorization);
        httpResponse = httpclient.execute(httpRequest);
        return httpResponse;
    }

    private HttpPost getHttpRequest(boolean requiresAuthorization) throws UnsupportedEncodingException {

        Date date = new Date(System.currentTimeMillis());

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        TimeZone PKT = TimeZone.getTimeZone("Asia/Karachi");
        dateFormat.setTimeZone(PKT);
        this.originTimeStamp = dateFormat.format(date);

        //String originDateTime = dateFormat.format(date)+"+0500";

        //log.info(originDateTime);

        HttpPost httpRequest=null;
        httpRequest = new HttpPost(Constants.Warid_URL);

        //Authorization

        String user_password = username+":"+password;

        String Authorization = null;


        final String encoded = new String(Base64.encodeBase64(user_password.getBytes()));
        //log.info("U-P: "+ user_password +", Auth_Byte: "+ encoded);

        Authorization =  "Basic " + encoded;
        //log.info(Authorization);

        httpRequest.addHeader("Content-Type", "text/xml");
        httpRequest.addHeader("User-Agent", "UGw Server/4.3/1.0");
        httpRequest.addHeader("charset","ISO-8859-1");
        httpRequest.addHeader("Authorization", Authorization);
        //httpRequest.addHeader("Content-Length", "1440");
        httpRequest.addHeader("Expect", "100-continue");
        httpRequest.addHeader("Pragma", "no-cache");
        httpRequest.addHeader("Host", "10.11.221.81:10010");
        //httpRequest.addHeader("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        //httpRequest.addHeader("Connection", "keep-alive");


        int adjustmentamount = (int)this.adjustmentAmountRelative;

        String inputXML = "<?xml version=\"1.0\" ?>\n" +
                "<methodCall>\n" +
                "<methodName>" + this.methodName + "</methodName>\n" +
                "<params>\n" +
                "<param>\n" +
                "<value>\n" +
                "<struct>\n" +
                "<member>\n" +
                "<name>originNodeType</name>\n" +
                "<value><string>" + this.originNodeType + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>originHostName</name>\n" +
                "<value><string>" + this.originHostName + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>originTransactionID</name>\n" +
                "<value><string>" + this.originTransactionID + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>originTimeStamp</name>\n" +
                "<value><dateTime.iso8601>" + this.originTimeStamp + "+0500</dateTime.iso8601></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>subscriberNumber</name>\n" +
                //"<value><string>3246822732</string></value>\n" +
                "<value><string>" + this.subscriberNumber + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>adjustmentAmountRelative</name>\n" +
                "<value><string>-" + adjustmentamount + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>transactionCurrency</name>\n" +
                "<value><string>" + this.transactionCurrency + "</string></value>\n" +
                "</member>\n" +
                "<member>\n" +
                "<name>transactionCode</name>\n" +
                "<value><string>" + this.transactionCode + "</string></value>\n" +
                "</member>\n" +
                "</struct>\n" +
                "</value>\n" +
                "</param>\n" +
                "</params>\n" +
                "</methodCall>";


        //log.info(inputXML);

        httpRequest.setEntity(new StringEntity(inputXML));

        //log.info(httpRequest);

        return httpRequest;

    }


}
