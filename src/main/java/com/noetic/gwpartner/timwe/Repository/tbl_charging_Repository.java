package com.noetic.gwpartner.timwe.Repository;


import com.noetic.gwpartner.timwe.Entity.Charging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface tbl_charging_Repository extends JpaRepository<Charging, Long> {

//    @Query(value = "Select count(*) from public.sms where sms_timestamp between :sdate and :edate and sms_direction=1 ",nativeQuery = true)
//    public int GetTotalCount(@Param("sdate") Timestamp startdate, @Param("edate") Timestamp enddate);
//
//
    @Query(value = "SELECT * FROM tbl_charging WHERE subscribernumber = :subscriberNumber ",nativeQuery = true )
    List<Charging> GetSubscriberNumber(String subscriberNumber);

    @Query(value = "select count(*) as total from tbl_charging where origintimestamp >= CURRENT_DATE::timestamp " +
            "and origintimestamp < now()::timestamp and charging_mechanism = 2 and responsecode = 0",nativeQuery = true)
    public String GetDotTodaysChargedCount(Charging charging);


    @Query(value = "INSERT INTO tbl_charging (original_sms_id, origintransactionid, origintimestamp, statuscode, shortcode, keyword, ischarged, partnerid, responsecode, attempt, subscribernumber, adjustmentamountrelative, operatorid, smstext,charging_mechanism,is_postpaid) VALUE",nativeQuery = true)
    public Charging insertRecord();




    @Query(value =  "SELECT count(*) as total FROM tbl_charging where origintimestamp between  :formdate and :todate ",nativeQuery = true)
    Integer findLastRecord(Date formdate, Date todate);
}
