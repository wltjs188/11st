package com.example.samsung.myapplication;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * Created by SAMSUNG on 2019-03-19.
 */

public class ProductSearchService {
    String URL_DETAIL="";

    //원피스
//    String[] productList ={"14129013", "2421930203", "2214911420", "602904262",  "2319996891", "2406950987", "796247461", "820255150", "54150416", "1788295032", "86899719", "1065082805", "1684537568", "1684537568", "1065316907", "1443435844", "1127541141", "85552030", "567202245", "567202245", "2319996891",
//            "1173558823", "1698091418", "490333290", "16954640", "14708455", "1878082899", "16983949", "2258948125", "1197219729", "1979883393","1872292454", "2346303523", "2190437266", "54150416", "1779030549", "1255229793", "2418637376", "1857172300", "1164974120", "1516201127", "1444409522", "2038464407", "820255150", "368805752", "1779030549", "2319996891", "1770919141", "1443435844", "2370547699", "1255229793"
//    };

    //하의
    String[] productList={"30432998","1017008148","2459456198","1978909970","60937795","41119945","1522475374","1988820436","3059633","2409735325","8840192","186774740","1057474966","2426064755","1709518944","1434474102","2462512601","41119945","748083957","2423633084","2450593925","2471993196",
        "2435669515","2039250220","2459346058","1270463397","786230245","2434469822","2368346692","2421935894","2395835000","2381790838","2420822164","1700597803","2397203559","1124027418","2373908150","2364883905","2390159074","2371231210","2445871452","2407705648","2375520198","2439300227","2078801694",
        "2345426253","2392324302","1967132239","2382916876","1856319504","1899306059","1306404063","1236879639","412482241","1766878207","1306404063","1436826869","2410444031","2100286290","2461911541","1487675157"};
    //상의



    public List<Option> search_detail() {
        List<Option> list = null;
        List<Option> realList=null;
        String productCode="";

        for (int i = 0; i < productList.length; i++) {
            productCode="";
            URL_DETAIL="http://openapi.11st.co.kr/openapi/OpenApiService.tmall?key=ad722ec66e955e9c584c2b828158dee9&apiCode=ProductInfo&productCode=";
            URL_DETAIL=URL_DETAIL+productList[i]+"&option=PdOption";
            String titleName="";
            productCode=productList[i];


            try {
                java.net.URL url;
                url = new URL(URL_DETAIL);
                URLConnection urlConn = url.openConnection();

                // xml파서객체만들고
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                // 요청에 대한 응답 결과를 파서에 세팅
                parser.setInput(new InputStreamReader(urlConn.getInputStream(), "EUC-KR"));

                int eventType = parser.getEventType();
                Option o=null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.END_DOCUMENT: // 문서의 끝
                            break;
                        case XmlPullParser.START_DOCUMENT:
                            if(realList==null){
                                list=new ArrayList<Option>();
                                realList=new ArrayList<Option>();
                            }
                            else {
                                realList.addAll(list);
                                list = new ArrayList<Option>();
                            }
                            break;
                        case XmlPullParser.END_TAG: {
                            String tag=parser.getName();
                            if(tag.equals("Value")){
                                list.add(o);
                                o=null;
                            }
                            break;
                        }
                        case XmlPullParser.START_TAG: {
                            String tag = parser.getName();
                            switch (tag) {
                                case "Value":
                                    o=new Option();
                                    o.setOptionTitle(titleName);
                                    o.setProductCode(productCode);
                                    break;
                                case "Order":
                                    if (o != null) {
                                        String a=parser.nextText();
                                        o.setOptionOrder(a);
                                    }
                                    break;
                                case "TitleName":
                                        String title = parser.nextText();
                                        titleName = title;
                                    break;
                                case "ValueName":
                                    if (o != null) {
                                        String name=parser.nextText();
                                        o.setOptionValue(name);
                                    }
                                    break;
                                case "Price":
                                    if (o != null) {
                                        String price = parser.nextText();
                                        o.setOptionPrice(price);
                                    }
                                    break;
                            }
                        }
                    }
                    eventType = parser.next();
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return realList;
    }


}

