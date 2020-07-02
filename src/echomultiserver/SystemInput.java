/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomultiserver;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wei Cheng
 */
public class SystemInput extends Thread{
    private DataInputStream  input   = null; 
    private ConnectionManager server;
    private PrintWriter out;
    private boolean runForever = true;
    private long heartbeatDelayMilis = 10000;
    private int port;
    

    public SystemInput(ConnectionManager server) {
        this.server = server;
    }

    public void run() {
        System.out.println("started input thread");
         Map<Integer, EchoClientHandler> Map;
        
        // takes input from terminal 
        input  = new DataInputStream(System.in); 
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over") && runForever) 
        { 

            String reply;
            try {
                reply = input.readLine();
                if (reply.equals("")) continue;
                if (reply == null) continue;
                switch(reply){
                    case "1":
                        Map = server.getMap();
                        
                        for (Map.Entry<Integer, EchoClientHandler> entry : Map.entrySet()) {
                            System.out.println("SystemInput data: "+ entry.getKey() + "/" + entry.getValue());
                            Map.get(entry.getKey()).getDataOutToClient().println("id");
                        }
                        break;
                    case "2":
                        Map = server.getMap();
                        
                        for (Map.Entry<Integer, EchoClientHandler> entry : Map.entrySet()) {
                            Map.get(entry.getKey()).getDataOutToClient().println("rssh");
                        }
                        break;
                    case "3":
                        System.out.println("SystemInput 2");
                        break;
                    default:
                        //System.out.println("SystemInput default server reply: "+reply);
                        Map = server.getMap();
                        
                        for (Map.Entry<Integer, EchoClientHandler> entry : Map.entrySet()) {
                            Map.get(entry.getKey()).getDataOutToClient().println(reply);
                        }
                }
            } catch (IOException ex) {
                Logger.getLogger(SystemInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void remove(){
        runForever = false;
    }
    
}
