package com.example.grouping;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNV;
    public String rs;
    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView);

        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.floatingaddbtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateTable();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.framemainlayout, FragmentHome.class, null)
                    .commit();
        }

        /* ==== set navigation bar ==== */

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabchatting)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, FragmentChatting.class, null)
                        .commit();
            else if (itemId == R.id.tabhome)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, FragmentHome.class, null)
                        .commit();
            else if (itemId == R.id.tabmypage)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, FragmentMypage.class, null)
                        .commit();
            else
                return false;
            return true;
        });
        navigation.setOnItemReselectedListener(null);

    }
    private void populateTable() {
        ProgressDialog mProgressDialog = ProgressDialog.show(this, "Please wait", "Long operation starts...", true);
        new Thread() {
            @Override
            public void run() {
                try {
                    new JSONTask().execute("http://172.10.5.168:443/get/suggest");//AsyncTask 시작시킴
                    // code runs in a thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();

    }
    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", "2");
                jsonObject.accumulate("name", "김태훈");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tv.setText(result);
        }
    }
//    public class JSONTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//
//
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("user_id", "2");
//                jsonObject.accumulate("name", "김태훈");
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    URL url = new URL(urls[0]);
//                    con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("POST");//get방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
//
////                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//
//                    con.connect();//서버로 보내기위해서 스트림 만듬
//
//
//                    OutputStream outStream = con.getOutputStream();//버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//
//
//
//                    InputStream stream = con.getInputStream();
//                    reader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuffer buffer = new StringBuffer();
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            rs=result;
//        }
//    }

}