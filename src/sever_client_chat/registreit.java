package sever_client_chat;

import java.io.*;
import java.sql.*;

import static sever_client_chat.connectclient.socket;
import static sever_client_chat.server.*;

public class registreit implements Runnable {
    public static volatile int free=0;
    DataOutputStream outd;
    DataInputStream ind;
    String loginin = null;
    String passwordin;
    String login;
    String password;
    int id = 0;
    boolean checkr=true;

    @Override
    public void run() {
        try {
            InputStream inIS = (LLS.get(num)).getInputStream();
            OutputStream outIS = (LLS.get(num)).getOutputStream();
            ind = new DataInputStream(inIS);
            outd = new DataOutputStream(outIS);
            LLin.add(ind);
            LLout.add(outd);
            String line;
            help();
            while (checkr){
                line=ind.readUTF();
                if(line.equals("help") || line.equals("?")){
                    help();
                }
                if(line.equals("l")){
                    login();
                }
                if(line.equals("r")){
                    regist();
                }
                if(line.equals("e")){
                    exit();
                }
            }
        }catch (IOException e){
            System.out.println("registreitt error");
        }
    }

    private void exit() throws IOException {
        outd.writeUTF("You disconnect from server");
        socket.close();
        checkr=false;
    }

    private void regist() {
        boolean check=true;
        boolean checkl=true;
        boolean checkp=true;
        ResultSet resset;
        Statement s;
        try {
            System.out.println("You enter to sign up menu");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chat", "root", "root");
            s = c.createStatement();
            outd.writeUTF("You enter to registreited menu");
            while (check) {
                outd.writeUTF("write login");
                while(checkl) {
                    loginin=ind.readUTF();
                    resset = s.executeQuery("SELECT * FROM login");
                    while (resset.next()) {
                        id = resset.getInt("id");
                        login = resset.getString("login");
                        if(login.equals(loginin)){
                            free=free+1;
                        }
                    }
                    if(free==0) {
                        id=id+1;
                        checkl=false;
                    }
                }
                if(checkp){
                    outd.writeUTF("write password");
                    passwordin=ind.readUTF();
                    s.execute("insert into login(id,login,password) values ('"+id+"','"+loginin+"','"+passwordin+"')");
                    outd.writeUTF("You registreited in chat your login " + loginin + " your password " + passwordin);
                    outd.writeUTF("Do not forget them");
                    outd.writeUTF("You login to chat "+loginin);
                    runchatinout();
                    check=false;
                    checkr=false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Not connect to sql");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {

        boolean check=true;
        boolean checkp=false;
        boolean checkpp=false;
        ResultSet resset;
        Statement s;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chat", "root", "root");
            s = c.createStatement();

            outd.writeUTF("You enter to login menu");
            outd.writeUTF("Write login");
            while (check) {
                loginin = ind.readUTF();
                resset = s.executeQuery("SELECT * FROM login");
                while (resset.next()) {
                    login = resset.getString("login");
                    if (login.equals(loginin)) {
                        checkp = true;
                    }
                }
                if(!checkp){
                    outd.writeUTF("This login not sign up");
                }else{
                    outd.writeUTF("Write password");
                    check=false;
                }
            }
            while (checkp) {
                passwordin = ind.readUTF();
                resset = s.executeQuery("SELECT * FROM login");
                while (resset.next()) {
                    password = resset.getString("password");
                    if (password.equals(passwordin)) {
                        outd.writeUTF("You enter to chat");
                        checkpp=false;
                        runchatinout();
                        checkp=false;
                    }
                }
                if(checkpp){
                    outd.writeUTF("You write wrong password");
                    outd.writeUTF("Write password again");
                    checkp=true;
                    checkr=false;
                }
            }
        }catch (IOException e){
            System.out.println("Login error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void runchatinout() {
        Thread intextclientt=new Thread((Runnable) new intextclient());
        Thread outtextclientt=new Thread((Runnable) new outtextclient());
        //Thread infileclientt=new Thread((Runnable) new infileclient());
        //Thread outfileclientt=new Thread((Runnable) new outfileclient());
        intextclientt.setName("intext");
        outtextclientt.setName("outtext");
        LLt.add(intextclientt);
        //LLt.add(infileclientt);
        LLt.add(outtextclientt);
        //LLt.add(outfileclientt);
        //infileclientt.start();
        intextclientt.start();
        outtextclientt.start();
       // outfileclientt.start();
        System.out.println("Client work begin");
        int i=0;
        while (true){
            i++;
        }
    }

    private void help() {
        try {
            outd.writeUTF("If you want login write - 'l'");
            outd.writeUTF("If you want registration write - 'r'");
            outd.writeUTF("If you want exit write - 'e'");
            outd.writeUTF("If you want help write - 'help' or '?'");
        }catch (IOException e){
            System.out.println("help error");
        }
    }
}
