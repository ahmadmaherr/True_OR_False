package com.example.trivia.data;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.AppController.appController;
import com.example.trivia.MainActivity;
import com.example.trivia.model.question;


import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;


public class data{
    ArrayList<question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json";

public List<question> getQuestions(final synchronizing callback){
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            response -> {
                for(int i =0; i<response.length(); i++){
                    try {
                        question question = new question( response.getJSONArray(i).get(0).toString(), response.getJSONArray(i).getBoolean(1));
                        questionArrayList.add(question);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
                if(null != callback){callback.processFinished(questionArrayList);  }
                },
            error -> {
       /*         Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show();*/

            });

    appController.getInstance().addToRequestQueue(jsonArrayRequest);
    return questionArrayList;
}



}
