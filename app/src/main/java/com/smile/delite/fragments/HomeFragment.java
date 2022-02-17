package com.smile.delite.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.smile.delite.R;
import com.smile.delite.activities.HomeActivity;
import com.smile.delite.activities.ItemDetailsActivity;
import com.smile.delite.adapters.PopularThisWeekAdapter;
import com.smile.delite.adapters.SliderPagerAdapter;
import com.smile.delite.adapters.SpecialOfferAdapter;
import com.smile.delite.other.ExpandableHeightGridView;

import java.util.ArrayList;

public class HomeFragment extends Fragment  implements View.OnTouchListener, View.OnClickListener, AdapterView.OnItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private SliderPagerAdapter sliderPagerAdapter;
    private LinearLayout ll_dots;
    ArrayList<Integer> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    private Handler handler;
    private static String TAG = HomeActivity.class.getSimpleName();
    String[] cities = { "Mumbai", "Pune", "Kolkata", "Banglore", "Chennai"};
    View v;
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    public void onClick(View v) {
    }

    Runnable runnable = new Runnable()
    {
        public void run()
        {
            slider_image_list = new ArrayList<>();
            slider_image_list.add(R.drawable.burritochicken);
            slider_image_list.add(R.drawable.burritochicken);
            slider_image_list.add(R.drawable.burritochicken);
            slider_image_list.add(R.drawable.burritochicken);
            slider_image_list.add(R.drawable.burritochicken);

            if (slider_image_list.size()-1 == page_position)
            {
                page_position = 0;
            }
            else
            {
                page_position++;
            }

            viewPager.setCurrentItem(page_position, true);
            handler.postDelayed(this, 5000);
        }
    };

    private void addBottomDots(int currentPage)
    {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++)
        {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#FFFFFF"));
            ll_dots.addView(dots[i]);
        }
        if (dots.length > 0)
            try
            {
                //dots[currentPage].setTextColor(Color.parseColor("#F47A20"));
                dots[currentPage].setTextColor(getResources().getColor(R.color.green));
            }
            catch (Exception e)
            {
                Log.e("NPE",e.toString());
                e.printStackTrace();
            }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);
        //sliderItemsList=new ArrayList<SliderItem>();
        handler = new Handler();
        ll_dots = v.findViewById(R.id.pagesIndicator);
        viewPager =v. findViewById(R.id.viewPager);

        //setSpinner(v);

        setUpTopScrollBar();
        //setUpSpecialOffersScrollBar();
        //setUpPopularThisWeekScrollBar();

        slider_image_list = new ArrayList<>();

        slider_image_list.add(R.drawable.app_logo);
        slider_image_list.add(R.drawable.app_logo);
        slider_image_list.add(R.drawable.app_logo);
        slider_image_list.add(R.drawable.app_logo);
        slider_image_list.add(R.drawable.app_logo);
        slider_image_list.add(R.drawable.app_logo);

        addBottomDots(0);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list);

        viewPager.setAdapter(sliderPagerAdapter);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                page_position = position;
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //getBtnList();
        getSpecialOfferList();
        getPopularThisWeekList();
        getTopRatedList(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            v.animate().translationX(100).setDuration(200);
            //v.setLayoutParams(pressTrue);

            // return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            //v.setLayoutParams(pressFalse);
            v.animate().translationX(0).setDuration(200);
            // return true;
        }
        return false;
    }
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }
    private void setUpTopScrollBar() {
        LinearLayout linearLayout = v.findViewById(R.id.scrollViewChild);
        //linearLayout.getChildAt(0).setOnClickListener(this);
        TextView textView;
        Button button;
        LinearLayout ll;
        TextView textView1;
        ImageView btnImage;
        CardView scrollCardItem;

        for (int i = 65; i <= 90; i++)
        {
            View view = getLayoutInflater().inflate(R.layout.scroller_item, null, false);
            //textView = view.findViewById(R.id.scrollChar);
            scrollCardItem = view.findViewById(R.id.scrollItem);
            ll=scrollCardItem.findViewById(R.id.ll);
            textView1=scrollCardItem.findViewById(R.id.btn_text);
            btnImage=scrollCardItem.findViewById(R.id.btn_img);
            textView1.setText("Runtime");
            btnImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_user));
            //button.setText("RunTime");
            view.setOnClickListener(this);
            /*GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[] {getResources().getColor(R.color.yellow),
                    getResources().getColor(R.color.white), getResources().getColor(R.color.yellow)});*/
            GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[] {getResources().getColor(R.color.transWhite),getResources().getColor(R.color.yellow)});

            //gradientDrawable.setCornerRadius(5f);
            //gradientDrawable.setGradientRadius(2f);

            //scrollCardItem.setBackground(gradientDrawable);
            gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            gradientDrawable.setGradientRadius(90);
            ll.setBackground(gradientDrawable);

            linearLayout.addView(view);
            //linearLayout.getChildAt(i).setOnClickListener(this);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getActivity(),cities[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getSpecialOfferList()
    {
        Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");


        ////////////
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");

        /////////

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.spec_off_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        SpecialOfferAdapter adapter = new SpecialOfferAdapter(getActivity(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
    private void getPopularThisWeekList()
    {
        Log.d(TAG, "getPopularThisWeekList: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");


        ////////////
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");



        ////////////
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Baked Zucchini Friters");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Baked Zucchini Friters");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Baked Zucchini Friters");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.pop_thisweek_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        PopularThisWeekAdapter adapter = new PopularThisWeekAdapter(getActivity(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
    private  void getTopRatedList(View v)
    {
        ((ExpandableHeightGridView) v.findViewById(R.id.daysListGridView)).setAdapter(new DaysGridViewAdapter());
        ((ExpandableHeightGridView) v.findViewById(R.id.daysListGridView)).setExpanded(true);
    }

    class DaysGridViewAdapter extends BaseAdapter {

        private TextView textView;
        private Json day_json;

        @Override
        public int getCount() {
           /* if (days_jsonList.size() == 0)
                findViewById(R.id.noItemTextView).setVisibility(View.VISIBLE);*/

            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.top_rated_layout, parent, false);

               // day_json = days_jsonList.get(position);

               /* String string = day_json.getString(P.event_date_title);
                if (string != null) {
                    textView = convertView.findViewById(R.id.textView);
                    textView.setText(string);
                }*/

                //  checking is remained for this


                convertView.findViewById(R.id.thumbnail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                     /*   App.singleDayJson = days_jsonList.get(position);
                        H.log("singleDayJsonIs",App.singleDayJson.toString());
                        App.eventScanningUserData = App.singleDayJson.getJson(P.event_scanning_user);
                        int i = App.eventScanningUserData.getInt(P.status);
                        if (i==0)
                        {
                            H.showMessage(context,"You are not allowed to scan for this day.");
                            return;
                        }
                        App.eventScanningUserData = App.eventScanningUserData.getJson(P.data);
                        //H.log("eventScanningUserDataIs", App.eventScanningUserData.toString());


                        Intent intent;
                        if (new Session(context).getString(P.usertype).equalsIgnoreCase("E")) {
                            intent = new Intent(EventDescriptionActivity.this, HallSelectionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            intent = new Intent(EventDescriptionActivity.this, StartScanningActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }*/

                        Intent intent;
                        intent = new Intent(getActivity(), ItemDetailsActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                convertView.findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return convertView;
        }
    }
}
