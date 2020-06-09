package com.strawberry.delivery.data;

import com.strawberry.delivery.data.foodCounter;

import java.io.Serializable;
import java.util.List;

public class orderform implements Serializable {
    private String ID;
    private List<foodCounter> foods;
    private int total;
    private String address;
    public orderform(String ID,List<foodCounter> foods,String address){
        this.ID=ID;
        this.foods=foods;
        for(foodCounter f:foods){
            this.total+=f.getTotal();
        }
        this.address=address;
    }

    public String getID() {
        return ID;
    }

    public List<foodCounter> getFoods() {
        return foods;
    }

    public int getTotal() {

        return total;
    }

    public String getAddress() {
        return address;
    }
}
