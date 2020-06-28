/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomultiserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wei Cheng
 */
public class EchoClientHandler extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(EchoMultiServer.class);
    private static final String MSG_HEARTBEAT = "HBeat77";
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public EchoClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("EchoClientHandler init out parameter error");
            java.util.logging.Logger.getLogger(EchoClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            System.out.println("EchoClientHandler init in parameter error");
            java.util.logging.Logger.getLogger(EchoClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        System.out.println("started");
        try {
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("input: "+inputLine);
                if (inputLine.equals("")) continue;
                if (inputLine == null) continue;
                if (MSG_HEARTBEAT.equals(inputLine)) continue;
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }

                System.out.println("going to send: "+inputLine);
                out.println(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            LOG.debug(e.getMessage());
        }
    }
    
    public Socket getSocket() {
        return clientSocket;
    }
    
    
    public PrintWriter getDataOutToClient(){
        return out;
    }
    
    public BufferedReader getDataInFromClient(){
        return in;
    }
}
