package com.strawberry.delivery.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.strawberry.delivery.R;
import com.strawberry.delivery.data.orderform;

import java.util.List;

import com.strawberry.delivery.data.foodCounter;

public class publishOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_order);
        TextView tvTest=(TextView)findViewById(R.id.tvTest);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        try{
            orderform orderForm=(orderform)bundle.getSerializable("orderForm");
            List<foodCounter> foods=orderForm.getFoods();
            String sFoods=new String();
            for(foodCounter food:foods){
                sFoods="Name:"+food.getName()+"\nCount:"+String.valueOf(food.getCount())+"\n"+sFoods;
            }
            tvTest.setText("ID:"+orderForm.getID()+"\nfoods:\n"+sFoods+"Total:"+String.valueOf(orderForm.getTotal())+"\nAddress:"+orderForm.getAddress());

        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }
}
