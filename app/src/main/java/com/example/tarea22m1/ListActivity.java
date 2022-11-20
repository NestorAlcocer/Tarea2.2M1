package com.example.tarea22m1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {
    ArrayList<String> headerContent = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        obtenerListaPersonas();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, headerContent);
        contentList = (ListView) findViewById(R.id.contentList);
        contentList.setAdapter(arrayAdapter);
    }


    private void obtenerListaPersonas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContentApi contentApi = retrofit.create(ContentApi.class);
        Call<List<ContentModel>> getList = contentApi.getContents();
        getList.enqueue(new Callback<List<ContentModel>>() {
            @Override
            public void onResponse(Call<List<ContentModel>> call, Response<List<ContentModel>> response){
                for(ContentModel content : response.body())
                {
                    Log.i(content.getTitle(), "onResponse");
                    headerContent.add(content.getTitle());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ContentModel>> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}