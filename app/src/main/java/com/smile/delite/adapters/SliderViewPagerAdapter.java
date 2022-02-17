package com.smile.delite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.smile.delite.R;

public class SliderViewPagerAdapter extends PagerAdapter
{

    Context context;
    LayoutInflater inflater;
    public int images[]={R.drawable.fruit_one,R.drawable.fruit_two,R.drawable.fruit_three};
    public  String title[]={"one","two","three"};
    public  String desc[]={"one desc","two desc","three desc"};

    public SliderViewPagerAdapter(Context context)
    {
        this.context=context;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==(RelativeLayout)object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.walkthrough_slide_item_layout,container,false);
        ImageView imageView=(ImageView) view.findViewById(R.id.img);
        TextView titletv=(TextView)view.findViewById(R.id.titleTv);
        TextView desctv=(TextView)view.findViewById(R.id.descTv);
        imageView.setImageResource(images[position]);
        //titletv.setText(title[position]);
        //desctv.setText(desc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((RelativeLayout)object);
    }
}
