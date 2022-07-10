package com.example.grouping;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String URL = "http://172.10.19.184:443/";
    private final String TAG = "request log";

    private Retrofit retrofit;
    private APIService service;

    public static ArrayList<JSONObject> jsonMypageArray =new ArrayList<>();
    Integer size = jsonMypageArray.size();
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
        mypageWritingCardview=view.findViewById(R.id.mypageseemywriting);
        mypageRatingCardview=view.findViewById(R.id.mypageseemyrating);
        mypageWritingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMywritingActivity.class);
                startActivity(intent);
            }
        });

        mypageRatingCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMyratingActivity.class);
                try {
                    intent.putExtra("user_id", jsonMypageArray.get(0).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        final Context context = view.getContext();
        ImageView imageView = view.findViewById(R.id.myPageImage);
        TextView nameView = view.findViewById(R.id.myPageName);
        TextView titleView = view.findViewById(R.id.myPageTitle);
        TextView attractView = view.findViewById(R.id.myPageAttract);
        TextView hobbyView = view.findViewById(R.id.myPageHobby);

        if(jsonMypageArray.size()==0){
            populateTable();
        }

        if(jsonMypageArray.size() != 0) {
            JSONObject jsonData = jsonMypageArray.get(0);
            try {
                nameView.setText(jsonData.getString("name"));
                titleView.setText(jsonData.getString("title"));
                attractView.setText(jsonData.getString("attractive"));
                hobbyView.setText(jsonData.getString("hobby_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateTable() {
        ProgressDialog mProgressDialog = ProgressDialog.show(getContext(),
                "Please wait",
                "Long operation starts...",
                true);
        new Thread() {
            @Override
            public void run() {
                try {
                    request();
                    getActivity().runOnUiThread(new Runnable() {
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

    private void request() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);

        Call<ResponseBody> call_get = service.getUser("3");
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.v(TAG, "result = " + result);
                        try {
                            JSONArray arr = new JSONArray(result);
                            for (int i=0; i<arr.length();i++){
                                jsonMypageArray.add(arr.getJSONObject(i));
                            }
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

}