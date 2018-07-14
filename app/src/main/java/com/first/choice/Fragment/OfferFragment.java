package com.first.choice.Fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.first.choice.Adapter.ImageOfferPagerAdapter;
import com.first.choice.Adapter.OfferCatWiseProadapter;
import com.first.choice.BuildConfig;
import com.first.choice.Model.Subcatdatamodel;
import com.first.choice.R;
import com.first.choice.Rest.ApiClient;
import com.first.choice.Rest.ApiInterface;
import com.first.choice.Rest.Datum;
import com.first.choice.Rest.Responce;
import com.first.choice.Rest.ResponceSub;
import com.first.choice.util.AutoScrollViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

public class OfferFragment extends Fragment implements OfferCatWiseProadapter.OfferClickShare {
    RecyclerView recyclerView;
    ApiInterface apiService;
    ImageView ivnotfound;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    String descrepstion;
    ProgressDialog pDialog;
    String USER_ID;
    List<Subcatdatamodel> items;
    CircleIndicator indicator;
    AutoScrollViewPager viewpager;
    int min;
    OfferCatWiseProadapter offerCatWiseProadapter;
    //Share
    private List<Subcatdatamodel> studentList = new ArrayList<Subcatdatamodel>();
    public static RelativeLayout popupshare;
    public static RelativeLayout rlShareImage, rlShareText, rlShareOther;
    public static TextView txtShareImage, txtShareText;
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static long refid;
    public static ArrayList<Long> listt = new ArrayList<>();
    static ArrayList<String> pathlist = new ArrayList<String>();

    private OnFragmentInteractionListener mListener;

    private static int firstVisibleInListview;
    private static int current_page = 1;
    private int ival = 1;
    private int loadLimit = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public OfferFragment() {
        // Required empty public constructor
    }


    public static OfferFragment newInstance(String param1, String param2) {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        viewpager = (AutoScrollViewPager) view.findViewById(R.id.news_slider);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewpager.startAutoScroll();
        viewpager.setInterval(5000);
        viewpager.setCycle(true);
        viewpager.setStopScrollWhenTouch(true);
        ///////share//////////////
        popupshare = view.findViewById(R.id.popupshare);
        rlShareImage = view.findViewById(R.id.rlShareImage);
        rlShareText = view.findViewById(R.id.rlShareText);
        txtShareImage = view.findViewById(R.id.txtShareImage);
        txtShareText = view.findViewById(R.id.txtShareText);
        ///////share over //////////////
        //showSoftwareKeyboard(true);
        SharedPreferences prefs = getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
        final Boolean Login = prefs.getBoolean("LOGIN", false);
        USER_ID = prefs.getString("USER_ID", "0");

        ivnotfound = (ImageView) view.findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);

        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
      //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        firstVisibleInListview = mLayoutManager.findFirstVisibleItemPosition();
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int currentFirstVisible = mLayoutManager.findFirstVisibleItemPosition();
//
//                if(currentFirstVisible > firstVisibleInListview)
//                    // loadMoreData(current_page);
//                    Log.i("RecyclerView scrolled: ", "scroll up!");
//                else
//                    catwisepro(current_page);
//                  //  loadMoreData();
//                // Log.i("RecyclerView scrolled: ", "scroll down!");
//
//                firstVisibleInListview = currentFirstVisible;
//                if (dy > 0) //check for scroll down
//                {
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
//                   //     loading = false;
//
//                        Log.v("...", " Reached Last Item");
//                    //    catwisepro(searchVideos);
//                    }
//
//
//                 }
//
//            }
//        });
        min = viewpager.getCurrentItem();
        catwisepro(current_page);
        fetchSlider();
        return view;
    }

    private void fetchSlider() {
        try {
            Call<Responce> call = apiService.getOfferSlider();

            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {
                    List<Datum> items = response.body().getData();
                    Log.d("", "Number of CAT By Image received: " + items.size());

                    if (response.body().getSuccess() == 1) {
                        ImageOfferPagerAdapter madapter = new ImageOfferPagerAdapter(getActivity(), items);
                        viewpager.setAdapter(madapter);
                        indicator.setViewPager(viewpager);
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    // Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
        }

    }
    public void sharedata(String SUB_Cat) {
        Log.d("DATA","MAINDATASUBCAT" + SUB_Cat);
        Call<Responce> call = apiService.GetcatShareProduct(SUB_Cat);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> items = response.body().getData();
                List<String> imagurl = response.body().getImages();
                if (response.body().getSuccess() == 0) {

                } else {
                    popupshare.setVisibility(View.VISIBLE);
                    rlShareImage.setBackgroundResource(R.drawable.round_share);
                    txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    rlShareText.setBackgroundResource(R.drawable.round_share);
                    txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    Spanned spanned = Html.fromHtml(response.body().getDescription());
                    descrepstion = spanned.toString();
//                            ArrayList<String> dataurl = new ArrayList<>();
//
//                            for (int i = 0; i < imagurl.size(); i++) {
//                                dataurl.add(imagurl.get(i).get());
//                            }

                    if (imagurl.size() > 0) {

                        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        for (int i = 0; i < imagurl.size(); i++) {
                            Uri Download_Uri = Uri.parse(imagurl.get(i));
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("Images Downloading " + "Sample_" + i + ".png");
                            request.setDescription("Downloading " + "Sample_" + i + ".png");
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/1st Choice/" + "/" + "Sample_" + i + ".png");
                            try {
                                refid = downloadManager.enqueue(request);
                                listt.add(refid);
                                pathlist.add(Environment.getExternalStorageDirectory().toString() + "/1st Choice/" + "/" + "Sample_" + i + ".png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        Intent intent1 = new Intent();
                        intent1 = new Intent("android.intent.action.SEND");
                        intent1.setPackage("com.whatsapp");
                        intent1.setType("text/plain");
                        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent1.putExtra(Intent.EXTRA_TEXT, descrepstion);
                        try {
                            getActivity().startActivityForResult(intent1, 2);
                        } catch (Exception e2s) {
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                pDialog.dismiss();
                Log.e(TAG, t.toString());
            }
        });

    }

    private void catwisepro(int current_page) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<ResponceSub> call = apiService.GetOffersubcategory();
        call.enqueue(new Callback<ResponceSub>() {
            @Override
            public void onResponse(Call<ResponceSub> call, retrofit2.Response<ResponceSub> response) {
                items = response.body().getData();
                Log.d("", "Numbe " + items.size());
                if (response.body().getData().size() == 0) {
                    pDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                    ivnotfound.setVisibility(View.VISIBLE);
                } else {
                    pDialog.dismiss();
                    ivnotfound.setVisibility(View.GONE);
                    loadLimit = ival + 10;
//                    for (int i = ival; i <= loadLimit; i++) {
//                        studentList.add(response.body().getData().get(i));
//                        Log.d("DATAsize", "SecondDATASIZE" + studentList.size());
//                        ival++;
//                    }
                 //   recyclerView.setAdapter(new OfferCatWiseProadapter(getActivity(), items));
                    offerCatWiseProadapter = new OfferCatWiseProadapter(getActivity(),items);
                    offerCatWiseProadapter.RegisterofferClickShareClick(OfferFragment.this);
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(offerCatWiseProadapter);
                   // offerCatWiseProadapter.RegisterofferClickShareClick(OfferFragment.this);
                }

            }

            @Override
            public void onFailure(Call<ResponceSub> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                //  imageView.setVisibility(View.GONE);
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


//    @Override
//    public void onDetach() {
//        super.onDetach();
//        showSoftwareKeyboard(false);
//    }
//    protected void showSoftwareKeyboard(boolean showKeyboard){
//        final Activity activity = getActivity();
//        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
//    }
    @Override
    public void OfferClickShare(int position) {
        sharedata(items.get(position).getCatId());
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity().getIntent().removeExtra("android.intent.extra.STREAM");
        if (requestCode == 1) {
            rlShareImage.setBackgroundResource(R.drawable.round_share_completed);
            txtShareImage.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whats_color));
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/1st Choice");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            Toast.makeText(getActivity().getApplicationContext(), "Share Description", Toast.LENGTH_LONG).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Intent intent1 = new Intent();
                    intent1 = new Intent("android.intent.action.SEND");
                    intent1.setPackage("com.whatsapp");
                    intent1.setType("text/plain");
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent1.putExtra(Intent.EXTRA_TEXT, descrepstion);
                    try {
                        getActivity().startActivityForResult(intent1, 2);
                    } catch (Exception e2s) {
                    }

                }
            }, 2000);
            popupshare.setVisibility(View.GONE);

        } else if (requestCode == 2) {

            rlShareText.setBackgroundResource(R.drawable.round_share_completed);
            txtShareText.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whats_color));
            popupshare.setVisibility(View.GONE);
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            listt.remove(referenceId);

            if (listt.isEmpty()) {

                ArrayList<Uri> files_list = new ArrayList<Uri>();
                File files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/1st Choice");
                File[] F = files.listFiles();
                for (int i = 0; i < F.length; i++) {
                    String s = F[i].getName();
                    if (F[i].getName().contains("Sample_")) {
                        Uri uri1 = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", F[i]);
                        files_list.add(uri1);
                    }

                }

                Intent intent1 = new Intent();
                intent1 = new Intent("android.intent.action.SEND");
                intent1.setPackage("com.whatsapp");
                intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent1.setType("image/jpeg");
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.removeExtra("android.intent.extra.STREAM");
                intent1.putParcelableArrayListExtra("android.intent.extra.STREAM", files_list);
                try {
                    startActivityForResult(intent1, 1);
                } catch (Exception e2s) {
                }

            }

        }
    };

    @Override
    public void onResume() {
        getActivity().getIntent().removeExtra("android.intent.extra.STREAM");
        getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(onComplete);
        getActivity().unregisterReceiver(onComplete);
        super.onPause();
    }



}
