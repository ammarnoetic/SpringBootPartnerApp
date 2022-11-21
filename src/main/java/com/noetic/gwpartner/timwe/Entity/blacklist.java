package com.noetic.gwpartner.timwe.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "tbl_blacklist", schema = "public",catalog = "ucip_db")

public class blacklist {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "msisdn")
    private String msisdn;
    @Column(name = "cdate")
    private String cdate;
    @Column(name = "mdate")
    private String mdate;
    @Column(name = "userid")
    private int userid;
    @Column(name = "statuscode")
    private int statuscode;
    @Column(name = "status")
    private int status;
    @Column(name = "modify_date")
    private Date modify_date;

    //Get Methods
    public long get_id() {
        return this.id;
    }
    public String get_msisdn() {
        return this.msisdn;
    }
    public String get_cdate() {
        return this.cdate;
    }
    public String get_mdate() {
        return this.mdate;
    }
    public int get_userid() {
        return this.userid;
    }
    public int get_statuscode() {
        return this.statuscode;
    }

    public int getStatus() {
        return status;
    }

    public Date getModify_date() {
        return modify_date;
    }

    //Set Methods
    public void set_id(long id) {
        this.id=id;
    }
    public void set_msisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public void set_cdate(String cdate) {
        this.cdate = cdate;
    }
    public void set_mdate(String mdate) {
        this.mdate = mdate;
    }
    public void set_userid(int userid) {
        this.userid = userid;
    }
    public void set_statuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
