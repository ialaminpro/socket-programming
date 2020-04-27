package bd.com.serversq.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    EditText etIP, etPort;
    TextView tvMessages;
    EditText etMessage, etSecondNumber, etetOpt;
    Button btnSend;
    String SERVER_IP;
    String Opt, firstNumber, secondNumber;
    int SERVER_PORT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tvMessages = findViewById(R.id.tvMessages);
        etMessage = findViewById(R.id.etfirstNumber);
        etSecondNumber = findViewById(R.id.etsecondNumber);
        etetOpt = findViewById(R.id.etOpt);
        btnSend = findViewById(R.id.btnSend);
        Button btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMessages.setText("");
                SERVER_IP = "10.0.2.2";
//                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());

                Opt = etetOpt.getText().toString().trim();
                firstNumber = etMessage.getText().toString().trim();
                secondNumber = etSecondNumber.getText().toString().trim();

                Thread1 = new Thread(new Thread1());
                Thread1.start();

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opt = etetOpt.getText().toString().trim();
                firstNumber = etMessage.getText().toString().trim();
                secondNumber = etSecondNumber.getText().toString().trim();
                if (!Opt.isEmpty() && !firstNumber.isEmpty() && !secondNumber.isEmpty()) {
                    new Thread(new Thread3(Opt,firstNumber,secondNumber)).start();
                }
            }
        });
    }
    public PrintWriter output;
    public BufferedReader input;
    public InputStream in;
    public InputStream inFromServer;
    Socket socket;
    OutputStream outToServer;
    DataOutputStream out;
    class Thread1 implements Runnable {

        public void run() {

            try {
                System.out.println("Connecting...");
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());

                inFromServer = socket.getInputStream();


                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Connected\n");
                        output.println(Opt);
                        output.println(firstNumber);
                        output.println(secondNumber);

                        System.out.println("client: " + Opt+ firstNumber + secondNumber + "\n");
                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable {
        @Override
        public void run() {
                try {
                    final String message = input.readLine();
                    final String Line1 = input.readLine();
                    final String Line2 = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("Server says: " + message + "\n");
                                tvMessages.append("Server says: " + Line1 + "\n");
                                tvMessages.append("Server says: " + Line2 + "\n");
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    output.close();
                    input.close();
                    socket.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    class Thread3 implements Runnable {
        private String Opt, message, secondNumber;
        Thread3(String Opt, String message, String secondNumber) {
            this.Opt = Opt;
            this.message = message;
            this.secondNumber = secondNumber;
        }
        @Override
        public void run() {
            output.write(Opt);
            output.write(message);
            output.write(secondNumber);
            output.flush();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tvMessages.append("client: " + Opt+ message + secondNumber + "\n");
                    etMessage.setText("");
                    etSecondNumber.setText("");
                    etetOpt.setText("");
                }
            });

        }
    }
}