package com.mmabnets.mvvmrecyclerview.Models;

import java.util.Objects;

public class Mydata{
    public String photo;
    public String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mydata mydata = (Mydata) o;
        return Objects.equals(photo, mydata.photo);
    }


}
