package com.andrew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class CDClient {

    String hostName = "127.0.0.1";
    int portNum = 8888;

    public CDClient() {
        System.out.println("CDClient constructor called");
    }

    public void connectToServer() {
        try (Socket clientSocket = new Socket(hostName,portNum);
             PrintWriter socketSender = new PrintWriter(clientSocket.getOutputStream(),true); // true for autoflush (what is that)
             BufferedReader socketReceiver = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader systemReceiver = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = systemReceiver.readLine()) != null) {
                socketSender.println(userInput);
                System.out.println("Sending: " + userInput);
                System.out.println("Receiving: " + socketReceiver.readLine());
            }

        } catch(UnknownHostException uhe) {
            System.out.println("Don't know about host " + hostName);
            System.out.println(uhe);
        } catch(IOException ioe) {
            System.out.println("Error with I/O");
            System.out.println(ioe);
        }
    }

}
