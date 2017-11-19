package com.example.laurageerars.laurageerarspset3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class OrderActivity extends AppCompatActivity {
    public ListView OrderListView;
    public ArrayList<String> order = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        OrderListView = (ListView) findViewById(R.id.theOrderListView);

        Adapter();
    }


    public void Adapter() {
        final TextView mTextView = findViewById(R.id.textView);
        ArrayList<String> listmenu = getOrder();
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listmenu);
        ListView MenuListView = (ListView) findViewById(R.id.theOrderListView);
        MenuListView.setAdapter(theAdapter);
        MenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public ArrayList<String> getOrder() {
        ArrayList<String> result = new ArrayList<String>();
        SharedPreferences output = this.getSharedPreferences("order", MODE_PRIVATE);
        int count = output.getInt("count", 0);
        for (int i = 0; count+1 > i; i++) {
            String stringcount = Integer.toString(i);
            if (output.contains(stringcount)){
            result.add(output.getString(stringcount, null).toString());}
        }
        return result;
    }
    public void clearItems(View view){
        SharedPreferences prefs = this.getSharedPreferences("order",this.MODE_PRIVATE);
        SharedPreferences.Editor Editor = prefs.edit();
        Editor.clear().commit();
        Context context = getApplicationContext();
        CharSequence text = "Cleared your order";
        int toastduration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, toastduration);
        toast.show();
        Adapter();
    }

    public void submitOrder(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/order";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);
                            Context context = getApplicationContext();
                            CharSequence text = "The preparation time of your order is: " + newObject.getString("preparation_time");
                            int toastduration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, toastduration);
                            toast.show();
                        }

                        catch (JSONException e) {
                            e.printStackTrace();


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


}



