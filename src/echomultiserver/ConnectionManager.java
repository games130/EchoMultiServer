/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomultiserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Wei Cheng
 */
public class ConnectionManager extends Thread{
    private ServerSocket serverSocket;
    private Map<Integer, EchoClientHandler> mapA;
    int port,count;
    
    public ConnectionManager(int port) {
        this.port = port;
        this.mapA = new HashMap<>();
        count = 1;
    }
    
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                EchoClientHandler client = new EchoClientHandler(serverSocket.accept());
                System.out.println("loop in connectionManager "+count);
                client.start();
                mapA.put(count, client);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    
    public void Stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Map getMap(){
        return mapA;
    }
    
}
