package com.example.grouping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://192.249.19.184:443/";

    private View header;
    private static Socket socket;
    private static InputStream is;
    private static OutputStream os;
    private ImageView myhomeMyheart;
    private TextView myhomeMyheartNum;

    FragmentHome fragmentHome;

    public static String current_user_id;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if( !TextUtils.isEmpty(intent.getStringExtra("current_user_id")) ) {
            current_user_id = intent.getStringExtra("current_user_id");
        }

        try {
            socket = IO.socket(URL);
//            socket.on(Socket.EVENT_CONNECT, onMessage);
            socket.connect();
            socket.emit("EVENT_NAME", "DATA");
            //on이 서버에서 받는 거.
//            socket.on(Socket.EVENT_CONNECT, onMessage);
            //emit이 주는 거. request response.
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("send","socket FAIL");
        }


        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.floatingaddbtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //emit이 클라이언트에서 받는 거
                socket.emit("msg", "hi");
                Log.e("send","data");
                Intent intent = new Intent(MainActivity.this, HomeWriteActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.framemainlayout, FragmentChatting.class, null)
                    .commit();
        }

        header=getLayoutInflater().inflate(R.layout.fragment_mypage, null, false);
        myhomeMyheart=(ImageView) header.findViewById(R.id.mypageHeartBar);
        myhomeMyheartNum=(TextView) header.findViewById(R.id.myPageAttract);


        //하단 메뉴바 구현
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
//        navigation.setSelectedItemId(R.id.tabhome);
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
            else if (itemId == R.id.tabmypage) {
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.framemainlayout, FragmentMypage.class, null)
                        .commit();

            }
            else
                return false;
            return true;
        });
        navigation.setOnItemReselectedListener(null);

    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = String.valueOf(args[0]);
                    Log.e("get",data);
                }
            });
        }
    };

}