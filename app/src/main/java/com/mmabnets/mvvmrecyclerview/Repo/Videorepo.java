package com.mmabnets.mvvmrecyclerview.Repo;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.Models.Vids;
import com.mmabnets.mvvmrecyclerview.Network.ApiClient;
import com.mmabnets.mvvmrecyclerview.Network.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Videorepo {
    private ArrayList<Vids> vidlist = new ArrayList<>();
    private MutableLiveData<List<Vids>> mutableLiveData;
    private Application application;
    private static ApiInterface apiInterface;

    private  int page=1;
    private int count=10;


    public Videorepo(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Vids>> getallvideos() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Vids>> call = apiInterface.getvideoz(page,count,"");
        call.enqueue(new Callback<List<Vids>>() {
            @Override
            public void onResponse(Call<List<Vids>> call, Response<List<Vids>> response) {
                List<Mydata> videos = response.body().get(2).mydata;
                String status  = response.body().get(1).status;
                /* vidlist.add(new Vids(status,videos));*/
                mutableLiveData.setValue(vidlist);


            }

            @Override
            public void onFailure(Call<List<Vids>> call, Throwable t) {


            }
        });
        return mutableLiveData;

    }
}
