package com.example.test;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.FoodModel;
import com.example.test.ViewHolder.ComboAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    View view;
    ArrayList<FoodModel> combolist = new ArrayList<>();


    public BlankFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ComboAdapter(combolist));


        return view;
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String eggurl="http://61.247.229.49:8082/biryani/food/cat/egg";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
       final ArrayList<FoodModel> foodModels=new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, eggurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array =new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                FoodModel foodItem = new FoodModel();
                                JSONObject object = array.getJSONObject(i);
                                foodItem.setFoodName(object.getString("food_name"));
                                foodItem.setFoodCat(object.getString("food_cat"));
                                foodItem.setFoodDes(object.getString("food_desc"));
                                foodItem.setFoodPrice(object.getInt("food_price"));
                                foodItem.setFood_imag_url(object.getString("food_image_blob"));
                                foodModels.add(i,foodItem);
                            }
                            System.out.println("here your fooditemsssssssssssssssssssssssssssssss"+foodModels);
                            RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
                            recyclerView.hasFixedSize();
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            recyclerView.setAdapter(new ComboAdapter(foodModels));

                        }catch (Exception e)
                        {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }
}
