package com.example.Katrina.mobileshopdq.http.service;


import com.example.Katrina.mobileshopdq.http.entity.CategoryEntity;
import com.example.Katrina.mobileshopdq.http.entity.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface CategoryService {

    @GET("cat/show")
    Observable<HttpResult<List<CategoryEntity>>> getTopList();

    @GET("cat/show/{parentId}")
    Observable<HttpResult<List<CategoryEntity>>> getSecondList(
            @Path("parentId") int parentId
    );
}