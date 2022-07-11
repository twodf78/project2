package com.example.grouping;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grouping.post.PostUser;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class    StartActivity extends AppCompatActivity {
    ImageButton kakaoLogin;
    TextView kakaoLogout;
    private static final String URL = "http://192.249.19.184:443/";

    private Retrofit retrofit;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewInit();
        // kakao
        if (AuthApiClient.getInstance().hasToken()) {
            UserApiClient.getInstance().accessTokenInfo((accessTokenInfo, error) -> {
                if (error != null) {
                    Log.d("token error", "토큰 없음");
                } else if (accessTokenInfo != null) {
                    Log.i("token ok", "토큰 정보 보기 성공" +
                            "\n회원번호: " + accessTokenInfo.getId() +
                            "\n만료시간: " + accessTokenInfo.getExpiresIn() + "초");
                    kakaoGetUserInfo();
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Log.d("token ok", "토큰 있음 근데 만료됨");
                }
                return null;
            });
        } else {
            // 토큰 없을 때 -> 로그인 창
        }

        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(StartActivity.this))
                    kakaoLogin();
                else
                    kakaoAccountLogin();
            }
        });
    }

    private void viewInit(){
        kakaoLogin = findViewById(R.id.kakaoLoginbtn);
    }

    // 로그인 공통 callback 구성
    public void kakaoLogin() {
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(StartActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                kakaoGetUserInfo();
            }
            return null;
        });
    }

    public void kakaoAccountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(StartActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                kakaoGetUserInfo();
            }
            return null;
        });
    }

    public void kakaoGetUserInfo() {
        String TAG = "kakaoGetUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                Log.d(TAG, "로그인 완료");
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: " + user.getId());
                }
                Account kakaoUserAccount = user.getKakaoAccount();
                Profile kakaoUser = kakaoUserAccount.getProfile();

                //String type으로 해서 putExtra 해서 intent로 값 넘겨주기
                String userName = kakaoUser.getNickname();
                String userProfileUrl = kakaoUser.getProfileImageUrl();
                String userId = "" + user.getId();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("current_user_id", userId);
                intent.putExtra("current_user_name", userName);
                intent.putExtra("current_user_image",userProfileUrl);
                postUser(userId, userName, userProfileUrl);
                startActivity(intent);
            }
            return null;
        });
    }
    public void postUser(String userId, String userName,String userProfileUrl){
        PostUser post = new PostUser(userId,userName, userProfileUrl, 1,  50);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        Call<PostUser> call_put = service.postUser( post);
        call_put.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                if (response.isSuccessful()) {
                    String result = response.toString();
                    Log.v(TAG, "result = " + result);
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}