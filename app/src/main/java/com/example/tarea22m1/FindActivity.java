package com.example.tarea2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindActivity extends AppCompatActivity {

    ListView contentList;
    ArrayList<String> headerContent = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        txtId = (EditText) findViewById(R.id.txtId);

        txtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ObtenerUsuario(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, headerContent);
        contentList = (ListView) findViewById(R.id.resultList);
        contentList.setAdapter(arrayAdapter);
    }

    private void ObtenerUsuario(String texto)
    {
        if (headerContent.size()>0)
        {
            headerContent.remove(0);
        }
        arrayAdapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContentApi contentApi = retrofit.create(ContentApi.class);

        Call<ContentModel> calllista = contentApi.getContent(texto);
        calllista.enqueue(new Callback<ContentModel>() {
            @Override
            public void onResponse(Call<ContentModel> call, Response<ContentModel> response)
            {
                Log.i(response.body().getTitle(), "onResponse");
                headerContent.add(response.body().getTitle());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ContentModel> call, Throwable t) {

            }
        });
    }
    private void obtenerListaPersonas()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContentApi usuariosApi = retrofit.create(ContentApi.class);

        Call<List<ContentModel>> getList = usuariosApi.getContents();

        getList.enqueue(new Callback<List<ContentModel>>() {
            @Override
            public void onResponse(Call<List<ContentModel>> call, Response<List<ContentModel>> response)
            {
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