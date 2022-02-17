package com.smile.delite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.smile.delite.R;
import com.smile.delite.fragments.HomeFragment;
import com.smile.delite.fragments.ItemDetailsFragment;
import com.smile.delite.fragments.MyCartFragment;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment tempFragment;
    private Handler handler;
    private Runnable runnable;
    private ItemDetailsFragment itemDetailsFragment;
    public MyCartFragment myCartFragment;
    private ArrayList<String> arrayList = new ArrayList<>();
    private int fragmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        fragmentManager = getSupportFragmentManager();
        handler = new Handler();

        itemDetailsFragment = ItemDetailsFragment.newInstance();
        fragmentLoader(itemDetailsFragment, getString(R.string.ordernow));
    }

    public void fragmentLoader(final Fragment fragment, final String string) {
        if (tempFragment == fragment)
            return;
        tempFragment = fragment;


        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit,R.anim.anim_enter,R.anim.anim_exit)
                .addToBackStack(string)
                .replace(R.id.frameLayout, fragment)
                .commit();

        arrayList.add(string);
        ((TextView) findViewById(R.id.titleName)).setText(string);

        //changeDesigns(fragment);
        fragmentId = fragment.getId();
        Log.e("idIs", fragmentId + "");

        //decideBottomSelection(fragment);

        H.log("fragmentLoader", "isCalled");

    }
    public void onBack(View view) {
        H.log("onBack", "isCalled");
        tempFragment = null;
        Fragment fragment = fragmentManager.findFragmentById(fragmentId);
        if (fragment instanceof ItemDetailsFragment)
            //drawerLayout.openDrawer(Gravity.LEFT);
        {
            finish();
        }
        else {


            fragmentManager.popBackStack();

            try {
                if (arrayList.size() >= 1) {
                    arrayList.remove(arrayList.size() - 1);
                    ((TextView) findViewById(R.id.titleName)).setText(arrayList.get(arrayList.size() - 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


       /* new Handler().post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = fragmentManager.findFragmentById(fragmentId);
                //changeDesigns(fragment);
                //decideBottomSelection(fragment);
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        //
        H.log("backStackEntryCountIs", fragmentManager.getBackStackEntryCount() + "");
        if (itemDetailsFragment != null && itemDetailsFragment.isVisible()) {
            //handleExit();
            finish();
        } else
            onBack(null);
    }
}
