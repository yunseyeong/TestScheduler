package com.example.seyeong.testscheduler;

import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by Seyeong on 2017-06-07.
 */

public class GetFindTestThread extends Thread {
    Button getBtn;
    EditText whatTest;
    static public boolean active=false;
    int data=0;
    public boolean isreceiver;
    int TotalCount;
    String[] slectureName, slectureCode;
    boolean blectureName,blectureCode ,bTotalCount;
    boolean tResponse;
    String levelName;
    Handler handler;
    String Servicekey="ServiceKey=kb9YUbHdabaPOznNordxDZT0jN5tD7fAOlPnwPQYGXEPtXY1ALNit8JzdHFO%2F26JfC%2B9nNPULLEa50qF60xVGQ%3D%3D";
    String getInfo="http://openapi.q-net.or.kr/api/service/rest/InquiryTestInformationNTQSVC/";
    String getTestFindinfo="getJMList?";
    String test = "jmCd=";
    String codeName;
    String[] s_jm_fld_nm, s_impl_plan_nm, s_doc_reg_start_dt, s_doc_reg_end_dt, s_doc_exam_start_dt, s_doc_exam_end_dt, s_doc_pass_dt,
            s_doc_submit_start_dt, s_doc_submit_end_dt, s_prac_reg_start_dt, s_prac_reg_end_dt, s_prac_exam_start_dt,
            s_prac_exam_end_dt, s_prac_pass_start_dt, s_prac_pass_end_dt, s_oblig_fld_cd, s_oblig_fld_nm, s_mdoblig_fld_cd, s_mdoblig_fld_nm;

    boolean b_jm_fld_nm, b_impl_plan_nm, b_doc_reg_start_dt, b_doc_reg_end_dt, b_doc_exam_start_dt, b_doc_exam_end_dt, b_doc_pass_dt,
            b_doc_submit_start_dt, b_doc_submit_end_dt, b_prac_reg_start_dt, b_prac_reg_end_dt, b_prac_exam_start_dt, b_prac_exam_end_dt, b_prac_pass_start_dt, b_prac_pass_end_dt,
            b_oblig_fld_cd, b_oblig_fld_nm, b_mdoblig_fld_cd, b_mdoblig_fld_nm;
    public GetFindTestThread(boolean receiver, String code)
    {
        Log.w("code", ""+code);
        handler=new Handler();
        isreceiver=receiver;
        codeName=code;
        b_jm_fld_nm=b_impl_plan_nm=b_doc_reg_start_dt=b_doc_reg_end_dt=b_doc_exam_start_dt=b_doc_exam_end_dt=b_doc_pass_dt=
                b_doc_submit_start_dt=b_doc_submit_end_dt=b_prac_reg_start_dt=b_prac_reg_end_dt=b_prac_exam_start_dt=b_prac_exam_end_dt=b_prac_pass_start_dt=b_prac_pass_end_dt=
                b_oblig_fld_cd=b_oblig_fld_nm=b_mdoblig_fld_cd=b_mdoblig_fld_nm;
    }
    public void run()
    {
        if(active)
        {
            try
            {
                s_jm_fld_nm=new String[100];
                s_impl_plan_nm=new String[100];
                s_doc_reg_start_dt=new String[100];
                s_doc_reg_end_dt=new String[100];
                s_doc_exam_start_dt=new String[100];
                s_doc_exam_end_dt=new String[100];
                s_doc_pass_dt=new String[100];
                s_doc_submit_start_dt=new String[100];
                s_doc_submit_end_dt=new String[100];
                s_prac_reg_start_dt=new String[100];
                s_prac_reg_end_dt=new String[100];
                s_prac_exam_start_dt=new String[100];
                s_prac_exam_end_dt=new String[100];
                s_prac_pass_start_dt=new String[100];
                s_prac_pass_end_dt=new String[100];
                s_oblig_fld_cd=new String[100];
                s_oblig_fld_nm=new String[100];
                s_mdoblig_fld_cd=new String[100];
                s_mdoblig_fld_nm=new String[100];
                data=0;

                XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                String infoUrl = getInfo+getTestFindinfo+test+codeName+"&"+Servicekey;
                //API인증주소 + 사용할 API함수 + jmCd= + 시험코드 + & + 개발인증키
                URL url = new URL(infoUrl);
                InputStream is =url.openStream();
                xpp.setInput(is, "UTF-8");

                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    Log.e("eventType", ""+eventType);
                    switch(eventType)
                    {
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equals("docExamEndDt"))
                                b_doc_exam_end_dt=true;
                            if(xpp.getName().equals("docExamStartDt"))
                                b_doc_exam_start_dt=true;
                            if(xpp.getName().equals("docPassDt"))
                                b_doc_pass_dt=true;
                            if(xpp.getName().equals("docRegEndDt"))
                                b_doc_reg_end_dt=true;
                            if(xpp.getName().equals("docRegStartDt"))
                                b_doc_reg_start_dt=true;
                            if(xpp.getName().equals("docSubmitEndDt"))
                                b_doc_submit_end_dt=true;
                            if(xpp.getName().equals("docSubmitStartDt"))
                                b_doc_submit_start_dt=true;
                            if(xpp.getName().equals("implPlanNm"))
                                b_impl_plan_nm=true;
                            if(xpp.getName().equals("jmFldNm"))
                                b_jm_fld_nm=true;
                            if(xpp.getName().equals("mdobligFldCd"))
                                b_mdoblig_fld_cd=true;
                            if(xpp.getName().equals("mdobligFldNm"))
                                b_mdoblig_fld_nm=true;
                            if(xpp.getName().equals("obligFldCd"))
                                b_oblig_fld_cd=true;
                            if(xpp.getName().equals("obligFldNm"))
                                b_oblig_fld_nm=true;
                            if(xpp.getName().equals("pracExamEndDt"))
                                b_prac_exam_end_dt=true;
                            if(xpp.getName().equals("pracExamStartDt"))
                                b_prac_exam_start_dt=true;
                            if(xpp.getName().equals("pracPassEndDt"))
                                b_prac_pass_end_dt=true;
                            if(xpp.getName().equals("pracPassStartDt"))
                                b_prac_pass_start_dt=true;
                            if(xpp.getName().equals("pracRegEndDt"))
                                b_prac_reg_end_dt=true;
                            if(xpp.getName().equals("pracRegStartDt"))
                                b_prac_reg_start_dt=true;
                            break;

                        case XmlPullParser.TEXT:
                            if(b_doc_exam_end_dt)
                            {
                                s_doc_exam_end_dt[data] = xpp.getText();
                                b_doc_exam_end_dt=false;
                            }
                            if(b_doc_exam_start_dt)
                            {
                                s_doc_exam_start_dt[data] = xpp.getText();
                                b_doc_exam_start_dt=false;
                            }
                            if(b_doc_pass_dt)
                            {
                                s_doc_pass_dt[data] = xpp.getText();
                                b_doc_pass_dt=false;
                            }
                            if(b_doc_reg_end_dt)
                            {
                                s_doc_reg_end_dt[data] = xpp.getText();
                                b_doc_reg_end_dt=false;
                            }
                            if(b_doc_reg_start_dt)
                            {
                                s_doc_reg_start_dt[data] = xpp.getText();
                                b_doc_reg_start_dt=false;
                            }
                            ///
                            if(b_doc_submit_end_dt)
                            {
                                s_doc_submit_end_dt[data]=xpp.getText();
                                b_doc_submit_end_dt=false;
                            }
                            if(b_doc_submit_start_dt)
                            {
                                s_doc_submit_start_dt[data]=xpp.getText();
                                b_doc_submit_start_dt=false;
                            }
                            if(b_impl_plan_nm)
                            {
                                s_impl_plan_nm[data]=xpp.getText();
                                b_impl_plan_nm=false;
                            }
                            if(b_jm_fld_nm)
                            {
                                s_jm_fld_nm[data]=xpp.getText();
                                b_jm_fld_nm=false;
                            }
                            if(b_mdoblig_fld_cd)
                            {
                                s_mdoblig_fld_cd[data]=xpp.getText();
                                b_mdoblig_fld_cd=false;
                            }
                            if(b_mdoblig_fld_nm)
                            {
                                s_mdoblig_fld_nm[data]=xpp.getText();
                                b_mdoblig_fld_nm=false;
                            }
                            if(b_oblig_fld_cd)
                            {
                                s_oblig_fld_cd[data]=xpp.getText();
                                b_oblig_fld_cd=false;
                            }
                            if(b_oblig_fld_nm)
                            {
                                s_oblig_fld_nm[data]=xpp.getText();
                                b_oblig_fld_nm=false;
                            }
                            if(b_prac_exam_end_dt)
                            {
                                s_prac_exam_end_dt[data]=xpp.getText();
                                b_prac_exam_end_dt=false;
                            }
                            if(b_prac_exam_start_dt)
                            {
                                s_prac_exam_start_dt[data]=xpp.getText();
                                b_prac_exam_start_dt=false;
                            }
                            if(b_prac_pass_end_dt)
                            {
                                s_prac_pass_end_dt[data]=xpp.getText();
                                b_prac_pass_end_dt=false;
                            }
                            if(b_prac_pass_start_dt)
                            {
                                s_prac_pass_start_dt[data]=xpp.getText();
                                b_prac_pass_start_dt=false;
                            }
                            if(b_prac_reg_end_dt)
                            {
                                s_prac_reg_end_dt[data]=xpp.getText();
                                b_prac_reg_end_dt=false;
                            }
                            if(b_prac_reg_start_dt)
                            {
                                s_prac_reg_start_dt[data]=xpp.getText();
                                b_prac_reg_start_dt=false;
                                TotalCount=data;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("response"))
                                view_text();
                            if(xpp.getName().equals("pracRegStartDt"))
                                data++;
                            break;
                    }
                    eventType=xpp.next();
                }
                //s_doc_exam_end_dt=new String[100];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void view_text()
    {
        handler.post(()->{
            active=false;
            try {
                MainActivity.findTestThreadResponse(TotalCount,s_jm_fld_nm, s_impl_plan_nm, s_doc_reg_start_dt, s_doc_reg_end_dt, s_doc_exam_start_dt, s_doc_exam_end_dt, s_doc_pass_dt,
                        s_doc_submit_start_dt, s_doc_submit_end_dt, s_prac_reg_start_dt, s_prac_reg_end_dt, s_prac_exam_start_dt,
                        s_prac_exam_end_dt, s_prac_pass_start_dt, s_prac_pass_end_dt, s_oblig_fld_cd, s_oblig_fld_nm, s_mdoblig_fld_cd, s_mdoblig_fld_nm);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
