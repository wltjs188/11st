package com.example.samsung.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ProductSearchService service;
    ArrayList<Option> optionList;
    String ProductCode="";
    String OptionTitle="";
    String OptionOrder="";
    String OptionValue="";
    String OptionPrice="";
    String OptionPoint="";
    String OptionInstallment="";
    String OptionShipFee="";
    DiscountInfo discountInfo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        optionList = new ArrayList<Option>();
        service = new ProductSearchService();
        ProductSearchThread thread = new ProductSearchThread(service,handler);
        thread.start();
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            optionList= (ArrayList<Option>) msg.obj;
            for(int i=0;i<optionList.size();i++) {
                ProductCode=optionList.get(i).getProductCode();
                OptionTitle=optionList.get(i).getOptionTitle();
                OptionOrder=optionList.get(i).getOptionOrder();
                OptionValue=optionList.get(i).getOptionValue();
                OptionPrice=optionList.get(i).getOptionPrice();
                discountInfo=optionList.get(i).getDiscountInfo();
                OptionPoint=discountInfo.getPoint(); //포인트
                OptionInstallment=discountInfo.getInstallment(); //무이자
                OptionShipFee=discountInfo.getShipFee(); //배송비

                Log.i("상품"+i,"상품코드:"+ProductCode+",옵션타이틀:"+OptionTitle+",옵션번호:"+OptionOrder+",옵션이름:"+OptionValue+",가격:"+OptionPrice+",포인트적립:"+OptionPoint+",무이자:"+OptionInstallment+",배송비:"+OptionShipFee);
            }
        }
    };
}