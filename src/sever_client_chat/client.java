package sever_client_chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class client {
    public static Thread clientoutt=new Thread(new clientout());
    public static Thread clientint=new Thread(new clientin());
    public static int serverPort=6666;
    public static volatile DataInputStream in;
    public static volatile DataOutputStream out;
    public static volatile ArrayList<Thread> arrt=new ArrayList<Thread>();

    public static void main(String[] args) throws IOException {
        System.out.println("Connect to server...");
        try {
            String address = "127.0.0.1";

            System.out.println("You connect to server");
            System.out.println("If you wont exit write 'exit' ");
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
            arrt.add(clientint);
            arrt.add(clientoutt);
            clientoutt.start();
            clientint.start();


        }catch (IOException e) {
            System.out.println("Connect to server error");
        }
    }

    public static class clientout implements  Runnable{
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while (true) {
                    line = reader.readLine();
                    if(line.equals("exit")){
                        for(int i=0;i<arrt.size();i++){
                            arrt.get(i).stop();
                        }
                        System.out.println("You exit");
                    }
                    out.writeUTF(line);
                    //out.flush();
                }
            } catch (Exception x) {
                System.out.println("Client not maik connect to server");
            }
        }
    }

    public static class clientin implements Runnable {
        @Override
        public void run() {
            try{
                String line;
                while (true){
                    line = in.readUTF();
                    System.out.println(line);
                }
            }catch (IOException e){
                System.out.println("ClientIn error");
            }
        }
    }
}

