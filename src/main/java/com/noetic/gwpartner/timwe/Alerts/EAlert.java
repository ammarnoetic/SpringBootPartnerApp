package com.noetic.gwpartner.timwe.Alerts;


import org.apache.log4j.Logger;

public class EAlert extends Thread{

    private boolean active = false;
    private boolean whileTrue = true;
    private int operatorId;
    private static Logger log = Logger.getLogger(Alerts.class);

    public EAlert() {

    }

    public EAlert(String threadName) {
        System.out.println("Starting... "+threadName);
        setName(threadName);
    }
    public EAlert(String threadName, int operatorId) {
        System.out.println("Starting... "+threadName);
        setName(threadName);
        setOperatorId(operatorId);
    }

    @Override
    public void run() {
        try {
            while(whileTrue)                                  //timer of one hour!
            {
                while(whileTrue)
                {
                    for (int i = 0; i < 60; i++) {
                        for (int j = 0; j < 60; j++) {
                            if(isActive())
                                break;
                            Thread.sleep(1000);
                        }
                    }
                }
                try {
                    log.info("GENERATING-ALERT-FOR: "+getName());
                    new InitiateAlert(String.valueOf(operatorId),4).start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }
}
