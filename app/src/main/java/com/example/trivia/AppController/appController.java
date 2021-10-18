package com.example.trivia.AppController;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class appController extends Application {
     private static appController instance;
     private RequestQueue requestQueue;

     public static synchronized appController getInstance(){
         return instance;
     }

     public RequestQueue getRequestQueue(){
         if(requestQueue == null){
             requestQueue = Volley.newRequestQueue(getApplicationContext());
         }
         return requestQueue;
     }

     public <T> void addToRequestQueue(Request<T> req){
         getRequestQueue().add(req);
     }

     public void onCreate(){
         super.onCreate();
         instance = this;
     }
}
