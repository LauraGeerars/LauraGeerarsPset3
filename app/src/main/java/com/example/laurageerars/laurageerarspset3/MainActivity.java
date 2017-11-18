package com.example.laurageerars.laurageerarspset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

//In MainActivity worden de categories geregeld (maar dat is verwarrend met de naam CategoryActivity daar wordt Menu geregeld
public class MainActivity extends AppCompatActivity {
    public ArrayList<String> listcategory = new ArrayList<String>();
    public ListView CategoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoryListView = (ListView) findViewById(R.id.theCategoryListView);

        //Order button fixing
        //Button orderbutton = (Button) findViewById(R.id.OrderButton);
        //orderbutton.setOnClickListener(new OrderButtonClickListener());

        // code from: https://developer.android.com/training/volley/simple.html

        final TextView mTextView = (TextView) findViewById(R.id.textView);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/categories";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the characters of the response string.
                        //mTextView.setText("Response is: " + response);

                        try {

                            JSONObject newObject = (JSONObject) new JSONTokener(response).nextValue();
                            ArrayList<JSONObject> listcategory = new ArrayList<JSONObject>();
                            JSONArray categoryArray = newObject.getJSONArray("categories");
                            for (int i = 0; i < categoryArray.length(); i++) {
                                //listmenu.add(menuArray.getJSONObject(i));
                                //mTextView.setText(menuArray.getJSONObject(i).getString("name"));
                                addItem(categoryArray.get(i).toString());
                            }
                            Adapter();

                        } catch (JSONException e) {
                            //mTextView.setText(e.toString());
                            e.printStackTrace();


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

    public void Adapter() {
        final TextView mTextView = findViewById(R.id.textView);
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listcategory);
        ListView MenuListView = (ListView) findViewById(R.id.theCategoryListView);
        MenuListView.setAdapter(theAdapter);
        MenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoCategoryMenu(String.valueOf(adapterView.getItemAtPosition(i)));

            }
        });
    }

    public void addItem(String Item){

        listcategory.add(Item);
    }

    public void gotoCategoryMenu(String CategoryMenu){
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("CategoryMenu", CategoryMenu);
        startActivity(intent);
    }


    public void OrderClick(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}





