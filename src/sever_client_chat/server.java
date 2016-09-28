package sever_client_chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class server {
    public static Thread connectclientt=new Thread((Runnable) new connectclient());
    public static LinkedList<Thread>LLt=new LinkedList<Thread>();
    public static volatile LinkedList<DataInputStream> LLin=new LinkedList<DataInputStream>();
    public static volatile LinkedList<DataOutputStream> LLout=new LinkedList<DataOutputStream>();
    public static volatile LinkedList<Socket> LLS=new LinkedList<Socket>();
    public static ServerSocket ss;
    public static int port=6666;
    public static volatile int num=0;
    public static volatile String line;
    public static volatile boolean checkin=false;

    public static void main(String[] args){
        try {
            System.out.println("Server start work");

            ss = new ServerSocket(port);

            connectclientt.setName("Wait client thread");
            LLt.add(connectclientt);
            connectclientt.setPriority(6);
            connectclientt.start();

            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("If you want off server write - 'exit'");

            while(true) {
                String line =reader.readLine();
                if(line.equals("exit")){
                    for(int i=0;i<LLt.size();i++){
                        LLt.get(i).stop();
                        System.out.println("STOP - "+LLt.get(i).getName());
                    }
                    System.out.println("Server STOP");
                }
            }
        }catch (IOException e){
            System.out.println("Server not run");
        }
    }

    /*public static class ServerSIn implements Runnable {
        volatile boolean WaitSignupOrLogin=true;
        @Override
        public void run() {
            try{
                System.out.println("ServerSIn run");
                int w=in-1;
                while (true){
                    if(WaitSignupOrLogin==true) {
                        LLout.get(LLout.size()-1).writeUTF("If you login write 'l' if you want ristreit write 'r' if you want exit write 'e'");
                        Thread RegistrT=new Thread(new Registr());
                        RegistrT.start();
                    }else{
                        line=LLin.get(w).readUTF();
                        ChekIn=true;
                    }
                }
            }catch (IOException e){
                System.out.println("Client disconnect");
            }
        }
    }

    public static class ServerSOut implements Runnable {
        @Override
        public void run() {
            try{
                System.out.println("ServerSOut run");
                int a=0;
                while (true){
                    if(ChekIn) {
                        for(int i=0; i<LLout.size();i++){
                            LLout.get(i).writeUTF(line);
                            System.out.println(line);
                            LLout.get(i).flush();
                            a=a+1;
                            if(a==LLout.size()){
                                ChekIn=false;
                                a=0;
                            }
                        }
                    }
                }
            }catch (IOException e){
                System.out.println("ServerSOut error");
            }
        }
    }*/
}