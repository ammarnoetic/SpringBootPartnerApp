package com.noetic.gwpartner.timwe.Alerts;

import com.noetic.gwpartner.timwe.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;

public class InitiateAlert extends Thread {

    private static Process p;
    private static BufferedReader reader;
    private static String str;
    private static String opId;
    private int status = 0;  //1,2,3,4

    public InitiateAlert() {

    }

    @Override
    public void run() {
        try {
            if (status == 1) {
                InitiateSuccessAlert();
            } else if (status == 2) {
                InitiateFailureAlert();
            } else if (status == 3) {
                InitiateOperatorSuccessAlert();
            } else if (status == 4) {
                InitiateOperatorFailureAlert();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public InitiateAlert(String operatorId, int status) {
        this.opId = operatorId;
        this.status = status;
    }

    public void InitiateOperatorSuccessAlert() throws IOException {
        p = Runtime
                .getRuntime()
                .exec("ssh root@192.168.127.4 java -jar /home/Third\\ Party\\ Alerts/TimWe/TimWeAlert.jar " + Constants.phoneNumber1.toString() + " " + Constants.phoneNumber2.toString() + " " + this.opId + " success");
//		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		str = "";
//		while ((str = reader.readLine()) != null) {
//			System.out.println(str);
//		}
    }

    public void InitiateOperatorFailureAlert() throws IOException {
        p = Runtime
                .getRuntime()
                .exec("ssh root@192.168.127.4 java -jar /home/Third\\ Party\\ Alerts/TimWe/TimWeAlert.jar " + Constants.phoneNumber1.toString() + " " + Constants.phoneNumber2.toString() + " " + this.opId + " failure");
//		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		str = "";
//		while ((str = reader.readLine()) != null) {
//			System.out.println(str);
//		}
    }


    public void InitiateSuccessAlert() throws IOException {
        p = Runtime
                .getRuntime()
                .exec(
                        "ssh root@192.168.127.4 java -jar /home/Third\\ Party\\ Alerts/TimWe/TimWeAlert.jar "
                                + Constants.phoneNumber1.toString()
                                + " "
                                + Constants.phoneNumber2.toString()
                                + " "
                                + "success");
//		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		str = "";
//		while ((str = reader.readLine()) != null) {
//			System.out.println(str);
//		}
    }

    public void InitiateFailureAlert() throws IOException {
        p = Runtime.getRuntime().exec("ssh root@192.168.127.4 java -jar /home/Third\\ Party\\ Alerts/TimWe/TimWeAlert.jar " + Constants.phoneNumber1.toString() + " " + Constants.phoneNumber2.toString() + " failure");
//		reader = new BufferedReader(new InputStreamReader(p
//				.getInputStream()));
//		str = "";
//		while ((str = reader.readLine()) != null) {
//			System.out.println(str);
//		}
    }
}
