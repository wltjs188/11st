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

    String[] productList={"2561192508","1562551963","1125616073","2118337055","106598793","2555593319","1582758351","1487678688","1350259768","2248396230"
    ,"2435582233","1676108423","2459334462","1409918456","1527780447","2516257890","2339074037","2386389981","786092814","1470964507",
            "1134877208","1516602141","80659589","1411199360","1411199360","286582659","1718864143","767479579","142578216","1406735575",
            "19015293","81447566","97951629","1485026251","80739333","1406735575","2555580390","1437108439","1616598459","1595783629","1213820186","1231220577","1231220577","1774217869","2169260239",
            "1287039133","1652648933","2136860723","449370932","2383651361","1805529477","1748893368","2423996673","700625477","2544186121","41119945","1680608842","17807434","1437861102","1293929125",
            "2554584234","1891870528","1443293443","2570759935","2570871974","2418385362","496755049","1563270643","38168884","1131760352","1508027664","1486946425","1856319504","2527932690","2543587595",
            "2569307739","2567929601","2571487110","2570410241","1891870794","2342532763","2571499168","2568585378","2571516439","2552342072","2571669392","2571416736","1263772182","1500126583","2569573888",
            "1223384973","2571259735","2571413991","2383732083","189236553","990823394","1487688499","2039250220","2274346528","1455718052","1355025883","2355004619","2494518334",
            "390971109","975629171","1942629375","2569629484","2564525562","2529486814","526247809","143339833","143339833","1446736942",
            "185144673","1116123128","827707902","1571073370","2118060124","827312225","2567063592","2567063592","2164650922","1970064606","2524261378","41686160","2044062312","1004852394","1543710867","2561406523",
            "1984048999","2311811877","2537964232","2517031249","1980744464","2322446535","2329247940","361102851","2536243572","1228327675","2546038637",
            "2563530668","1657513612","1254598940","1651822964","1452592762","797893777","1381989763","1381989763","991310147","2568861544",
            "2544008247","2543656276","1008570781","2570927175","2569631946",
            "1918328509","2561000004","2568693930","2434737049","2299839253",
            "2571508658","90336998","1129368529","1580925721","2393864400",
            "724715285","1127541141","1127541141","820255150","257190124","1770919141"
    };


    public List<Option> search_detail() {
        List<Option> list = null;
        List<Option> realList=null;
        String productCode="";

        for (int i = 0; i < productList.length; i++) {
            productCode="";
            URL_DETAIL="http://openapi.11st.co.kr/openapi/OpenApiService.tmall?key=ad722ec66e955e9c584c2b828158dee9&apiCode=ProductInfo&productCode=";
            URL_DETAIL=URL_DETAIL+productList[i]+"&option=PdOption,ProductReview";
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
                DiscountInfo d =null;

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
                                case "Chip":
                                    d=new DiscountInfo();
                                    String point = parser.nextText();
                                    d.setPoint(point);
                                    Log.i("포인트", point);
                                    break;
                                case "Installment":
                                    String installment = parser.nextText();
                                    d.setInstallment(installment);
                                    Log.i("무이자", installment);
                                    break;
                                case "ShipFee":
                                    String shipFee = parser.nextText();
                                    d.setShipFee(shipFee);
                                    Log.i("배송비", shipFee);
                                    break;
                                case "Value":
                                    o=new Option();
                                    o.setDiscountInfo(d);
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
                                case "Description": //리뷰내용
                                    String review=parser.nextText();
                                    Log.i("리뷰",review);
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
