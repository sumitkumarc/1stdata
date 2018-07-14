package com.first.choice.Adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.first.choice.Activity.CatWiseProActivity;
import com.first.choice.Activity.SubCategoryWiseProductActivity;
import com.first.choice.BuildConfig;
import com.first.choice.Fragment.MainFragment;
import com.first.choice.R;
import com.first.choice.Rest.ApiClient;
import com.first.choice.Rest.ApiInterface;
import com.first.choice.Rest.Datum;
import com.first.choice.Rest.Responce;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.facebook.GraphRequest.TAG;

/**
 * Created by kevalt on 3/15/2018.
 */

public class Catalog_CatAdapter extends RecyclerView.Adapter<View_Holder> {

    private long refid;
    ArrayList<Long> list = new ArrayList<>();
    ArrayList<String> pathlist = new ArrayList<String>();
    List<Datum> dataSet;
    Context activity;
    int height;
    Boolean flag = false;
    ApiInterface apiService;
   public static String descrepstion;
    ProgressDialog pDialog;
    private String[] urlArray = null;
    ClickShare clickShare;
    public ProgressBar progressBar;
    public interface ClickShare
    {
        public void ClickOnShare(int position);
    }

    public void RegisterClickShareClick(ClickShare clickShare)
    {
        this.clickShare=clickShare;
    }

    public Catalog_CatAdapter(List<Datum> items, FragmentActivity activity) {
        dataSet = items;
        this.activity = activity;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        holder.tvName.setText(dataSet.get(position).getCatName());
        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        int dis = dataSet.get(position).getDiscount();
        if (dis == 0) {
            holder.tvDiscount.setVisibility(View.GONE);
        } else {
            holder.tvDiscount.setText("(" + dataSet.get(position).getDiscount() + "%OFF)");
        }
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getTotalDesign() + " Desings"));
        holder.tvprice.setText("Staring Price : ₹ " + String.valueOf(dataSet.get(position).getStartingPrice()));

//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);

        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
                .into(holder.ivImage);
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
              /*  String SUB_Cat = dataSet.get(position).getCatId();
//                String SUB_Cat = dataSet.get(position).getCatId();
                MainFragment fragment = new MainFragment();
                ((MainFragment) fragment).sharedata(SUB_Cat);
             //   activity.sharedata(SUB_Cat);
              //  fragment.myMethod();

                String catimgeurl =  dataSet.get(position).getCatImage();*/


                if(clickShare!=null)
                {
                    clickShare.ClickOnShare(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
       // return 20;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
