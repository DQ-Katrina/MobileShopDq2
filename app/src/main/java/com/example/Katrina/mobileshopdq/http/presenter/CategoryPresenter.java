package com.example.Katrina.mobileshopdq.http.presenter;

import com.example.Katrina.mobileshopdq.http.HttpMethods;
import com.example.Katrina.mobileshopdq.http.entity.CategoryEntity;

import java.util.List;

import rx.Subscriber;


public class CategoryPresenter extends HttpMethods {

    public static void getTopList(Subscriber<List<CategoryEntity>> subscriber){
        rx.Observable<List<CategoryEntity>> observable = categoryService.getTopList()
                .map(new HttpMethods.HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,subscriber);
    }

    public static void getSecondList(Subscriber subscriber, int parentId){
        rx.Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,subscriber);
    }
}