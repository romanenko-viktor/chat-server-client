package sever_client_chat;

import java.io.IOException;

import static sever_client_chat.server.*;

public class intextclient implements Runnable{
    @Override
    public void run() {
        try {
            int i = num;
            num=num+1;
            while (true) {
                line = LLin.get(i).readUTF();
                checkin=true;
                System.out.println(i);
                System.out.println(line);
            }
        }catch (IOException e){
            System.out.println("intextclient error");
        }
    }
}
