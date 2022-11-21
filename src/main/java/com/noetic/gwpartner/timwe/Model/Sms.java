package com.noetic.gwpartner.timwe.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sms {

    private String msisdn;
    private String shortcode;
    private String smsData;
    private int connectionPointId;

    @Id
    private long smsId;

    public Sms() {
    }

    public Sms(String msisdn, String shortcode, String smsData,
               int connectionPointId, long smsId) {
        super();
        this.msisdn = msisdn;
        this.shortcode = shortcode;
        this.smsData = smsData;
        this.connectionPointId = connectionPointId;
        this.smsId = smsId;
    }

    public String getMsisdn() {
        return msisdn;
    }
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public String getShortcode() {
        return shortcode;
    }
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
    public String getSmsData() {
        return smsData;
    }
    public void setSmsData(String smsData) {
        this.smsData = smsData;
    }
    public int getConnectionPointId() {
        return connectionPointId;
    }
    public void setConnectionPointId(int connectionPointId) {
        this.connectionPointId = connectionPointId;
    }
    public long getSmsId() {
        return smsId;
    }
    public void setSmsId(long smsId) {
        this.smsId = smsId;
    }


    //for testing


    @Override
    public String toString() {
        return "Sms{" +
                "msisdn='" + msisdn + '\'' +
                ", shortcode='" + shortcode + '\'' +
                ", smsData='" + smsData + '\'' +
                ", connectionPointId=" + connectionPointId +
                ", smsId=" + smsId +
                '}';
    }
}
