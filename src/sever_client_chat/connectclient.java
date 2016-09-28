package sever_client_chat;

import java.io.*;
import java.net.Socket;

import static sever_client_chat.server.*;

public class connectclient implements Runnable{
    public static volatile Socket socket=new Socket();
    @Override
    public void run() {
        try{
            System.out.println("Client connect run");
            while (true){
                Socket socket=ss.accept();
                this.socket=socket;
                LLS.add(socket);
                System.out.println("Client sign up or login");
                if(socket.isConnected()) {
                    Thread registreitt=new Thread(new registreit());
                    registreitt.setName("regis");
                    LLt.add(registreitt);
                    registreitt.start();
                    System.out.println("Client connect from ip: " + socket.getInetAddress());
                }
            }
        }catch (IOException e){
            System.out.println("StartServer error");
        }
    }

    /*public static void OutWriteSocket() {
        try {
            InputStream inIS = (LLS.get(num)).getInputStream();
            OutputStream outIS=(LLS.get(num)).getOutputStream();
            DataInputStream ind=new DataInputStream(inIS);
            DataOutputStream outd=new DataOutputStream(outIS);
            LLin.add(ind);
            LLout.add(outd);
            num=num+1;
            System.out.println("Number of client "+in);
            in=in+1;
            Thread ServerSInT=new Thread(new ServerSIn());
            ServerSInT.setName("Client in masseg #"+in+" thread");
            ServerSInT.start();
            LLt.add(ServerSInT);
            if(!ServerSOutT.isAlive()){
                ServerSOutT.setName("Client out masseg");
                LLt.add(ServerSOutT);
                ServerSOutT.start();
            }
        }catch (IOException e){
            System.out.println("OutWriteSocket error");
        }
    }*/
}
