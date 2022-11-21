package com.noetic.gwpartner.timwe;

import com.noetic.gwpartner.timwe.Model.MoReceiver;
import com.noetic.gwpartner.timwe.Model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MoReceiverApplication {

//	@Autowired
//	private MoReceiver moReceiver;

	public static void main(String[] args) {
		SpringApplication.run(MoReceiverApplication.class, args);
	}

//	@PostConstruct
//	public void call(){
//		moReceiver.getMcg(new Sms());
//	}

}
