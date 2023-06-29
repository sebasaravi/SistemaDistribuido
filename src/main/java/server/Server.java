/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import utilities.ServerHandler;

/**
 *
 * @author Sebastian
 */
public class Server extends Thread{
    public List<Socket> clientSockets;
    private ServerSocket server = null;
    
    
    public Server(){
        try{
            server = new ServerSocket(8000);
            clientSockets = new ArrayList<>();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void run(){
        int count = 0;
        while(true){
            try{
                Socket client = server.accept();
                if(count < 3){
                    clientSockets.add(client);
                    count++;
                }
                System.out.println("Client connected "+client.getInetAddress());
                Thread t = new Thread(new ServerHandler(client));
                t.start();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
