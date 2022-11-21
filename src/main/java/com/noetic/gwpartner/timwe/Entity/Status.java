package com.noetic.gwpartner.timwe.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "tbl_status", schema = "public",catalog = "ucip_db")
public class Status {

    //DB Attributes
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String desc;
    @Column(name = "cdate")
    private Date cdate;
    @Column(name = "status")
    private int statuscode;

    //Get Methods
    public long get_id() {
        return this.id;
    }
    public String get_name() {
        return this.name;
    }
    public String get_desc() {
        return this.desc;
    }
    public Date get_cdate() {
        return this.cdate;
    }
    public int get_statuscode() {
        return this.statuscode;
    }


    //Set Methods
    public void set_id(long id) {
        this.id=id;
    }
    public void set_name(String name) {
        this.name = name;
    }
    public void set_desc(String desc) {
        this.desc = desc;
    }
    public void set_cdate(Date cdate) {
        this.cdate = cdate;
    }
    public void set_statuscode(int statuscode) {
        this.statuscode = statuscode;
    }
}
