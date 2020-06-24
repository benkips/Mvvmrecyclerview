package com.mmabnets.mvvmrecyclerview.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.mmabnets.mvvmrecyclerview.Datasouce.VideoDatasource;
import com.mmabnets.mvvmrecyclerview.Datasouce.VideoDatasourcefactory;
import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.utils.NetworkState;


public class Videoviewmodeltwo extends ViewModel {

    public LiveData<PagedList<Mydata>> mydatapagedlist;
    //public LiveData<PageKeyedDataSource<Integer,Mydata>> liveDatasource;

    //search
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    //loading
    VideoDatasourcefactory videoDatasourcefactory;
    private LiveData<NetworkState> networkState;

    public Videoviewmodeltwo() {
    //liveDatasource=videoDatasourcefactory.getVideolivedatasource();
        init();
    //  mydatapagedlist=(new LivePagedListBuilder(videoDatasourcefactory,config)).build();

    }

    private  void init(){
        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(VideoDatasource.pagesize)
                .build();

        mydatapagedlist= Transformations.switchMap(
                filterTextAll,input -> {
                    videoDatasourcefactory= new VideoDatasourcefactory(input);
                    networkState = Transformations.switchMap(videoDatasourcefactory.getMutableLiveData(),
                            dataSource -> dataSource.getNetworkState());
                    return (new LivePagedListBuilder(videoDatasourcefactory,config))
                            .build();
                });

    }
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
