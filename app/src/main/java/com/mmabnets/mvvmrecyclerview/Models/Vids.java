package com.mmabnets.mvvmrecyclerview.Models;




import java.util.List;

public class Vids {

    public String status;
    public Integer total;

    public List<Mydata> mydata;

   /* public Vids(String status, List<Mydata> mydata) {
        this.status = status;
        this.mydata = mydata;
    }*/

    public Vids(String status, Integer total, List<Mydata> mydata) {
        this.status = status;
        this.total = total;
        this.mydata = mydata;
    }
}


/*class Mydata {
    @SerializedName("photo")
    @Expose
    private String photo;
}*/
