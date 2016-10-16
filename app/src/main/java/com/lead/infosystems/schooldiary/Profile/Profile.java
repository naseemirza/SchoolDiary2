package com.lead.infosystems.schooldiary.Profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.lead.infosystems.schooldiary.Data.QuestionData;
import com.lead.infosystems.schooldiary.R;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ArrayList<QuestionData> listdata = new ArrayList<QuestionData>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Umar Mujale");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listdata.add(new QuestionData("Umar Mujale","2 days ago","My Post",getResources().getDrawable(R.drawable.a2)));
        listdata.add(new QuestionData("Umar Mujale","3 days ago","My Post",getResources().getDrawable(R.drawable.a1)));
        listdata.add(new QuestionData("Umar Mujale","4 days ago","My Post",getResources().getDrawable(R.drawable.a3)));
        listdata.add(new QuestionData("Umar Mujale","6 days ago","My Post",getResources().getDrawable(R.drawable.a4)));
        adapter = new MyAdapter(listdata);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_profile,menu);
        return true;
    }
}
