package btb.blindtest;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by vince_000 on 23/01/2017.
 * NE PAS OUBLIER DE CLOSE LA COMMUNICATION APRES UTILISATION
 */

public class Communication {
    Socket sock = null;
    BufferedReader in = null;
    PrintWriter out = null;
    Context c = null;

    public Communication(String host,int port, Context c) {
        super();
        this.c = c;
        try {
            sock = new Socket(host,port);
        } catch (IOException e) {
            new Toast(c).makeText(c, "Connection failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if(!sock.isConnected()){
            new Toast(c).makeText(c, "Connection failed", Toast.LENGTH_LONG).show();
        }
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            new Toast(c).makeText(c, "Disconnection failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if((!sock.isClosed()) || (!sock.isInputShutdown()) || (!sock.isOutputShutdown())){
            new Toast(c).makeText(c, "Disconnection failed", Toast.LENGTH_LONG).show();
        }
    }

    public String[] read(){
        String temp, total;
        String[] res;
        temp = new String();
        total = new String("");
        try {
            while((temp = in.readLine()) != null){
                total.concat(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        res = total.split("|");
        return res;
    }

    public void write(String s){
        out.write(s);
    }

}
