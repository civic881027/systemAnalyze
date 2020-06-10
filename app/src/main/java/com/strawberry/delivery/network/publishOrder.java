package com.strawberry.delivery.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.strawberry.delivery.R;
import com.strawberry.delivery.data.orderform;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.strawberry.delivery.data.foodCounter;

public class publishOrder extends Activity {

    private static String orderMessage;
    private static Socket clientSocket;
    private Thread thread;
    private Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_order);

        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            orderform orderForm = (orderform) bundle.getSerializable("orderForm");
            List<foodCounter> foods = orderForm.getFoods();
            String sFoods = new String();
            for (foodCounter food : foods) {
                sFoods = "Name:" + food.getName() + "\nCount:" + String.valueOf(food.getCount()) + "\n" + sFoods;
            }
            orderMessage = "ID:" + orderForm.getID() + "\nfoods:\n" + sFoods + "Total:" + String.valueOf(orderForm.getTotal()) + "\nAddress:" + orderForm.getAddress()+"\n\n";
            tvTest.setText(orderMessage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        thread=new Thread(socket_server);
        thread.start();
    }

    private Runnable socket_server=new Runnable() {
        @Override
        public void run() {
            try{
                InetAddress serverIP=InetAddress.getByName("10.0.2.2");
                clientSocket=new Socket(serverIP,5050);
                BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "BIG5"));
                while(clientSocket.isConnected()){
                    bw.write(orderMessage);
                    bw.flush();
                    break;
                }
                bw.close();
                clientSocket.close();
            }catch (IOException e){

            }
        }
    };

}

