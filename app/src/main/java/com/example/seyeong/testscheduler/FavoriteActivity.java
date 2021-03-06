package com.example.seyeong.testscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.seyeong.testscheduler.R.id.count_textview;

/**
 * Created by Seyeong on 2017-06-10.
 */

public class FavoriteActivity extends Activity implements View.OnClickListener {
    Button insertBtn, deleteBtn, prevBtn, nextBtn;
    TextView name_tv, number_tv, drs_tv, des_tv, docpass_tv, dss_tv, prs_tv, pes_tv, pracpass_tv, count_tv;
    DBHelper dbHelper;
    String name, number, doc_reg_date, des, docpass, doc_submit_date, prac_reg_date, pes, pracpass;
    String[] favorite;
    int index = 0, count = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        try {
            Intent intent = getIntent();
            name_tv = (TextView) findViewById(R.id.name_textview);
            number_tv = (TextView) findViewById(R.id.number_textview);
            drs_tv = (TextView) findViewById(R.id.drs_textview);
            des_tv = (TextView) findViewById(R.id.des_textview);
            docpass_tv = (TextView) findViewById(R.id.docpass_textview);
            dss_tv = (TextView) findViewById(R.id.dss_textview);
            prs_tv = (TextView) findViewById(R.id.prs_textview);
            pes_tv = (TextView) findViewById(R.id.pes_textview);
            pracpass_tv = (TextView) findViewById(R.id.pracpass_textview);
            count_tv = (TextView) findViewById(count_textview);

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
            //
            if (name != null)
                name_tv.setText(name);
            if (number != null)
                number_tv.setText(number);
            if (drs != null)
                drs_tv.setText(doc_reg_date);
            if (des != null)
                des_tv.setText(des);
            if (docpass != null)
                docpass_tv.setText(docpass);
            if (dss != null)
                dss_tv.setText(doc_submit_date);
            if (prs != null)
                prs_tv.setText(prac_reg_date);
            if (pes != null)
                pes_tv.setText(pes);
            if (pracpass != null)
                pracpass_tv.setText(pracpass);
            if (count != 0)
                count_tv.setText(count);
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
        init();
    }

    public void init() {
        index = 0;
        dbHelper = new DBHelper(getApplicationContext(), "FAVORITEBOOK.db", null, 1);
        insertBtn = (Button) findViewById(R.id.insert_Btn);
        deleteBtn = (Button) findViewById(R.id.delete_Btn);
        prevBtn = (Button) findViewById(R.id.prevFavor_Btn);
        nextBtn = (Button) findViewById(R.id.nextFavor_Btn);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        //showFavorite(); //여기서 호출하면 오류발생
    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.insert_Btn:
                    Log.e("insert", "Btn");
                    Snackbar.make(v, "즐겨찾기 추가", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    favorite = dbHelper.getResult().split("/");
                    dbHelper.insert(name, number, doc_reg_date, des, docpass, doc_submit_date, prac_reg_date, pes, pracpass);
                    index = 0;
                    showFavorite();
                    break;
                case R.id.delete_Btn:
                    Log.e("delete", "Btn");
                    Snackbar.make(v, "즐겨찾기 삭제", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    favorite = dbHelper.getResult().split("/");
                    String delete = name_tv.getText().toString();//시험이름
                    String delete2 = number_tv.getText().toString();//회차
                    dbHelper.delete(delete, delete2);
                    index = 0;
                    showFavorite();
                    break;
                case R.id.prevFavor_Btn:
                    if (index > 0) {
                        favorite = dbHelper.getResult().split("/");
                        index -= 9;
                        showFavorite();
                    } else {
                        Snackbar.make(v, "첫번째 즐겨찾기 입니다.", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                    }
                    break;
                case R.id.nextFavor_Btn:
                    if ((index + 9) < favorite.length) {
                        favorite = dbHelper.getResult().split("/");
                        index += 9;
                        showFavorite();
                    } else {
                        Snackbar.make(v, "마지막 즐겨찾기 입니다.", Snackbar.LENGTH_SHORT).setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                    }
            }
            //count = dbHelper.getCount();// 총 갯수 출력 위치가 이상하니 바꿔라
        } catch (Exception e) {
        }
        //count_tv.setText(count + "개");// 이것도 여기 두면 안될것같다
    }

    public void showFavorite() {
        if (index + 8 < favorite.length) {
            name_tv.setText(favorite[index]);
            number_tv.setText(favorite[index + 1]);
            drs_tv.setText(favorite[index + 2]);
            des_tv.setText(favorite[index + 3]);
            docpass_tv.setText(favorite[index + 4]);
            dss_tv.setText(favorite[index + 5]);
            prs_tv.setText(favorite[index + 6]);
            pes_tv.setText(favorite[index + 7]);
            pracpass_tv.setText(favorite[index + 8]);
        } else {
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
        count = favorite.length/9;//시험당 정보가 9개이므로 정보의 갯수를 9로 나눈것이 즐겨찾기의 갯수이다.
        count_tv.setText((index/9+1) + "/" + count + "개");
    }
}
