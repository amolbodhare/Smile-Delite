package com.smile.delite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smile.delite.R;
import com.smile.delite.adapters.SliderViewPagerAdapter;

public class WalkThroughActivity extends AppCompatActivity {

    ViewPager sliderViewPager;
    SliderViewPagerAdapter sliderViewPagerAdapter;
    Context context;
    TextView [] dots;
    LinearLayout mDotsLayout;
    int mDotsLength;
    Button skipButton;
    LinearLayout textLinLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context=getApplicationContext();
        mDotsLayout=(LinearLayout) findViewById(R.id.dotsLayout);
        skipButton=(Button)findViewById(R.id.skipBtn);
        textLinLayout=(LinearLayout)findViewById(R.id.textLayout);
        skipButton.setPaintFlags(skipButton.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sliderViewPager=(ViewPager)findViewById(R.id.siderViewPager);
        sliderViewPagerAdapter=new SliderViewPagerAdapter(this);
        sliderViewPager.setAdapter(sliderViewPagerAdapter);
        addDotsIndicator(0);
        sliderViewPager.addOnPageChangeListener(viewListener);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalkThroughActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
    private  void addDotsIndicator(int position)
    {
        dots=new TextView[3];
        mDotsLength=2;
        mDotsLayout.removeAllViews();

        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(getResources().getColor(R.color.colorWhite));
            mDotsLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.colorBlack));
        }

    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            if(i==mDotsLength)
            {
                skipButton.setText("Finish");
                textLinLayout.setVisibility(View.VISIBLE);

            }
            else
            {
                skipButton.setText("SKIP");
                textLinLayout.setVisibility(View.GONE);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
