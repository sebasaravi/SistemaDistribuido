/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author Sebastian
 */
public class ServerHandler extends Thread{
    private final Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    public ServerHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            inputStream = new DataInputStream(new BufferedInputStream(
            this.clientSocket.getInputStream()));
            outputStream = new DataOutputStream(new BufferedOutputStream(
            this.clientSocket.getOutputStream()));
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    @Override
    public void run(){
        String received;
        boolean running = true;
        while(running){
            try{
                received = inputStream.readUTF();
                if(received.equals("1")){
                    String path = "C:\\Users\\Sebastian\\Documents\\NetBeansProjects\\"+clientSocket.getInetAddress().toString()+".txt";
                    FileOutputStream fileText = new FileOutputStream(path);
                    byte[]buffer = new byte[1024];
                    int bytesRead;
                    while((bytesRead = inputStream.read(buffer)) != -1){
                        try{
                            fileText.write(buffer, 0, bytesRead);
                        }catch(IOException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    fileText.close();
                    System.out.println("Archivo recibido");
                }else if(received.equals("2")){
                    
                }
                
            }catch(IOException ex){
                System.out.println("Hola"+ex.getMessage());
            }
        }
    }
    
    public void defineFragments() throws FileNotFoundException, IOException{
        System.out.println("Hola");
        File file = new File("C:/Users/Sebastian/Documents/NetBeansProjects/"+clientSocket.getInetAddress().toString()+".txt");
        long maxSize = file.length();
        long fragment = maxSize / 3;
        byte[]buffer = new byte[(int)fragment];
        
        try(FileInputStream fis = new FileInputStream(file)){
            for(int i=0; i < 3; i++){
                String name = "Fragment_"+i+1+".txt";
                try(FileOutputStream fos = new FileOutputStream(name)){
                    int bytesRead = fis.read(buffer);
                    fos.write(buffer, 0, bytesRead);
                }catch(IOException e){
                    
                }
            }
        }catch(IOException e){
        
        }
    }
    
    public void send(Socket client, byte[] file) throws IOException{ 
        int bytes;
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        while((bytes = inputStream.read(file)) != -1){
            try{
                out.write(file, 0, bytes);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        out.flush();
    }
    
}
