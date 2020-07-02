/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomultiserver;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Wei Cheng
 */
public class EchoMultiServer {
    
    //private static final Logger LOG = LoggerFactory.getLogger(EchoMultiServer.class);
      

    public static void main(String[] args) {
        int port = 5555;
        ConnectionManager server = new ConnectionManager(port);
        server.start();
        SystemInput systemInput_1 = new SystemInput(server); 
        systemInput_1.start();
        
        Map<Integer, EchoClientHandler> Map;
        
        while (true){
            Map = server.getMap();
            //System.out.println("total map: "+Map.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(EchoMultiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
