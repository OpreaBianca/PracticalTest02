package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            /* citesti din socket */
            BufferedReader bufferedReader = Utilities.getReader(socket);

            /* scrii pe socket */
            PrintWriter printWriter = Utilities.getWriter(socket);

            /* citesti pana la \n */
            String ip = bufferedReader.readLine();
            String hourMinute = bufferedReader.readLine();
            String commandName = bufferedReader.readLine();

            if (commandName.equals("set alarm")) {
                serverThread.clientAlarms.put(ip, hourMinute);
                /* scrii pe socket */
                printWriter.println(serverThread.clientAlarms);
            }

            if (commandName.equals("reset alarm")) {
                serverThread.clientAlarms.remove(ip);
                /* scrii pe socket */
                printWriter.println(serverThread.clientAlarms);
            }

            if (commandName.equals("poll")) {
                String currentClient = socket.getInetAddress().toString();

                if (serverThread.clientAlarms.get(currentClient) == null) {
                    printWriter.println("none");
                } else {

                    if (ip.toLowerCase().contains(hourMinute.toLowerCase())) {
                        printWriter.println("active");
                    } else {
                        printWriter.println("inactive");
                    }
                }

            }


            /* trimiti */
            printWriter.flush();

        } catch (IOException ioException) {

        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}
