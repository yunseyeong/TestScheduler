package com.example.seyeong.testscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Seyeong on 2017-06-10.
 */

public class FavoriteActivity extends Activity implements View.OnClickListener {
    Button insertBtn, selectBtn, deleteBtn, prevBtn, nextBtn;
    TextView name_tv, number_tv, drs_tv, dre_tv, des_tv, docpass_tv, dss_tv, dse_tv, prs_tv, pre_tv, pes_tv, pracpass_tv;
    DBHelper dbHelper;
    String name, number, doc_reg_date, des, docpass, doc_submit_date, prac_reg_date, pes, pracpass;
    String[] favorite;
    int index=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        try {
            Intent intent = getIntent();

        /*TextView */
            name_tv = (TextView) findViewById(R.id.name_textview);
            number_tv = (TextView) findViewById(R.id.number_textview);
            drs_tv = (TextView) findViewById(R.id.drs_textview);
            des_tv = (TextView) findViewById(R.id.des_textview);
            docpass_tv = (TextView) findViewById(R.id.docpass_textview);
            dss_tv = (TextView) findViewById(R.id.dss_textview);
            prs_tv = (TextView) findViewById(R.id.prs_textview);
            pes_tv = (TextView) findViewById(R.id.pes_textview);
            pracpass_tv = (TextView) findViewById(R.id.pracpass_textview);

            name = intent.getStringExtra("jm_fld_nm"); //DB
            number = intent.getStringExtra("number"); //DB

            //필기접수 시작 ~ 마감
            String drs = intent.getStringExtra("drs");
            String dre = intent.getStringExtra("dre");
            doc_reg_date = drs + " ~ " + dre.substring(6, 13);//합침 DB

            //필기시험일
            des = intent.getStringExtra("des");//DB

            //필기발표
            docpass = intent.getStringExtra("docpass");//DB

            //응시자격서류
            String dss = intent.getStringExtra("dss");
            String dse = intent.getStringExtra("dse");
            doc_submit_date = dss + " ~ " + dse.substring(6, 13);//합침 DB

            //실기접수시작
            String prs = intent.getStringExtra("prs");
            String pre = intent.getStringExtra("pre");
            prac_reg_date = prs + " ~ " + pre.substring(6, 13);//합침 DB

            //실기 시험일
            pes = intent.getStringExtra("pes");// DB

            //실기 발표일일
            pracpass = intent.getStringExtra("pracpass"); // DB

        if(name != null)
            name_tv.setText(name);
        if(number != null)
            number_tv.setText(number);
        if(drs != null)
            drs_tv.setText(doc_reg_date);
        if(des!=null)
            des_tv.setText(des);
        if(docpass != null)
            docpass_tv.setText(docpass);
        if(dss!=null)
            dss_tv.setText(doc_submit_date);
        if(prs!=null)
            prs_tv.setText(prac_reg_date);
        if(pes!=null)
            pes_tv.setText(pes);
        if(pracpass!=null)
            pracpass_tv.setText(pracpass);
        }catch (Exception e)
        {}
        init();
    }

    public void init()
    {
        index=0;
        dbHelper = new DBHelper(getApplicationContext(), "FAVORITEBOOK.db", null, 1);
        insertBtn=(Button)findViewById(R.id.insert_Btn);
        deleteBtn=(Button)findViewById(R.id.delete_Btn);
        prevBtn=(Button)findViewById(R.id.prevFavor_Btn);
        nextBtn=(Button)findViewById(R.id.nextFavor_Btn);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }
    public void onClick(View v){
        try {
            switch (v.getId()) {
                case R.id.insert_Btn:
                    Log.e("insert", "Btn");
                    Toast.makeText(getApplicationContext(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                    favorite = dbHelper.getResult().split("/");
                    dbHelper.insert(name, number, doc_reg_date, des, docpass, doc_submit_date, prac_reg_date, pes, pracpass);
                    index = 0;
                    break;
                case R.id.delete_Btn:
                    Log.e("delete", "Btn");
                    Toast.makeText(getApplicationContext(), "즐겨찾기 삭제", Toast.LENGTH_SHORT).show();
                    favorite = dbHelper.getResult().split("/");
                    String delete = name_tv.getText().toString();
                    dbHelper.delete(delete);
                    index = 0;
                    showFavorite();
                    break;
                case R.id.prevFavor_Btn:
                    if (index > 0) {
                        favorite = dbHelper.getResult().split("/");
                        index -= 9;
                        showFavorite();
                    }
                    break;
                case R.id.nextFavor_Btn:
                    if ((index + 9) < favorite.length) {
                        favorite = dbHelper.getResult().split("/");
                        index += 9;
                        showFavorite();
                    }
            }
        }catch (Exception e)
        {}
    }

    public void showFavorite()
    {
        if(index+8<favorite.length) {
            name_tv.setText(favorite[index]);
            number_tv.setText(favorite[index + 1]);
            drs_tv.setText(favorite[index + 2]);
            des_tv.setText(favorite[index + 3]);
            docpass_tv.setText(favorite[index + 4]);
            dss_tv.setText(favorite[index + 5]);
            prs_tv.setText(favorite[index + 6]);
            pes_tv.setText(favorite[index + 7]);
            pracpass_tv.setText(favorite[index + 8]);
        }
        else
        {
            name_tv.setText("");
            number_tv.setText("");
            drs_tv.setText("");
            des_tv.setText("");
            docpass_tv.setText("");
            dss_tv.setText("");
            prs_tv.setText("");
            pes_tv.setText("");
            pracpass_tv.setText("");
        }
    }
}
