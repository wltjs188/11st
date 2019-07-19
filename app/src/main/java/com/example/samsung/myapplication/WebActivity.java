package com.example.samsung.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebActivity extends AppCompatActivity {

    //주소버튼(상품id를 페이지주소로 만들어 줌) 누르고 크롤링누르기
    private String htmlPageUrl = ""; //파싱할 홈페이지의 URL주소
    //상품id 넣기
    //하의
    String strPants = "30432998,1017008148,2459456198,1978909970,60937795,41119945,1522475374,1988820436,3059633,2409735325,8840192,186774740,1057474966,2426064755,1709518944,1434474102,2462512601,41119945,748083957,2423633084,2450593925,2471993196,2435669515,2039250220,2459346058,1270463397,786230245,2434469822,2368346692,2421935894,2395835000,2381790838,2420822164,1700597803,2397203559,1124027418,2373908150,2364883905,2390159074,2371231210,2445871452,2407705648,2375520198,2439300227,2078801694,2345426253,2392324302,1967132239,2382916876,1856319504,1899306059,1306404063,1236879639,412482241,1766878207,1306404063,1436826869,2410444031,2100286290,2461911541,1487675157";
    //상의
    //String strPants = "1166358755,2214502602,1679741579,2107859509,1465997983,2336766878,2198911704,2126828599,1733642304,1954136407,110397986,2217389058,1833492575,2059243168,106598793,81447566,142019339,608750373,1453335029,148298786,2177923925,2348017873,1470964507,142578216,286582659,1300337873,2415780474,156095570,175354224,58575631,152445627,286601199,1616598459,2470803274,1343874682,1119964027,1977953143,2177934431,832124076,2124171724,2336766878,2341784903,175354224,248329991,1658511114,1130471176,1130679126,1955684853,2043381666,1242620330";
    //원피스
    //String strPants ="14129013,2421930203, 2214911420, 602904262,  2319996891, 2406950987, 796247461, 820255150, 54150416, 1788295032, 86899719, 1065082805, 1684537568, 1684537568, 1065316907, 1443435844, 1127541141, 85552030, 567202245, 567202245, 2319996891,1173558823, 1698091418, 490333290, 16954640, 14708455, 1878082899, 16983949, 2258948125, 1197219729, 1979883393,1872292454, 2346303523, 2190437266, 54150416, 1779030549, 1255229793, 2418637376, 1857172300, 1164974120, 1516201127, 1444409522, 2038464407, 820255150, 368805752, 1779030549, 2319996891, 1770919141, 1443435844, 2370547699, 1255229793";

    //상품id 배열화
    String[] pants = strPants.split(",");

    //크롤링관련 html - 주소관련 http
    private TextView textviewHtmlDocument; //크롤링 결과 노출
    private String htmlContentInStringFormat="";
    private String httpContentInStringFormat="";
    Button htmlButton,httpButton;
    int cnt=0;

    //url 이미지url, opvalueno 옵션번호
    String url="";
    String opvalueno="";

    //11번가 기본 주소 - 상품id에 붙여서 파싱페이지로 만들것임
    private String testUrl = "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=";
    String[] test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        httpButton = (Button)findViewById(R.id.httpBtn);
        httpButton.setOnClickListener(new View.OnClickListener() { // 주소버튼 누르면 상품id앞에 주소가 붙게 됨. 크롤링 할 페이지 준비
            @Override
            public void onClick(View v) {
                for(int i=0;i<pants.length;i++) {
                    httpContentInStringFormat += testUrl+pants[i]+",";
                }
                pants = httpContentInStringFormat.split(",");
                httpContentInStringFormat="";
                Toast.makeText(getApplicationContext(), "주소 변환 완료", Toast.LENGTH_LONG).show();
            }
        });


        textviewHtmlDocument = (TextView)findViewById(R.id.htmlView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기
        htmlButton = (Button)findViewById(R.id.htmlBtn);
        htmlButton.setOnClickListener(new View.OnClickListener() { //크롤링 버튼 누르면, 페이지 1개씩 크롤링 시작
            @Override
            public void onClick(View v) {
                htmlPageUrl = pants[cnt]; //파싱페이지 설정
                htmlContentInStringFormat="";
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get(); //파싱페이지 넣어주기
                Elements contents = doc.select("li.photo");//옵션마다 접근
                for (Element e : contents) {
                    Elements a = e.getElementsByTag("a");
                    //옵션번호 저장
                    opvalueno = a.attr("data-optvalueno");
                    Elements b = e.getElementsByTag("div");
                    for (Element l : b) {
                        Elements c = l.getElementsByTag("img");
                        //이미지 url저장
                        url = c.attr("src");
                    }
                    htmlContentInStringFormat += "옵션번호 : " + opvalueno + "\n" + url + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat); //뷰에 노출
        }
    }}
