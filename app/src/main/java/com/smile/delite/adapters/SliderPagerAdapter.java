package com.smile.delite.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.smile.delite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter
{
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Integer> sliderItemArrayList;

    public SliderPagerAdapter(Activity activity, ArrayList<Integer> sliderItemArrayList) {
        this.activity = activity;
        this.sliderItemArrayList = sliderItemArrayList;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = view.findViewById(R.id.im_slider);

        //final SliderItem sliderItem = sliderItemArrayList.get(position);

        try
        {
            //holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(serviceItem.getBitmap(), 600, 800, false));
            Picasso.with(activity.getApplicationContext())
                    //.load(sliderItemArrayList.get(position))
                    .load(R.drawable.burritochicken)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .error(R.drawable.app_logo)
                    .into(im_slider);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return sliderItemArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
        //sliderItemArrayList.add(view.getId());
    }
}
