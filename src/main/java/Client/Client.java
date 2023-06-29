/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author Sebastian
 */
public class Client extends JFrame{
    private JButton btnReceivedFile;
    private JButton btnSendFile;
    private JLabel lblSelectedFile;
    
    static Socket sfd = null;

    public Client() {
        
    }
    public static void main(String[] args) throws IOException{
        sfd = new Socket("192.168.0.6",8000);
        DataInputStream inputStream = new DataInputStream(
                new BufferedInputStream(sfd.getInputStream()));
        DataOutputStream outputStream = new DataOutputStream(
                new BufferedOutputStream(sfd.getOutputStream()));
        
        outputStream.writeUTF("1");
        outputStream.flush();
        JFileChooser fileChooser = new JFileChooser();
        while(true){
            fileChooser.showDialog(fileChooser, 
                JFileChooser.APPROVE_SELECTION);
            File file = fileChooser.getSelectedFile();
            FileInputStream fileOut = new FileInputStream(file);
            byte[]buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileOut.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
        
        
        
        
        
    }
}
