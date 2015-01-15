package bernie.tcpclient;

import android.os.AsyncTask;
import android.view.View;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client{
    Socket echoSocket;
    PrintWriter out;
    BufferedReader in;
    int asyncRetInt =-1;
    String asyncRetString = "";


    class InitTask extends AsyncTask<String[],Void,Socket>{
        protected Socket doInBackground(String[]... args){
            try {
                echoSocket = new Socket(args[0][0], Integer.parseInt(args[0][1]));
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                asyncRetInt = 0;
            }
            catch(UnknownHostException ce){
                asyncRetInt = 1;
            }
            catch(ConnectException ce){
                asyncRetInt = 1;
            }
            catch(IOException ioe){
                asyncRetInt = 2;
            }
            return echoSocket;
        }
    }

    //TODO crashes when not connected
    class SendTask extends AsyncTask<String,Void,Void>{
        protected Void doInBackground(String... args){
            try {
                out.println(args[0]);
            }
            catch(NullPointerException npe){
            }
            try {
                asyncRetString = in.readLine();
            }
            catch(IOException ioe){
                asyncRetString = "Null";
            }
            return null;
        }
    }

    class SafeSendTask extends AsyncTask<String[],Void,Void>{
        protected Void doInBackground(String[]... args) {
            try {
                echoSocket = new Socket();
                echoSocket.connect(new InetSocketAddress(args[0][0], Integer.parseInt(args[0][1])),3000);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                asyncRetInt = 0;
                out.println(args[0][2]);
            } catch (UnknownHostException ce) {
                asyncRetInt = 1;
            } catch (ConnectException ce) {
                asyncRetInt = 1;
            } catch (IOException ioe) {
                asyncRetInt = 2;
            }
            try {
                in.close();
            } catch (Exception e) {

            }
            try {
                out.close();
            } catch (Exception e) {

            }
            try {
                echoSocket.close();
            } catch (Exception e) {

            }
            return null;
        }
    }

    public int init(String hostName, int portNumber){
        String[] address = {hostName,""+portNumber};
        new InitTask().execute(address);
        return asyncRetInt;
    }

    public void close(){
           out.close();
       try{
           in.close();
       }
       catch(IOException ioe){
       //didnt manage to close
       }
        try{
            echoSocket.close();
        }
        catch(IOException ioe){
            //didnt manage to close
        }
    }


    public String send(String msg){
        new SendTask().execute(msg);
        return asyncRetString;
    }

    public void safeSend(String hostName, int portNumber, String msg){
        String[] inpArray = {hostName,""+portNumber,msg};
        new SafeSendTask().execute(inpArray);
    }
}
