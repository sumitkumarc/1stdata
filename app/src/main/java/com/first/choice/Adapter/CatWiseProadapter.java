package com.first.choice.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.first.choice.Activity.CartActivity;
import com.first.choice.Activity.CatWiseProActivity;
import com.first.choice.Activity.SubCatActivity;
import com.first.choice.Activity.SubCatWiseSubCatActivity;
import com.first.choice.Activity.SubCategoryWiseProductActivity;
import com.first.choice.Model.Subcatdatamodel;
import com.first.choice.R;
import com.first.choice.Rest.ApiClient;
import com.first.choice.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by kevalt on 3/19/2018.
 */

public class CatWiseProadapter extends RecyclerView.Adapter<View_Holder> {

    List<Subcatdatamodel> dataSet;
    Context activity;
    ApiInterface apiService;
    Boolean flag = false;
    String name;
    ProgressDialog pDialog;

    public CatWiseProadapter(FragmentActivity activity, List<Subcatdatamodel> items) {
        dataSet = items;
        this.activity = activity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }


    public CatWiseProadapter(SubCatActivity subCatActivity, List<Subcatdatamodel> items) {
        dataSet = items;
        this.activity = subCatActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bran_pro_list, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.tvcat_name.setText(dataSet.get(position).getCatName());
//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);

        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
                .into(holder.ivImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catid = dataSet.get(position).getCatId();
                String catname = dataSet.get(position).getCatName();
                Intent intent = new Intent(activity, SubCatWiseSubCatActivity.class);
                intent.putExtra("CATID", catid);
                intent.putExtra("CATNAME", catname);
                activity.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
