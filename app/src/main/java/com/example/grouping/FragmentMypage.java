package com.example.grouping;

import static com.example.grouping.MainActivity.current_user_id;

import android.content.Intent;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentMypage extends Fragment {

    CardView mypageWritingCardview;
    CardView mypageRatingCardview;
    Button mypageLogoutBtn;
    Button mypageSelectHobbyBtn;

    private static final String URL = "http://192.249.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;


    public FragmentMypage() {
        super(R.layout.fragment_chatting);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        final Context context = view.getContext();
        ImageView imageView = view.findViewById(R.id.myPageImage);
        TextView nameView = view.findViewById(R.id.myPageName);
        TextView titleView = view.findViewById(R.id.myPageTitle);
        TextView attractView = view.findViewById(R.id.myPageAttract);
        TextView hobbyView = view.findViewById(R.id.myPageHobby);
        mypageWritingCardview=view.findViewById(R.id.mypageseemywriting);
        mypageWritingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMywritingActivity.class);
                startActivity(intent);
            }
        });

        mypageSelectHobbyBtn=view.findViewById(R.id.myPageSelectHobby);
        mypageSelectHobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageSelectHobby.class);
                startActivity(intent);
            }
        });





        //이 안으로 못 들어감
        mypageLogoutBtn=view.findViewById(R.id.kakaoLogoutbtn);
        mypageLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(getActivity(), StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });
        mypageRatingCardview=view.findViewById(R.id.mypageseemyrating);
        mypageRatingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMyratingActivity.class);
                startActivity(intent);
            }
        });

//        int attractNum = Integer.parseInt(attractView.getText().toString());
//        if (attractNum >= 60 && attractNum < 80){
//            mypageHeartImage.setImageResource(R.drawable.heartbar4);
//        }
//        else if(attractNum >= 80){
//            mypageHeartImage.setImageResource(R.drawable.heartbar5);
//        }
//        else if (attractNum<=40 && attractNum >20){
//            mypageHeartImage.setImageResource(R.drawable.heartbar2);
//        }
//        else if (attractNum<=20){
//            mypageHeartImage.setImageResource(R.drawable.heartbar1);
//        }


        request(titleView,nameView,attractView,hobbyView);

    }


    private void request(TextView titleView,TextView nameView,TextView attractView,TextView hobbyView) {
        Call<ResponseBody> call_get = service.getUser(current_user_id);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray arr = new JSONArray(result);
                            nameView.setText(arr.getJSONObject(0).getString("name"));
                            attractView.setText(arr.getJSONObject(0).getString("attractive"));
                            hobbyView.setText(arr.getJSONObject(0).getString("hobby_id"));
                            requestTitle(titleView,arr.getJSONObject(0).getString("attractive"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestTitle(TextView titleView, String attractive) {
        Call<ResponseBody> call_get = service.getTitle(attractive);
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray arrTitle = new JSONArray(result);
                            titleView.setText(arrTitle.getJSONObject(0).getString("title_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kakaoLogout() {
        UserApiClient.getInstance().logout(error -> null);
    }

}