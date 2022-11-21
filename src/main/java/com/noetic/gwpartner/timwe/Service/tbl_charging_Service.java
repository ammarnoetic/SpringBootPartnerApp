package com.noetic.gwpartner.timwe.Service;

import com.noetic.gwpartner.timwe.Entity.BlacklistStatus;
import com.noetic.gwpartner.timwe.Entity.Charging;
import com.noetic.gwpartner.timwe.Entity.Status;
import com.noetic.gwpartner.timwe.Repository.tbl_charging_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class tbl_charging_Service {

    @Autowired
    tbl_charging_Repository tbl_charging_repository;

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Charging InsertInTableCharging(Charging charging)
    {
        return tbl_charging_repository.save(charging);
    }

    public List<Charging>  GetSubscriberNumbers(String subscriberNumber)
    {
        return  tbl_charging_repository.GetSubscriberNumber(subscriberNumber);
    }

    public int getDotTodaysChargedCount(Charging charging)
    {
        return Integer.parseInt(tbl_charging_repository.GetDotTodaysChargedCount(charging));
    }

    public Charging insertInTo(){
        return tbl_charging_repository.insertRecord();
    }

    public Charging insert(Charging charging)
    {
        return  tbl_charging_repository.save(charging);
    }

    public Status selectStatus(Long status){

       // return tbl_charging_repository.select(status);
        return (Status) tbl_charging_repository.findAllById(Collections.singleton(status));


    }

    public BlacklistStatus blacklistStatus(Long blacklist_id){
        return (BlacklistStatus) tbl_charging_repository.findAllById(Collections.singleton(blacklist_id));

    }

    public Integer findLastRecord(String fromdate,String todate ) throws ParseException {


        String fromdatee = fromdate + " 00:00:00";
        String todatee = todate + " 23:59:59";
        return tbl_charging_repository.findLastRecord(formatter.parse(fromdatee), (Date) formatter.parse(todatee));


    }


}
