package com.mmabnets.mvvmrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mmabnets.mvvmrecyclerview.Adapters.Videoadapter;
import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.Viewmodel.Videoviewmodel;
import com.mmabnets.mvvmrecyclerview.Viewmodel.Videoviewmodeltwo;
import com.mmabnets.mvvmrecyclerview.utils.NetworkState;


public class MainActivity extends AppCompatActivity {
    Videoviewmodel videoviewmodel;
    Videoviewmodeltwo videoviewmodeltwo;
    Videoadapter videoadapter;
    private RecyclerView rv;
    private EditText searchq;
    private Button srchbtnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoviewmodel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Videoviewmodel.class);
        videoviewmodeltwo = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Videoviewmodeltwo.class);

        srchbtnn = findViewById(R.id.srchbtn);
        searchq = findViewById(R.id.srchquery);

        searchq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();
                videoviewmodeltwo.filterTextAll.setValue(query);
            }
        });
        //query
        srchbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchq.getText().toString().trim();
                videoviewmodeltwo.filterTextAll.setValue(query);
            }
        });

        //recyclerview
        rv = findViewById(R.id.rvd);
        rv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(manager);
        videoadapter = new Videoadapter(this);

        videoviewmodeltwo.filterTextAll.setValue("");
        videoviewmodeltwo.mydatapagedlist.observe(this, new Observer<PagedList<Mydata>>() {
            @Override
            public void onChanged(PagedList<Mydata> mydata) {
                loading();
                videoadapter.submitList(mydata);
            }
        });



        rv.setAdapter(videoadapter);


    }
    private  void loading(){
        videoviewmodeltwo.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                Toast.makeText(MainActivity.this, networkState.getMsg(), Toast.LENGTH_SHORT).show();;
                videoadapter.setNetworkState(networkState);
            }
        });
    }
}
