package com.example.laurageerars.laurageerarspset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Er staat CategoryActivity maar dit bestand is voor het regelen van het menu van de verschillende categorieen
public class CategoryActivity extends AppCompatActivity {
    final public ArrayList<String> listmenu = new ArrayList<String>();
    ListView MenuListView;
    String CategoryMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //String[] test = {"test", "hoi"};
        //Adapter(test);
        MenuListView = (ListView) findViewById(R.id.theMenuListView);

        // code from: https://developer.android.com/training/volley/simple.html

        final TextView mTextView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        CategoryMenu = (String) intent.getStringExtra("CategoryMenu");
        mTextView.setText(CategoryMenu);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/menu";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the characters of the response string.
                        //mTextView.setText("Response is: " + response);
                    if (response != null ) {
                        try {

                            JSONObject newObject = (JSONObject) new JSONTokener(response).nextValue();
                            //ArrayList<JSONObject> listmenu = new ArrayList<JSONObject>();
                            JSONArray menuArray = newObject.getJSONArray("items");
                            for (int i = 0; i < menuArray.length(); i++) {
                                //listmenu.add(menuArray.getJSONObject(i));
                                //mTextView.setText(menuArray.getJSONObject(i).getString("name"));
                                if (Objects.equals(menuArray.getJSONObject(i).getString("category"), CategoryMenu)) {
                                    //addItem(menuArray.getJSONObject(i).getString("name"));
                                    listmenu.add(menuArray.getJSONObject(i).getString("name"));
                                }
                            }
                            //Toast.makeText(CategoryActivity.this, listmenu.toString(), Toast.LENGTH_LONG).show();
                            Adapter(listmenu);


                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void Adapter(List Menu) {
        final TextView mTextView = findViewById(R.id.textView);
        //Log.d("test", "test1");
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Menu);
        //Log.d("test", "test2");

        ListView MenuListView = (ListView) findViewById(R.id.theMenuListView);
        //Log.d("test", "test3");

        MenuListView.setAdapter(theAdapter);
        //Log.d("test", "test4");

        MenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //gotoCategoryMenu(String.valueOf(adapterView.getItemAtPosition(i)));


            }
        });
    }

    public void addItem(String Item){
        listmenu.add(Item);
    }

    public void addOrder(String Item){
        SharedPreferences prefs = getSharedPreferences("order", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Item, Item);
        prefsEditor.commit();


    }
    public void gotoOrder(String Order){
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("Order", Order);
        startActivity(intent);
    }




}


