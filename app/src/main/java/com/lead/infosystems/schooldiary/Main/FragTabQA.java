package com.lead.infosystems.schooldiary.Main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lead.infosystems.schooldiary.R;
import com.lead.infosystems.schooldiary.Data.QuestionData;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragTabQA extends Fragment {

    View rootview;
    ListView listView;
    ArrayAdapter<QuestionData> adapter;
    List<QuestionData> items = new ArrayList<QuestionData>();
    public FragTabQA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_tab_qa, container, false);
        listView = (ListView) rootview.findViewById(R.id.qa_listview);
        populateListView();
        return rootview;
    }

    private void populateListView(){
        items.add(new QuestionData("Umar Mujale","2 days ago","What is the portion for next test..",getResources().getDrawable(R.drawable.student1)));
        items.add(new QuestionData("Naseem Mirza","3 days ago","Someone having class notes plzz..",getResources().getDrawable(R.drawable.student2)));
        items.add(new QuestionData("Suresh Patil","4 days ago","which textbook is good for algebra.",getResources().getDrawable(R.drawable.student3)));
        items.add(new QuestionData("Ramesh Bhat","6 days ago","what is the solution for maths exercise 1.4 and also are there any more questions",getResources().getDrawable(R.drawable.student4)));
        adapter = new MyListAdaptor();
        listView.setAdapter(adapter);
    }

    class MyListAdaptor extends ArrayAdapter<QuestionData> {

        public MyListAdaptor() {
            super(getActivity().getApplicationContext(), R.layout.question_ans_item, items);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemview;
            itemview = convertView;
            if(itemview == null){
                itemview = getActivity().getLayoutInflater().inflate(R.layout.question_ans_item,parent,false);
            }
            final QuestionData item = items.get(position);

            TextView name = (TextView) itemview.findViewById(R.id.name);
            name.setText(item.getname());
            TextView date = (TextView) itemview.findViewById(R.id.date);
            date.setText(item.getDate());
            TextView question= (TextView) itemview.findViewById(R.id.message);
            question.setText(item.getQuestion());
            ImageView propic = (ImageView) itemview.findViewById(R.id.profile_image);
            propic.setImageDrawable(item.getDrawable());

            return itemview;
        }
    }
}
