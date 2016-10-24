package com.lead.infosystems.schooldiary.Main;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lead.infosystems.schooldiary.Data.Post_Data;
import com.lead.infosystems.schooldiary.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<Post_Data> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;
    private Context context;
    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public AdapterItem(OnLoadMoreListener onLoadMoreListener, Context context) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView) {
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    public void addAll(List<Post_Data> lst) {
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Post_Data> lst) {
        itemList.addAll(lst);
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {
          final Post_Data singleItem = (Post_Data) itemList.get(position);
            ((StudentViewHolder) holder).name.setText(singleItem.getFirst_name() + " " + singleItem.getLast_name());
            ((StudentViewHolder) holder).time.setText(getTimeString(singleItem.getTimeInmilisec()));
            ((StudentViewHolder) holder).text.setText(singleItem.getText_message());
            if (singleItem.getSrc_link().length()>5) {
                ((StudentViewHolder) holder).postImage.setVisibility(View.VISIBLE);
                Picasso.with(context).load(singleItem.getSrc_link())
                        .into(((StudentViewHolder) holder).postImage);
            } else {
                ((StudentViewHolder) holder).postImage.setVisibility(View.GONE);
            }
        }
    }

    private String getTimeString(long postTime){
        String time = "";
        long seconds = (System.currentTimeMillis() - postTime)/1000;
        if((seconds/(60*60*24*30))>365){

        }else if(seconds > 518400){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd,  hh:mm a");
            time = simpleDateFormat.format(new Date(postTime)).replace("  "," at ");
        }else{
            if(seconds < 60 ){
                time  = seconds + " seconds ago";
            }else if(seconds <  60 * 60){
                time = (int)(seconds /60)+" min ago";
            }else if(seconds < 86400){
                time = (int) (seconds / (60*60))+"hr ago";
            }else if(seconds < 604800){
                time = (int) (seconds / (60*60*24)) + " days ago";
            }
        }
        return time;
    }
    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            if(itemList.size() != 0){
            itemList.remove(itemList.size() - 1);
            }
            notifyItemRemoved(itemList.size());
        }
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;
        public TextView text;
        public ImageView postImage;

        public StudentViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            time = (TextView) v.findViewById(R.id.time);
            text = (TextView) v.findViewById(R.id.text);
            postImage = (ImageView) v.findViewById(R.id.postimage);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }
}