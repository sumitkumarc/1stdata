package com.first.choice.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by kevalt on 4/27/2018.
 */

public class OfferCatWiseProadapter extends RecyclerView.Adapter<View_Holder> {
    List<Subcatdatamodel> dataSet;
    Context activity;
    ApiInterface apiService;
    Boolean flag = false;
    String name;
    ProgressDialog pDialog;
    OfferClickShare offerClickShare;

    public interface OfferClickShare
    {
        void OfferClickShare(int position);
    }
    public void RegisterofferClickShareClick(OfferClickShare offerClickShare)
    {
        this.offerClickShare=offerClickShare;
    }
    public OfferCatWiseProadapter(FragmentActivity activity, List<Subcatdatamodel> items) {
        dataSet = items;
        this.activity = activity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.tvName.setText(dataSet.get(position).getCatName());
        holder.tvprice.setText("Starting at : ₹ " + String.valueOf(dataSet.get(position).getStartingPrice()));
        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getTotalDesign() + " Desings"));
        int dis = dataSet.get(position).getDiscount();
        if (dis == 0) {
            holder.tvDiscount.setVisibility(View.GONE);
        } else {
            holder.tvDiscount.setText("(" + dataSet.get(position).getDiscount() + "%OFF)");
        }

//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).into(holder.ivImage);
//        URL url = null;
//        try {
//            url = new URL(dataSet.get(position).getCatImage());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.ivImage.setImageBitmap(bmp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Picasso.with(activity).load(dataSet.get(position).getCatImage())
                .placeholder(R.drawable.icon)
                .into(holder.ivImage);
//        Glide.with(activity)
//                .load(dataSet.get(position).getCatImage())
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);


        holder.btVproduct.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(activity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    activity.startActivity(intent);

                    /// Add step all post cat product api call
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(activity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    activity.startActivity(intent);
                }
            }
        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(offerClickShare!=null)
                {
                    offerClickShare.OfferClickShare(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
       return dataSet.size();
       // return 5;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
