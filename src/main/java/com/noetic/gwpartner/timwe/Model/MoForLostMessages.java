package com.noetic.gwpartner.timwe.Model;

import com.noetic.gwpartner.timwe.constants.Constants;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pk.com.digitania.httpapi.message.MoMessageObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MoForLostMessages {

    static Logger log = LoggerFactory.getLogger(MoReceiver.class);
    private boolean file_from_sms_table = true;
    private boolean file_from_backend_logs = false;
    File logFile = new File("/home/developer1/postgres/sms_timwe_20141009-20141016_3.csv");
    //File logFile = new File("/home/developer1/postgres/timwe_reinjection_files/20140410_1400-20140411_1000(1).csv");
    private Integer sms_id = 88391976;

    protected MoMessageObject processSMSRequest(MoMessageObject incomingPacket) {

        MoMessageObject moMessageObj = new MoMessageObject();

        try {
            String text = incomingPacket.getSmsText().trim();

            if (text.startsWith(Constants.TIMWE_KEYWORD_MCN)
                    || text.startsWith(Constants.TIMWE_KEYWORD_SHMO)
                    || text.startsWith(Constants.TIMWE_KEYWORD_YMN)
                    || text.startsWith(Constants.TIMWE_KEYWORD_GAME)
                    || text.startsWith(Constants.TIMWE_KEYWORD_SVPK)) {

                HttpRequestHandler requestHandler = new HttpRequestHandler(
                        incomingPacket.getMsisdn(), incomingPacket.getShortcode(), incomingPacket.getSmsText(),
                        String.valueOf(incomingPacket.getConnectivityPointId()),
                        String.valueOf(incomingPacket.getSmsId()),
                        String.valueOf(incomingPacket.getConnectivityPointId()));

                Response httpResponse = null;
                httpResponse = requestHandler.sendRequest(false);
                requestHandler = null;

                log.info("MSISDN: " + incomingPacket.getMsisdn() + " OperatorId: " + incomingPacket.getConnectivityPointId());

                if (httpResponse.isSuccessful()) {
                    moMessageObj.setIsAccepted(true);
                    return moMessageObj;
                }
            }
        } catch (Throwable e) {
            log.error(String.valueOf(e), e);
            e.printStackTrace();
        }
        //}

        moMessageObj.setIsAccepted(false);
        return moMessageObj;
    }

    private void sendLostMessagesFromFile() {
        String line = null;
        int records = 0;

        try {
            BufferedReader bufRdr = new BufferedReader(new FileReader(logFile));
            while((line = bufRdr.readLine()) != null) {
                try {
                    processLine(line);
                    records++;
                }
                catch(NoSuchElementException e) {
                    //e.printStackTrace();
                    System.out.println(line);
                    System.out.println("Wrong Input: NoSuchElement");
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total records found: " + records);
        System.out.println("Wrong msisdns size: " + wrongMsisdns.size());
    }

    private void processLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line,"	");

        MoMessageObject incomingPacket;

        if(file_from_sms_table) {
            String smsId = tokenizer.nextToken();
            tokenizer.nextToken();
            //String connectivityPointId = tokenizer.nextToken();
            String msisdn = tokenizer.nextToken();
            String shortcode = tokenizer.nextToken();
            tokenizer.nextToken();
            String smsText = tokenizer.nextToken();
            String connectivityPointId = checkConnectivityPointId(msisdn, shortcode);

            incomingPacket = new MoMessageObject();
            incomingPacket.setMsisdn(msisdn);
            incomingPacket.setConnectivityPointId(Integer.valueOf(connectivityPointId.trim()));
            incomingPacket.setSmsText(smsText);
            incomingPacket.setSmsId(Integer.valueOf(smsId.trim()));
            incomingPacket.setShortcode(shortcode);
            System.out.println("MSISDN: " + msisdn + " ConnectivityId: " + connectivityPointId + " shortcode: " + shortcode + " smstext: " + smsText + " smsid: " + smsId);
        }
        else if(file_from_backend_logs) { // file is created from sdp backend logs. IncomingRequestHandler -> IN-PROTOCOL (log)
            String shortcode;
            String msisdn;
            String text;
            String connPointId;
            //warid connection point log
            if(tokenizer.countTokens() == 5) {
                tokenizer.nextToken();
                tokenizer.nextToken();
                shortcode = tokenizer.nextToken();
                msisdn = tokenizer.nextToken();
                text = tokenizer.nextToken();
                connPointId = checkConnectivityPointId(msisdn, null);
            }
            else {
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                shortcode = tokenizer.nextToken();
                msisdn = tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                text = tokenizer.nextToken();
                connPointId = checkConnectivityPointId(msisdn, null);
            }
            incomingPacket = new MoMessageObject();
            incomingPacket.setMsisdn(msisdn);
            incomingPacket.setConnectivityPointId(Integer.valueOf(connPointId));
            incomingPacket.setSmsText(text);
            incomingPacket.setSmsId(sms_id++);
            incomingPacket.setShortcode(shortcode);
            System.out.println(msisdn);
        }
        else {  //file is from timewe logs
            tokenizer.nextToken();
            String msisdn = tokenizer.nextToken();
            tokenizer.nextToken();
            String shortcode = tokenizer.nextToken();
            String smsText = tokenizer.nextToken();
            String smsId = tokenizer.nextToken();
            String connectivityPointId = checkConnectivityPointId(msisdn, shortcode);

            incomingPacket = new MoMessageObject();
            incomingPacket.setMsisdn(msisdn);
            incomingPacket.setConnectivityPointId(Integer.valueOf(connectivityPointId.trim()));
            incomingPacket.setSmsText(smsText);
            incomingPacket.setSmsId(Integer.valueOf(smsId.trim()));
            incomingPacket.setShortcode(shortcode);
            System.out.println("MSISDN: " + msisdn + " ConnectivityId: " + connectivityPointId + " shortcode: " + shortcode + " smstext: " + smsText + " smsid: " + smsId);
        }
        processSMSRequest(incomingPacket);

    }

    private String checkConnectivityPointId(String msisdn, String shortcode) {
        String connPointId = "";
        Integer code = null;
        if(msisdn.startsWith("92")) {
            code = Integer.valueOf(msisdn.substring(2,4));
        }
        else {
            wrongMsisdns.add(msisdn);
        }
        switch(code) {
            case 30: {
                connPointId = "3";  //MOBILINK
                break;
            }
            case 31: {
                connPointId = "10";  //ZONG
                break;
            }
            case 32: {
                connPointId = "55"; //WARID
                break;
            }
            case 33: {
                connPointId = "71";  //UFONE
                break;
				/*if(shortcode.equals("3441")) {
					connPointId = "4";
				}
				else if(shortcode.equals("3443")) {
					connPointId = "5";
				}
				else if(shortcode.equals("3444")) {
					connPointId = "6";
				}*/
            }
            case 34: {
                connPointId = "77";  //TELENOR
                break;
				/*if(shortcode.equals("3441")) {
					connPointId = "77";
				}
				else if(shortcode.equals("3443")) {
					connPointId = "78";
				}
				else if(shortcode.equals("3444")) {
					connPointId = "79";
				}*/

            }
            default: {
                return "77";
            }
        }
        return connPointId;
    }

    List<String> wrongMsisdns = new ArrayList<String>();

    public static void main(String[] argv) {
        new MoForLostMessages().sendLostMessagesFromFile();
    }
    /*
     * private MoMessageObject returnError(MoMessageObject incomingPacket) {
     * incomingPacket.setIsAccepted(false);
     * incomingPacket.setSmsText(Constants.ERROR_GENERIC); return
     * incomingPacket; }
     */
}
