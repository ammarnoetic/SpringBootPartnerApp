package com.noetic.gwpartner.timwe.Entity;


import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_charging", schema = "public",catalog = "ucip_db")
public class Charging {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name="original_sms_id")
    private long original_sms_id;
    @Column(name = " subscribernumber")
    private String subscriberNumber;
    @Column(name = " origintransactionid")
    private String originTransactionID;
    @Column(name ="origintimestamp")
    private Timestamp originTimeStamp;
    @Column(name = "statuscode")
    private int statuscode;
    @Column(name = "shortcode")
    private String shortcode;
    @Column(name="keyword")
    private String keyword;
    @Column(name="ischarged")
    private int ischarged;
    @Column(name="partnerid")
    private int partnerid;
    @Column(name="responsecode")
    private int responsecode;
    @Column(name = "attempt")
    private int attempt;
    @Column(name="adjustmentamountrelative")
    private double adjustmentAmountRelative;
    @Column(name=" operatorid")
    private int operatorid;
    @Column(name = " smstext")
    private String smstext;
    @Column(name = "charging_mechanism")
    private int chargingMechanism;
    @Column(name = "is_postpaid")
    private int isPostPaid;
    @Column(name = "num_of_attempts")
    private int num_of_attempts;
    @Column(name = "last_attempt_time")
    private Time last_attempt_time;

    public Charging(){

    }

    public Charging(long original_sms_id, String subscriberNumber, String originTransactionID, int statuscode, String shortcode, String keyword, int ischarged, int partnerid, int responsecode, int attempt, double adjustmentAmountRelative2, int operatorid, String smstext, int chargingMechanism, int isPostPaid,
                    int num_of_attempts,Time last_attempt_time) {
        this.original_sms_id = original_sms_id;
        this.subscriberNumber = subscriberNumber;
        this.originTransactionID = originTransactionID;
        this.statuscode = statuscode;
        this.shortcode = shortcode;
        this.keyword = keyword;
        this.ischarged = ischarged;
        this.partnerid = partnerid;
        this.responsecode = responsecode;
        this.attempt = attempt;
        this.adjustmentAmountRelative = adjustmentAmountRelative2;
        this.operatorid = operatorid;
        this.smstext = smstext;
        this.chargingMechanism = chargingMechanism;
        this.isPostPaid = isPostPaid;
        this.num_of_attempts=num_of_attempts;
        this.last_attempt_time=last_attempt_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginal_sms_id() {
        return original_sms_id;
    }

    public void setOriginal_sms_id(long original_sms_id) {
        this.original_sms_id = original_sms_id;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    public String getOriginTransactionID() {
        return originTransactionID;
    }

    public void setOriginTransactionID(String originTransactionID) {
        this.originTransactionID = originTransactionID;
    }

    public Timestamp getOriginTimeStamp() {
        return originTimeStamp;
    }

    public void setOriginTimeStamp(Timestamp originTimeStamp) {
        this.originTimeStamp = originTimeStamp;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getIscharged() {
        return ischarged;
    }

    public void setIscharged(int ischarged) {
        this.ischarged = ischarged;
    }

    public int getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(int partnerid) {
        this.partnerid = partnerid;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public double getAdjustmentAmountRelative() {
        return adjustmentAmountRelative;
    }

    public void setAdjustmentAmountRelative(double adjustmentAmountRelative) {
        this.adjustmentAmountRelative = adjustmentAmountRelative;
    }

    public int getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(int operatorid) {
        this.operatorid = operatorid;
    }

    public String getSmstext() {
        return smstext;
    }

    public void setSmstext(String smstext) {
        this.smstext = smstext;
    }

    public int getChargingMechanism() {
        return chargingMechanism;
    }

    public void setChargingMechanism(int chargingMechanism) {
        this.chargingMechanism = chargingMechanism;
    }

    public int getIsPostPaid() {
        return isPostPaid;
    }

    public void setIsPostPaid(int isPostPaid) {
        this.isPostPaid = isPostPaid;
    }

    public int getNum_of_attempts() {
        return num_of_attempts;
    }

    public void setNum_of_attempts(int num_of_attempts) {
        this.num_of_attempts = num_of_attempts;
    }

    public Time getLast_attempt_time() {
        return last_attempt_time;
    }

    public void setLast_attempt_time(Time last_attempt_time) {
        this.last_attempt_time = last_attempt_time;
    }
}
