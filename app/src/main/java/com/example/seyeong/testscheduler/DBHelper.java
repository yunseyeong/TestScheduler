package com.example.seyeong.testscheduler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seyeong on 2017-06-11.
 */

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 FAVORITEBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE FAVORITEBOOK (name TEXT, number TEXT," +
                " doc_reg TEXT, doc_test TEXT, doc_pass TEXT, doc_submit TEXT, prac_reg TEXT, prac_text TEXT, prac_pass TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String number, String doc_reg_date, String des, String docpass, String doc_submit_date, String prac_reg_date, String pes, String pracpass) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        String insertText = "INSERT INTO FAVORITEBOOK VALUES('" + name + "', '" + number + "', '" + doc_reg_date + "', '" + des + "', '" + docpass + "', '" + doc_submit_date + "', '"+prac_reg_date+"', '" + pes + "', '" + pracpass +"');";
        //
        //db.execSQL("DROP TABLE FAVORITEBOOK");
        //
        db.execSQL(insertText);
        db.close();
    }
    /*
    public void update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }
*/
    public void delete(String name, String number) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        String sql = "DELETE FROM FAVORITEBOOK WHERE name='" + name + "'and " +"number='"+ number+ "';";
        //db.execSQL("DELETE FROM FAVORITEBOOK WHERE name='" + name + "'and " +"number='"+ number+ "';");
        db.execSQL(sql);
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM FAVORITEBOOK", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + "/"
                    + cursor.getString(1)
                    + "/"
                    + cursor.getString(2)
                    + "/"
                    + cursor.getString(3)
                    + "/"
                    +cursor.getString(4)
                    + "/"
                    + cursor.getString(5)
                    + "/"
                    + cursor.getString(6)
                    + "/"
                    + cursor.getString(7)
                    + "/"
                    + cursor.getString(8)
                    +"/";
        }
        return result;
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("select count(*) from table");
        Cursor cursor = db.rawQuery("SELECT name FROM FAVORITEBOOK", null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        //int count = cursor.getCount();
        cursor.close();
        return count;
    }
}

