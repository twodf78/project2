package com.example.grouping;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class StartActivity extends AppCompatActivity {
    ImageButton kakaoLogin;
    TextView kakaoLogout;


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
        kakaoLogout = findViewById(R.id.kakaoLogout);

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

    private void kakaoLogout() {
        UserApiClient.getInstance().logout(error -> null);
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
//                userName = kakaoUser.getNickname();
//                userProfileUrl = kakaoUser.getProfileImageUrl();
//                userId = "" + user.getId();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
            return null;
        });
    }
}