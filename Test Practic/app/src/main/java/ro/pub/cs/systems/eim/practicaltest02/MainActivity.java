package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private Button set_alarm = null;
    private Button reset_alarm = null;
    private Button start_server = null;
    private Button poll = null;
    private EditText hour = null;
    private EditText minute = null;
    private EditText port = null;
    private EditText ip = null;
    private TextView from_server = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        set_alarm = findViewById(R.id.set_alarm);
        reset_alarm = findViewById(R.id.rest_alarm);
        start_server = findViewById(R.id.start_server_button);
        poll = findViewById(R.id.poll);
        hour = findViewById(R.id.set_hour);
        minute = findViewById(R.id.set_minute);
        port = findViewById(R.id.port_client);
        ip = findViewById(R.id.adresa_ip);
        from_server = findViewById(R.id.text_view);

        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hourStr = hour.getText().toString();
                String minuteStr = minute.getText().toString();
                String text = hourStr + ':' + minuteStr;
                String clientAddress = ip.getText().toString();
                String clientPort = port.getText().toString();
                Button b = (Button)v;
                String commandName = b.getText().toString();

                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), text, commandName, from_server);
                clientThread.start();

            }
        });

        reset_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hourStr = hour.getText().toString();
                String minuteStr = minute.getText().toString();
                String text = hourStr + ':' + minuteStr;
                String clientAddress = ip.getText().toString();
                String clientPort = port.getText().toString();
                Button b = (Button)v;
                String commandName = b.getText().toString();

                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), text, commandName, from_server);
                clientThread.start();

            }
        });

        poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hourStr = hour.getText().toString();
                String minuteStr = minute.getText().toString();
                String text = hourStr + ':' + minuteStr;
                String clientAddress = ip.getText().toString();
                String clientPort = port.getText().toString();
                Button b = (Button)v;
                String commandName = b.getText().toString();

                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), text, commandName, from_server);
                clientThread.start();

            }
        });


        start_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverPortEditText = "6000";

                serverThread = new ServerThread(Integer.parseInt(serverPortEditText));
                serverThread.start();

            }
        });
    }
}
