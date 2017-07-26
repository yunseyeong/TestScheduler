package com.example.seyeong.testscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements TextWatcher, View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button getBtn, prevBtn, nextBtn, favoriteBtn;
    static Spinner level, lecture;
    static String levellist[]={"기술사", "기능장", "기사", "산업기사", "기능사"};
    static String lecturelist[], codelist[];//api에서 가져올 시험과목들의 목록
    static String[] industry_engr, engr, professional_engr, craftman, master_engr;
    static ArrayAdapter<String> spinnerLevel, spinnerLecture;
    static TextView totalcnt;
    //목록들
    static TextView jm_fld_nm, impl_plan_nm, doc_reg_start_dt, doc_reg_end_dt, doc_exam_start_dt, doc_exam_end_dt, doc_pass_dt,
    doc_submit_start_dt, doc_submit_end_dt, prac_reg_start_dt, prac_reg_end_dt, prac_exam_start_dt, prac_exam_end_dt, prac_pass_start_dt, prac_pass_end_dt, oblig_fld_cd, oblig_fld_nm, mdoblig_fld_cd, mdoblig_fld_nm;
    //시험정보들
    static int testCnt=0;
    static int count=0;//회차를 샐 카운트
    static int index=0;//arrayadapter의 인덱스 무슨시험인지를 결정
    static Context mContext;
    static AutoCompleteTextView actv;
    static String[] test_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init()
    {
        //하나의 토스트메시지만 사용하기 위한 객체 생성
        professional_engr = new String[1000];
        industry_engr = new String[1000];
        engr = new String[1000];
        master_engr = new String[1000];
        craftman = new String[1000];
        //
        mContext=getApplicationContext();
        totalcnt=(TextView)findViewById(R.id.totalcnt);
        jm_fld_nm=(TextView)findViewById(R.id.JM_FLD_NM);
        impl_plan_nm=(TextView)findViewById(R.id.IMPL_PLAN_NM);
        doc_reg_start_dt=(TextView)findViewById(R.id.DOC_REG_START_DT);
        doc_reg_end_dt=(TextView)findViewById(R.id.DOC_REG_END_DT);
        doc_exam_start_dt=(TextView)findViewById(R.id.DOC_EXAM_START_DT);
        doc_exam_end_dt=(TextView)findViewById(R.id.DOC_EXAM_END_DT);
        doc_pass_dt=(TextView)findViewById(R.id.DOC_PASS_DT);
        doc_submit_start_dt=(TextView)findViewById(R.id.DOC_SUBMIT_START_DT);
        doc_submit_end_dt=(TextView)findViewById(R.id.DOC_SUBMIT_END_DT);
        prac_reg_start_dt=(TextView)findViewById(R.id.PRAC_REG_START_DT);
        prac_reg_end_dt=(TextView)findViewById(R.id.PRAC_REG_END_DT);
        prac_exam_start_dt=(TextView)findViewById(R.id.PRAC_EXAM_START_DT);
        prac_exam_end_dt=(TextView)findViewById(R.id.PRAC_EXAM_END_DT);
        prac_pass_start_dt=(TextView)findViewById(R.id.PRAC_PASS_START_DT);
        prac_pass_end_dt=(TextView)findViewById(R.id.PRAC_PASS_END_DT);
        oblig_fld_cd=(TextView)findViewById(R.id.OBLIG_FLD_CD);
        oblig_fld_nm=(TextView)findViewById(R.id.OBLIG_FLD_NM);
        mdoblig_fld_cd=(TextView)findViewById(R.id.MDOBLIG_FLD_CD);
        mdoblig_fld_nm=(TextView)findViewById(R.id.MDOBLIG_FLD_NM);

        getBtn=(Button)findViewById(R.id.getBtn);
        prevBtn=(Button)findViewById(R.id.prevBtn);
        nextBtn=(Button)findViewById(R.id.nextBtn);
        favoriteBtn=(Button)findViewById(R.id.favorite_Btn);
        //
        level=(Spinner)findViewById(R.id.level);
        lecture=(Spinner)findViewById(R.id.lecture);
        level.setOnItemSelectedListener(this);
        lecture.setOnItemSelectedListener(this);
        //
        actv=(AutoCompleteTextView)findViewById(R.id.autoTv);
        actv.setOnItemSelectedListener(this);
        //
        getBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        spinnerLevel=new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, levellist);
        spinnerLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(spinnerLevel);
    }

    public static void getFindTest(String code)
    {
        GetFindTestThread.active=true;
        GetFindTestThread getFindTestThread = new GetFindTestThread(false, code);
        getFindTestThread.start();
    }
    public static void findTestThreadResponse(int getCnt, String[] jm_Fld_Nm, String[]  impl_Plan_Nm, String[] doc_Reg_Start_Dt, String[] doc_Reg_End_Dt,
                                              String[] doc_Exam_Start_Dt, String[] doc_Exam_End_Dt, String[] doc_Pass_Dt,
                                              String[] doc_Submit_Start_Dt, String[] doc_Submit_End_Dt, String[] prac_Reg_Start_Dt,
                                              String[] prac_Reg_End_Dt, String[] prac_Exam_Start_Dt, String[] prac_Exam_End_Dt, String[] prac_Pass_Start_Dt,
                                              String[] prac_Pass_End_Dt, String[] oblig_Fld_Cd, String[] oblig_Fld_Nm, String[] mdoblig_Fld_Cd, String[] mdoblig_Fld_Nm) throws ParseException {
        testCnt=0;
        testCnt=getCnt;
        Log.w("getcnt",""+getCnt);
        if(getCnt==0)
        {
            //국가 자격증이 아닐 경우 에러메시지와 함께 텍스트뷰의 텍스트가 비어진다.
            totalcnt.setTextColor(Color.RED);
            totalcnt.setText("국가자격증만 검색할 수 있습니다.");
            jm_fld_nm.setText(" ");
            impl_plan_nm.setText(" ");
            doc_reg_start_dt.setText(" ");
            doc_reg_end_dt.setText(" ");
            doc_exam_start_dt.setText("");
            doc_exam_end_dt.setText(" ");
            doc_pass_dt.setText(" ");
            doc_submit_start_dt.setText(" ");
            doc_submit_end_dt.setText(" ");
            prac_reg_start_dt.setText(" ");
            prac_reg_end_dt.setText(" ");
            prac_exam_start_dt.setText(" ");
            prac_exam_end_dt.setText(" ");
            prac_pass_start_dt.setText(" ");
            prac_pass_end_dt.setText(" ");
            oblig_fld_cd.setText(" ");
            oblig_fld_nm.setText(" ");
            mdoblig_fld_cd.setText(" ");
            mdoblig_fld_nm.setText(" ");
        }
        else
        {
            //국가 자격증일 경우 해당 자격증의 정보를 출력해준다.
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일");

            totalcnt.setTextColor(Color.DKGRAY);
            totalcnt.setText("검색 결과입니다.");
            jm_fld_nm.setText(jm_Fld_Nm[count]);
            impl_plan_nm.setText(impl_Plan_Nm[count]);
            //
            try {
                Date date = sdf1.parse(doc_Reg_Start_Dt[count]);
                doc_reg_start_dt.setText(sdf2.format(date));
                //
                date = sdf1.parse(doc_Reg_End_Dt[count]);
                doc_reg_end_dt.setText(sdf2.format(date));

                date = sdf1.parse(doc_Exam_Start_Dt[count]);
                doc_exam_start_dt.setText(sdf2.format(date));

                date = sdf1.parse(doc_Exam_End_Dt[count]);
                doc_exam_end_dt.setText(sdf2.format(date));

                date = sdf1.parse(doc_Pass_Dt[count]);
                doc_pass_dt.setText(sdf2.format(date));

                date = sdf1.parse(doc_Submit_Start_Dt[count]);
                doc_submit_start_dt.setText(sdf2.format(date));

                date = sdf1.parse(doc_Submit_End_Dt[count]);
                doc_submit_end_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Reg_Start_Dt[count]);
                prac_reg_start_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Reg_End_Dt[count]);
                prac_reg_end_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Exam_Start_Dt[count]);
                prac_exam_start_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Exam_End_Dt[count]);
                prac_exam_end_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Pass_Start_Dt[count]);
                prac_pass_start_dt.setText(sdf2.format(date));

                date = sdf1.parse(prac_Pass_End_Dt[count]);
                prac_pass_end_dt.setText(sdf2.format(date));

                oblig_fld_cd.setText(oblig_Fld_Cd[count]);

                oblig_fld_nm.setText(oblig_Fld_Nm[count]);

                mdoblig_fld_cd.setText(mdoblig_Fld_Cd[count]);

                mdoblig_fld_nm.setText(mdoblig_Fld_Nm[count]);
            }
            catch (Exception e)
            {}
        }
        GetFindTestThread.active=false;
        GetFindTestThread.interrupted();
    }

    public static void getTestList(String level)
    {
        GetTestListThread.active=true;
        GetTestListThread getTestListThread = new GetTestListThread(false, level);
        getTestListThread.start();
    }

    public static void testListInAutoTv(int cnt, String[] code, String[] test)
    {
        testCnt=0;
        testCnt=cnt;
        test_item= new String[testCnt];
        System.arraycopy(test, 0, test_item,0, testCnt);
        actv.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1,test_item));
        actv.setTextColor(Color.GRAY);
    }

    public static void testListThreadResponse(int cnt, String[] code, String[] test, String[] industry, int industry_cnt, String[] engineer, int engineer_cnt, String[] professional, int professional_cnt,
                                              String[] master, int master_cnt, String[] craft, int craft_cnt)
    {
        testCnt=0;
        testCnt=cnt;//스레드의 시험갯수 복사
        lecturelist= new String[testCnt];
        lecturelist=test;//스레드의 시험목록 복사
        codelist = new String[testCnt];
        codelist=code;//스레드의 시험코드 복사
        //
        industry_engr= new String[industry_cnt];
        industry_engr=industry;
        engr = new String[engineer_cnt];
        engr = engineer;
        professional_engr = new String[professional_cnt];
        professional_engr = professional;
        master_engr = new String[master_cnt];
        master_engr= master;
        craftman = new String[craft_cnt];
        craftman = craft;
        //

        String selected = (String)level.getSelectedItem();
        Log.e("Selected", ""+selected);
        //
        if (selected.equals("산업기사"))
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, industry_engr);
        else if (selected.equals("기술사"))
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, professional_engr);
        else if (selected.equals("기사"))
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, engr);
        else if(selected.equals("기능장"))
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, master_engr);
        else if(selected.equals("기능사"))
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, craftman);
        else
            spinnerLecture=new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, lecturelist);
        spinnerLecture.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //시험목록을 Spinner에 붙임
        lecture.setAdapter(spinnerLecture);
    }

    @Override
    public void onClick(View v) {
        int where=0;
        String testName;
        testName = actv.getText().toString();

        switch (v.getId())
        {
            case R.id.getBtn:
                //검색 버튼 클릭
                count=0;
                for(int i=0; i<600; i++)
                {
                    if(lecturelist[i] != null)
                        if(lecturelist[i].equals(testName))
                            where=i;
                }
                getFindTest(codelist[where]);
                break;
            case R.id.nextBtn:
                //-> 버튼 클릭
                Log.w("nextbtn", "clicked");
                if(testCnt>count) {
                    count++;
                    for(int i=0; i<600; i++)
                    {
                        if(lecturelist[i] != null)
                            if(lecturelist[i].equals(testName))
                                where=i;
                    }
                    getFindTest(codelist[where]);
                }
                else
                {
                    Snackbar.make(v, "올해의 마지막 시험입니다.", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
                break;
            case R.id.prevBtn:
                //<- 버튼 클릭
                if(count>0)
                {
                    count--;
                    for(int i=0; i<600; i++)
                    {
                        if(lecturelist[i] != null)
                            if(lecturelist[i].equals(testName))
                                where=i;
                    }
                    getFindTest(codelist[where]);
                }
                else
                {
                    Snackbar.make(v, "올해의 첫번째 시험입니다.", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
                break;
            case R.id.favorite_Btn:
                //즐겨찾기 버튼 클릭
                Intent intent = new Intent(this, FavoriteActivity.class);
                TextView name = (TextView)findViewById(R.id.JM_FLD_NM);
                TextView number=(TextView)findViewById(R.id.IMPL_PLAN_NM);
                TextView drs =(TextView)findViewById(R.id.DOC_REG_START_DT);
                TextView dre =(TextView)findViewById(R.id.DOC_REG_END_DT);
                TextView des =(TextView)findViewById(R.id.DOC_EXAM_START_DT);
                TextView docpass =(TextView)findViewById(R.id.DOC_PASS_DT);
                TextView dss=(TextView)findViewById(R.id.DOC_SUBMIT_START_DT);
                TextView dse =(TextView)findViewById(R.id.DOC_SUBMIT_END_DT);
                TextView prs =(TextView)findViewById(R.id.PRAC_REG_START_DT);
                TextView pre =(TextView)findViewById(R.id.PRAC_REG_END_DT);
                TextView pes =(TextView)findViewById(R.id.PRAC_EXAM_START_DT);
                TextView pracpass =(TextView)findViewById(R.id.PRAC_PASS_START_DT);

                intent.putExtra("jm_fld_nm", name.getText().toString());
                intent.putExtra("number", number.getText().toString());
                intent.putExtra("drs", drs.getText().toString());
                intent.putExtra("dre",dre.getText().toString());
                intent.putExtra("des", des.getText().toString());
                intent.putExtra("docpass",docpass.getText().toString());
                intent.putExtra("dss",dss.getText().toString());
                intent.putExtra("dse",dse.getText().toString());
                intent.putExtra("prs",prs.getText().toString());
                intent.putExtra("pre",pre.getText().toString());
                intent.putExtra("pes",pes.getText().toString());
                intent.putExtra("pracpass",pracpass.getText().toString());

                startActivity(intent);
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId())
        {
            case R.id.level:
                Log.e("level click", "true");
                getTestList(levellist[position]);
                break;
            case R.id.lecture:
                try
                {
                    index=0;
                    index=position;
                    Log.e("lecture name = ", lecturelist[position]);
                    String selectTest = (String) lecture.getSelectedItem();
                    actv.setText(selectTest);
                }
                catch (Exception e)
                {
                    Log.e("exception = ",""+e);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
