package com.noetic.gwpartner.timwe.Alerts;


import org.apache.log4j.Logger;

public class Alerts extends Thread{

    private boolean active = true;
    private int operatorId;
    private int seconds = 0;
    private int minutes = 0;
    private static Logger log = Logger.getLogger(Alerts.class);

    public Alerts() {

    }

    public Alerts(String threadName) {
        System.out.println("Starting... "+threadName);
        setName(threadName);
    }
    public Alerts(String threadName, int operatorId) {
        System.out.println("Starting... "+threadName);
        setName(threadName);
        setOperatorId(operatorId);
    }

    @Override
    public void run() {
        try {
            while(minutes!=60)                                  //timer of one hour!
            {
                while(seconds!=60)
                {
                    if(!isActive())                              //if related connectionPointId will be found then is active will be set to false. to prevent any further execution of the timer.
                    {
                        return;
                    }
                    Thread.sleep(1000);                             //Thread.sleep(1000);
                    ++seconds;
                }
                seconds = 0;
                ++minutes;
            }
            //Thread.sleep(10000); //set the time here of one hour
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(isActive())                                         // if the record is not received with in specified time then alert will be initiated in below line.
//		System.out.println("Terminated..........");
        {	try {
            log.info("GENERATING-ALERT-FOR: "+getName());
            new InitiateAlert(String.valueOf(operatorId),4).start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
