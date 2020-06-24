package com.mmabnets.mvvmrecyclerview.Network;


import com.mmabnets.mvvmrecyclerview.Models.Mydata;
import com.mmabnets.mvvmrecyclerview.Models.Vids;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("downloadtest2.php")
    Call<List<Vids>> getvideoz(@Query("pageno") int page, @Query("itemcount") int items,@Query("search") String query );

  /*  @FormUrlEncoded
    @POST("login.php")
     Call<ResponseBody> performuserLogin(@FieldMap Map<String, String> fields);*/
}
