package com.mmabnets.mvvmrecyclerview.Datasouce;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.mmabnets.mvvmrecyclerview.Models.Mydata;


public class VideoDatasourcefactory  extends DataSource.Factory {
//private MutableLiveData<PageKeyedDataSource<Integer, Mydata>> Videolivedatasource=new MutableLiveData<>();

private MutableLiveData<VideoDatasource> mutableLiveData;

private String searchKey;
private VideoDatasource videoDatasource;


    public VideoDatasourcefactory(String searchKey) {
        this.searchKey = searchKey;
        this.mutableLiveData = new MutableLiveData<VideoDatasource>();
    }

    @NonNull
    @Override
    public DataSource create() {
        videoDatasource=new VideoDatasource(searchKey);
        //Videolivedatasource.postValue(videoDatasource);
        mutableLiveData.postValue(videoDatasource);

        return videoDatasource;
    }

    /*public MutableLiveData<PageKeyedDataSource<Integer, Mydata>> getVideolivedatasource() {
        return Videolivedatasource;
    }*/

    public MutableLiveData<VideoDatasource> getMutableLiveData() {
        return mutableLiveData;
    }

}
