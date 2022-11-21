//package com.noetic.gwpartner.timwe.Repository;
//
//import com.noetic.gwpartner.timwe.Model.Sms;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface SecurityRepo extends JpaRepository<Sms,Long> {
//
//    @Query(value ="SELECT msisdn, shortcode, sms, connection_point_id, sms_id \n" +
//            "from sms where is_accepted = false and \n" +
//            "(lower(sms) like lower('shmo%') OR   lower(sms) like lower('mcn%') OR  \n" +
//            "lower(sms) like lower('ymn%') OR lower(sms) like lower('game%') OR lower(sms) like lower('svpk%')) \n" +
//            "and (sms_date >=  current_date - interval '7 days')",nativeQuery = true)
//    public Sms findAllSms();
//
//    @Query(value = "update sms set is_accepted = \"+bool+\" where sms_id = \"+smsId",nativeQuery = true)
//    int updateSms(boolean bool, long smsId);
//}
