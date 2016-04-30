package guuber.cmu.edu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import guuber.cmu.edu.messageConst.MessageKind;
import guuber.cmu.edu.ws.remote.ServerConfig;

/**
 * This class handles all issues related to connect with server
 * */

public class GuuberService extends Service {

    private Socket socket = null;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InetAddress serverAddr = InetAddress.getByName(ServerConfig.SERVERIP);
                            socket = new Socket(serverAddr, ServerConfig.PORTNUMBER);
                            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            System.out.println("connected to server");
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String operation = intent.getStringExtra("operation");
        if(operation.equals(MessageKind.SENDMESSAGE)) {
            new Thread(
                    new MessageHandler(intent)
            ).start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    /**
     * send a message to server, receive response from server, send it
     * back to activity
     * */
    public class MessageHandler implements Runnable {
        private Intent intent;

        public MessageHandler(Intent intent) {
            this.intent = intent;
        }

        public void run() {
            ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
            String message = intent.getStringExtra("message");
            int resultCode = intent.getIntExtra("resultCode", 0);
            System.out.println("message to be sent: " + message);
            System.out.println("result code: " + resultCode);
            try {
                bufferedWriter.write(message + "\n");
                bufferedWriter.flush();
                String response = bufferedReader.readLine();
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                resultReceiver.send(resultCode, bundle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
