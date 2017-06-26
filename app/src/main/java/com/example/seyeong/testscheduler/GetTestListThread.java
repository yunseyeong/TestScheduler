package com.example.seyeong.testscheduler;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Seyeong on 2017-06-07.

*/
public class GetTestListThread extends Thread{
    static public boolean active=false;
    int data=0;
    public boolean isreceiver;
    int TotalCount;
    String[] slectureName, slectureCode;
    //
    String[] industry_engr, engr, professional_engr, craftman, master_engr;
    //
    boolean blectureName,blectureCode, blectureSeries,bTotalCount;
    boolean tResponse;
    String levelName;
    Handler handler;
    String Servicekey="ServiceKey=kb9YUbHdabaPOznNordxDZT0jN5tD7fAOlPnwPQYGXEPtXY1ALNit8JzdHFO%2F26JfC%2B9nNPULLEa50qF60xVGQ%3D%3D";
    String getInfo="http://openapi.q-net.or.kr/api/service/rest/InquiryListNationalQualifcationSVC/";
    String getTestFindinfo="getList?";
    int index_a=0, index_b=0, index_c=0, index_d=0, index_e=0;
    public GetTestListThread(boolean receiver, String level)
    {
        Log.w("LEVEL = ", level);
        handler = new Handler();
        isreceiver=receiver;
        levelName=level;
        blectureName=false;
    }
    public void run()
    {
        if(active)
        {
            try
            {
                slectureName = new String[1000];
                slectureCode = new String[1000];
                professional_engr = new String[1000];
                industry_engr = new String[1000];
                engr = new String[1000];
                master_engr = new String[1000];
                craftman = new String[1000];
                data=0;

                XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                String testUrl=getInfo+getTestFindinfo+Servicekey;
                //API인증주소 + 사용할 API함수 + 개발인증키
                URL url =new URL(testUrl);
                InputStream is = url.openStream();
                xpp.setInput(is, "UTF-8");

                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    switch(eventType)
                    {
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("jmFldNm"))//종목 이름 조회
                                blectureName=true;
                            if(xpp.getName().equals("jmCd"))//종목 코드 조회
                                blectureCode=true;
                            break;
                        case XmlPullParser.TEXT:
                            if(blectureName)
                            {//종목 이름 저장
                                slectureName[data]=xpp.getText();
                                blectureName=false;
                                //
                                if (slectureName[data].substring(slectureName[data].length() - 3, slectureName[data].length()).equals("업기사")) {
                                    industry_engr[index_a] = slectureName[data];
                                    index_a++;
                                    break;
                                } else if (slectureName[data].substring(slectureName[data].length() - 2, slectureName[data].length()).equals("기사")) {
                                    engr[index_b] = slectureName[data];
                                    index_b++;
                                    break;
                                } else if (slectureName[data].substring(slectureName[data].length() - 2, slectureName[data].length()).equals("술사")) {
                                    professional_engr[index_c] = slectureName[data];
                                    index_c++;
                                    break;
                                } else if (slectureName[data].substring(slectureName[data].length() - 2, slectureName[data].length()).equals("능장")) {
                                    master_engr[index_d] = slectureName[data];
                                    index_d++;
                                    break;
                                } else if (slectureName[data].substring(slectureName[data].length() - 2, slectureName[data].length()).equals("능사")) {
                                    craftman[index_e] = slectureName[data];
                                    index_e++;
                                    break;
                                }
                            }
                            if(blectureCode)
                            {//종목 코드 저장
                                slectureCode[data]=xpp.getText();
                                TotalCount=data;
                                blectureCode=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("response"))
                                view_text();//항목의 마지막이면 데이터 전달
                            if(xpp.getName().equals("mdobligFldCd"))
                                data++;//마지막 데이터면 데이터카운트 증가
                            break;
                    }
                    eventType=xpp.next();//다음 항목으로 이동
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void view_text()
    {
        Log.e("view_text", "in");
        handler.post(()->{
            active=false;
            Log.e("tResponse", "is"+tResponse);
            //if(tResponse)
            //{
                Log.e("cnt", ""+data);
                data=0;
            MainActivity.test_item=slectureName;
                MainActivity.testListThreadResponse(TotalCount, slectureCode, slectureName, industry_engr, index_a, engr, index_b, professional_engr, index_c, master_engr, index_d, craftman, index_e);
            MainActivity.testListInAutoTv(TotalCount, slectureCode, slectureName);
            //}
        });
    }
}
