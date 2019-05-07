package com.example.samsung.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText keywordEdt;
    private Button searchBtn;
    private Button moreBtn;
    private List<Product> productList;
    private ListView listView;
    private ProductAdapter adapter;
    ProductSearchService service;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keywordEdt = (EditText)findViewById(R.id.main_keyword_edt);
        searchBtn = (Button) findViewById(R.id.main_search_btn);
        moreBtn = (Button) findViewById(R.id.main_more_btn);
        productList = new ArrayList<Product>();
        adapter = new ProductAdapter(this, R.layout.list_product_item, productList);
        listView = (ListView) findViewById(R.id.main_listView);
        listView.setAdapter(adapter);
//        Intent intent=getIntent();
//        keyword=intent.getStringExtra("keyword");
//        service = new ProductSearchService(keyword);
//        ProductSearchThread thread = new ProductSearchThread(service, handler);
//        Toast.makeText(MainActivity.this, "검색을 시작합니다.", Toast.LENGTH_LONG).show();
//        thread.start();
// 상품검색
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                keyword = keywordEdt.getText().toString();
                service = new ProductSearchService(keyword);
                ProductSearchThread thread = new ProductSearchThread(service, handler);
                Toast.makeText(MainActivity.this, "검색을 시작합니다.", 0).show();
                thread.start();
            }
        });
        //더보기
        moreBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                service.nextPage(keyword);
                ProductSearchThread thread = new ProductSearchThread(service, handler);
                Toast.makeText(MainActivity.this, "더보기", 0).show();
                thread.start();
            }
        });

    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what ==1 )
            {
                //arg1이 10이면 처음 검색에 대한 결과를 갖다 준걸로
                if(msg.arg1==10)
                {
                    productList.clear();
                    productList.addAll((List<Product>) msg.obj);
                    adapter.notifyDataSetChanged();
                }
//                arg2이 20이면 상품추가하기
                else if(msg.arg2==20){
                    String result = "";
                    List<Product> data = (List<Product>)msg.obj;
                    for(Product p : data)
                        result += p.getProductName() +"\n";
                    productList.addAll((List<Product>) msg.obj);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };
}
