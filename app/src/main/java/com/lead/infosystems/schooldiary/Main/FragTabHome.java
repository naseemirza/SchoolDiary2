package com.lead.infosystems.schooldiary.Main;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lead.infosystems.schooldiary.Data.Post_Data;
import com.lead.infosystems.schooldiary.Data.UserDataSP;
import com.lead.infosystems.schooldiary.R;
import com.lead.infosystems.schooldiary.ServerConnection.ServerConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragTabHome extends Fragment implements AdapterItem.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    View rootview;
    private ArrayList<Post_Data> itemlist;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdapterItem adapterItem;
    String POST_MIN = "0";

    public FragTabHome() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        loadData();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.frag_tab_home, container, false);
        itemlist = new ArrayList<Post_Data>();
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.rvList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterItem = new AdapterItem(this, getActivity().getApplicationContext());
        adapterItem.setLinearLayoutManager(linearLayoutManager);
        adapterItem.setRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterItem);
        recyclerView.getItemAnimator().setChangeDuration(0);

        swipeRefreshLayout.setOnRefreshListener(this);
        return rootview;
    }

    private void loadData() {
        new PostDataLoad().execute("0");
    }

    @Override
    public void onLoadMore() {
            adapterItem.setProgressMore(true);
            Log.d("POST_MIN",POST_MIN+"");
            new PostDataLoad().execute(POST_MIN);
    }

    @Override
    public void onRefresh() {
        POST_MIN = "0";
        loadData();
    }

    private class PostDataLoad extends AsyncTask<String, Void, String> {

        ServerConnect serverConnect = new ServerConnect();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("min", params[0]);
            String query = builder.build().getQuery();
            try {
                return serverConnect.downloadUrl("fetchpost.php", query);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                storeData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeData(String s) throws JSONException {
        itemlist.clear();
        JSONArray jsonArray = new JSONArray(s);
        if (POST_MIN == "0") {
            Log.e("1st",s);
            for (int i = 0; i <= jsonArray.length() - 1; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                itemlist.add(new Post_Data(jsonObject.getString("first_name"), jsonObject.getString("last_name"),
                        jsonObject.getString("id"), jsonObject.getString("text_message"),
                        jsonObject.getString("src_link"), jsonObject.getString("date")));
                if (i == jsonArray.length() - 1) {
                    POST_MIN = jsonObject.getString("id");
                }
            }
            swipeRefreshLayout.setRefreshing(false);
            adapterItem.addAll(itemlist);
        } else {
            Log.e("2snd",POST_MIN);
            adapterItem.setProgressMore(false);
            for (int i = 0; i <= jsonArray.length() - 1; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                itemlist.add(new Post_Data(jsonObject.getString("first_name"), jsonObject.getString("last_name"),
                        jsonObject.getString("id"), jsonObject.getString("text_message"),
                        jsonObject.getString("src_link"), jsonObject.getString("date")));
                if (i == jsonArray.length() - 1) {
                    POST_MIN = jsonObject.getString("id");
                }
            }
            adapterItem.addItemMore(itemlist);
            adapterItem.setMoreLoading(false);
        }
    }
    public boolean lastItem(){
        if(POST_MIN.contains("1")){
            return true;
        }else{
            return false;
        }
    }
}