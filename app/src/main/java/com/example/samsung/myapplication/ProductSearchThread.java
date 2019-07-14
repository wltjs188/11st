package com.example.samsung.myapplication;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by SAMSUNG on 2019-03-19.
 */

public class ProductSearchThread extends Thread {
    private Handler handler;
    private ProductSearchService service;

    public ProductSearchThread(ProductSearchService service, Handler handler){
        this.service = service;
        this.handler = handler;
    }

    public void run() {
        // TODO Auto-generated method stub
        super.run();
        // service의 search메소드를 수행하고 결과를 핸들러를 통해 메인에게 전달

        List<Option> optionList=service.search_detail();

        Message msg = handler.obtainMessage();
        msg.obj = optionList;
        handler.sendMessage(msg);
    }

}