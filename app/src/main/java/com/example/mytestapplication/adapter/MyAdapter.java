package com.example.mytestapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytestapplication.R;
import com.example.mytestapplication.model.MyListData;

import java.util.List;

// Adapter class to set the values to Recyclerview
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyListData> listData;
    private Context context;


    public MyAdapter(Context context ,List<MyListData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final MyListData myListData = listData.get(position);
        holder.title.setText(myListData.getTitle());
        holder.description.setText(myListData.getDescription());
        Glide.with(context).load(myListData.getImageId()).into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {

           // Dialog appears when clicked on Cardview
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_layout);
                TextView textView;
                textView = (TextView)dialog.findViewById(R.id.textview1);
                textView.setText(myListData.getDescription());
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView description;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraint);

        }
    }
}
