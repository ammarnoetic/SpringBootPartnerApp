package com.noetic.gwpartner.timwe.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketException;

public class ZongHeartBeat implements Runnable {

    private final Logger log =  LoggerFactory.getLogger(ZongMMLRequest.class);
    private String msg;

    @Autowired
    private ZongMMLRequest zongMMLRequest;

    @Override
    public void run() {
        try {
            String hearbeat = "`SC`0004HBHBB7BDB7BD";
            msg = zongMMLRequest.connect(hearbeat, "Y");
            log.info(msg);
        } catch (SocketException e) {
            log.info("ZONG LOGIN | EXCEPTION CAUGHT HERE" + e.getStackTrace());
            //  obj_ZongClient.Login();
        }catch (Exception e){
            log.info("Excption "+e.getMessage());
        }

    }
}
