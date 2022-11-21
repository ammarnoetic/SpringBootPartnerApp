package com.noetic.gwpartner.timwe.Model;

import com.noetic.gwpartner.timwe.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private MoReceiver moReceiver;

    @PostMapping("/show")
    public String getProductAsJson(@RequestBody Sms sms) {
        System.out.println(sms);
        System.out.println(sms.getSmsId());
        System.out.println(sms.getMsisdn());
        System.out.println(sms.getSmsData());
        System.out.println(sms.getConnectionPointId());
        System.out.println(sms.getShortcode());
        System.out.println("hello");

      //  new MoReceiver(Constants.FileConstants.MO_RECIEVE_PORT.getInt(), Constants.FileConstants.MO_RECIEVE_CONTEXT.getString());

        moReceiver.processSMSRequest(sms);
        return "sucess";
    }


}
