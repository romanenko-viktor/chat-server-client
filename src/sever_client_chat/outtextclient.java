package sever_client_chat;

import java.io.IOException;

import static sever_client_chat.server.LLout;
import static sever_client_chat.server.line;
import static sever_client_chat.server.checkin;

public class outtextclient implements Runnable{
    @Override
    public void run() {
        try {
            while (true) {
                if (line!=null) {
                    if(checkin) {
                        for (int i = 0; i < LLout.size(); i++) {
                            LLout.get(i).writeUTF(line);
                            //LLout.get(i).flush();
                        }
                        checkin=false;
                    }
                }
            }
        }catch (IOException e){
            System.out.println("outtextclient error");
        }
    }
}
