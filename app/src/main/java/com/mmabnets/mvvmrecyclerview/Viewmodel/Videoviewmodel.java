package com.mmabnets.mvvmrecyclerview.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mmabnets.mvvmrecyclerview.Models.Vids;
import com.mmabnets.mvvmrecyclerview.Repo.Videorepo;

import java.util.List;

public class Videoviewmodel extends AndroidViewModel {
    Videorepo videorepo;

    public Videoviewmodel(@NonNull Application application) {
        super(application);
        videorepo=new Videorepo(application);
    }
    public LiveData<List<Vids>> getallvideos(){
        return videorepo.getallvideos();
    }
}
