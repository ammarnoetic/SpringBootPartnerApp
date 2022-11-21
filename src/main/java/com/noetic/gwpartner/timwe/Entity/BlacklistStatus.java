package com.noetic.gwpartner.timwe.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlacklistStatus {

    @Id
    @Column(name="id")
    private long id;
    @Column(name = "blacklist_id")
    private long blacklist_id;
    @Column(name = "status_id")
    private long status_id;
    @Column(name = "cdate")
    private String cdate;
    @Column(name = "desc")
    private String desc;
    @Column(name = "statuscode")
    private int statuscode;
    @Column(name = " userid")
    private int userid;
    @Column(name = "mdate")
    private String mdate;


    //Get Methods
    public long get_id() {
        return this.id;
    }
    public long get_blacklist_id() {
        return this.blacklist_id;
    }
    public long get_status_id() {
        return this.status_id;
    }
    public String get_cdate() {
        return this.cdate;
    }
    public String get_desc() {
        return this.desc;
    }
    public int get_statuscode() {
        return this.statuscode;
    }
    public int get_userid() {
        return this.userid;
    }
    public String get_mdate() {
        return this.mdate;
    }

    //Set Methods
    public void set_id(long id) {
        this.id=id;
    }
    public void set_blacklist_id(long blacklist_id) {
        this.blacklist_id=blacklist_id;
    }
    public void set_status_id(long status_id) {
        this.status_id=status_id;
    }
    public void set_cdate(String cdate) {
        this.cdate = cdate;
    }
    public void set_desc(String desc) {
        this.desc = desc;
    }
    public void set_statuscode(int statuscode) {
        this.statuscode = statuscode;
    }
    public void set_userid(int userid) {
        this.userid = userid;
    }
    public void set_mdate(String mdate) {
        this.mdate = mdate;
    }

}
