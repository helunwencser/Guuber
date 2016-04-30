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
import java.util.HashMap;
import java.util.Map;

import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.resultCode.ResultCode;
import guuber.cmu.edu.ws.remote.ServerConfig;

/**
 * This class handles all issues related to connect with server
 * */

public class GuuberService extends Service {

    private Socket socket = null;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    /**
     * Store all ResultReceivers for all activities
     * */
    private Map<String, ResultReceiver> resultReceiverMap = new HashMap<String, ResultReceiver>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * receive messages from socket and dispatch message to activities
     * */
    private void receiveAndDispatchMessage() {
        String response = null;
        try {
            while((response = bufferedReader.readLine()) != null) {
                System.out.println("received message from server: " + response);
                String messageKind = response.substring(0, response.indexOf(":"));
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                switch (messageKind) {
                    case ClientMessageKind.SIGNUPDENIED:
                    case ClientMessageKind.SIGNUPOK:
                        if(resultReceiverMap.keySet().contains(ActivityNames.COMMONSIGNUPACTIVITY)) {
                            resultReceiverMap.get(ActivityNames.COMMONSIGNUPACTIVITY).send(
                                    ResultCode.RESULTCODE,
                                    bundle
                            );
                        }
                        break;
                    case ClientMessageKind.SIGNINDENIED:
                    case ClientMessageKind.SIGNINOK:
                        if(resultReceiverMap.keySet().contains(ActivityNames.COMMONSIGNINACTIVITY)) {
                            resultReceiverMap.get(ActivityNames.COMMONSIGNINACTIVITY).send(
                                    ResultCode.RESULTCODE,
                                    bundle
                            );
                        }
                        break;
                    case ClientMessageKind.DRIVERLOC:
                        if(resultReceiverMap.keySet().contains(ActivityNames.PASSENGERSTARTSERVICEACTIVITY)) {
                            resultReceiverMap.get(ActivityNames.PASSENGERSTARTSERVICEACTIVITY).send(
                                    ResultCode.RESULTCODE,
                                    bundle
                            );
                        }
                    case ClientMessageKind.PASSENGERLOC:
                        if(resultReceiverMap.keySet().contains(ActivityNames.DRIVERSTARTSERVICEACTIVITY)) {
                            resultReceiverMap.get(ActivityNames.DRIVERSTARTSERVICEACTIVITY).send(
                                    ResultCode.RESULTCODE,
                                    bundle
                            );
                        }
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                            receiveAndDispatchMessage();
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
        if(operation.equals(Operation.SENDMESSAGE)) {
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
            String activityName = intent.getStringExtra("activityName");
            resultReceiverMap.put(activityName, resultReceiver);
            try {
                bufferedWriter.write(message + "\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
