package com.example.mytestapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mytestapplication.adapter.MyAdapter;
import com.example.mytestapplication.model.MyListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<MyListData> myList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        //Initailizing the Volley Library
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        myList = new ArrayList<>();

        //Call to fetch the API and display the items
        fetchList();

        //Refresh the page
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"Page refreshed ",Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

    private void fetchList() {

        String url = "https://picsum.photos/v2/list?page=2&limit=20";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String title = jsonObject.getString("author");
                                String description = jsonObject.getString("url");
                                String image = jsonObject.getString("download_url");

                                MyListData myListData = new MyListData(title , description, image);
                                myList.add(myListData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MyAdapter adapter = new MyAdapter(MainActivity.this , myList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}