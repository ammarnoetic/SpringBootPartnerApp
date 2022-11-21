package com.noetic.gwpartner.timwe.Model;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    @Autowired
    private Socket clientSocket;

    public void Connect(String ServerIP, int ServerPort) {
        //create client socket, connect to server
        try {
            clientSocket = new Socket(ServerIP,ServerPort);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Get Stream
    protected OutputStream GetStream() {
        OutputStream OutputStream=null;
        try {
            OutputStream =  clientSocket.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return OutputStream;
    }

    //Input Stream
    protected InputStream Read() {
        InputStream MyInputStream=null;
        try {
            MyInputStream =  clientSocket.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return MyInputStream;
    }

}
