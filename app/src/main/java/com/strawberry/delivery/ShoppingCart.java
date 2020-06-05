package com.strawberry.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.strawberry.delivery.data.DBhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends AppCompatActivity {
    private SQLiteDatabase mDb;
    private Map<String, Integer> restID=new HashMap<>();
    private String restName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        createMap();
        ImageView ivRestImgae=(ImageView)findViewById(R.id.ivRestaurant);
        TextView tvRestName=(TextView)findViewById(R.id.tvSCrestname);
        TextView tvRestAddress=(TextView)findViewById(R.id.tvSCrestAddress);
        RecyclerView rv=(RecyclerView)findViewById(R.id.rvSCrestfood);
        DBhelper dBhelper=new DBhelper(this);
        mDb=dBhelper.getReadableDatabase();

        Bundle bundle=getIntent().getExtras();
        ArrayList<foodCounter> foodCounters=(ArrayList<foodCounter>) bundle.getSerializable("countFood");
        restName=bundle.getString("restaurant");

        Cursor cursor1=getRest();
        cursor1.moveToLast();
        String name=cursor1.getString(cursor1.getColumnIndex("restName"));
        String address=cursor1.getString(cursor1.getColumnIndex(("restAddress")));
        String url=cursor1.getString(cursor1.getColumnIndex("restUrl"));

        tvRestName.setText(name);
        tvRestAddress.setText(address);
        Glide.with(this).load(url).into(ivRestImgae);


        orderAdapter adapter=new orderAdapter(this,foodCounters);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);



    }

    private Cursor getFoodData(){
        int ID=restID.get(restName);
        return mDb.rawQuery(
                "SELECT _ID,foodName,foodPrice,foodUrl FROM RestFood WHERE restaurant="+ID,null);

    }


    private Cursor getRest(){
        return mDb.rawQuery("SELECT _ID,restName,restUrl,restAddress from restaurant where restName='"+restName+"'",null);
    }
    private void createMap(){
        restID.put("麥當勞-台北新生店",1);
        restID.put("可不可熟成紅茶-台北公館店",2);
        restID.put("再睡5分鐘",3);
    }
}
