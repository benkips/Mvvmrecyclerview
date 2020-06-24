package com.mmabnets.mvvmrecyclerview.Datasouce;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.Models.Vids;
import com.mmabnets.mvvmrecyclerview.Network.ApiClient;
import com.mmabnets.mvvmrecyclerview.Network.ApiInterface;
import com.mmabnets.mvvmrecyclerview.utils.NetworkState;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class VideoDatasource extends PageKeyedDataSource<Integer, Mydata> {
    private static ApiInterface apiInterface;
    public static final int pagesize = 2;
    public static final int firstpage = 1;
    public String dataz;


    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public VideoDatasource(String dataz) {
        this.dataz = dataz;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Mydata> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Vids>> call = apiInterface.getvideoz(firstpage, pagesize, dataz);
        call.enqueue(new Callback<List<Vids>>() {
            @Override
            public void onResponse(Call<List<Vids>> call, Response<List<Vids>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().get(2).mydata, null, firstpage + 1);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                }
            }

            @Override
            public void onFailure(Call<List<Vids>> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Mydata> callback) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Vids>> call = apiInterface.getvideoz(params.key, pagesize, dataz);
        call.enqueue(new Callback<List<Vids>>() {
            @Override
            public void onResponse(Call<List<Vids>> call, Response<List<Vids>> response) {
                Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
                Integer key = (params.key > 1) ? params.key - 1 : null;

                if (response.body() != null) {
                    callback.onResult(response.body().get(2).mydata, key);
                }
            }

            @Override
            public void onFailure(Call<List<Vids>> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Mydata> callback) {

        networkState.postValue(NetworkState.LOADING);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Vids>> call = apiInterface.getvideoz(params.key, pagesize, dataz);
        call.enqueue(new Callback<List<Vids>>() {
            @Override
            public void onResponse(Call<List<Vids>> call, Response<List<Vids>> response) {
                Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
                if (response.body() != null) {
                    Integer key = (response.body().get(1).total > params.key) ? params.key + 1 : null;
                    callback.onResult(response.body().get(2).mydata, key);
                    networkState.postValue(NetworkState.LOADED);
                }
            }

            @Override
            public void onFailure(Call<List<Vids>> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }

}