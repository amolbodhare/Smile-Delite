package com.smile.delite.activities;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.job.JobInfo;
        import android.app.job.JobScheduler;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Looper;
        import android.provider.Settings;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.adoisstudio.helper.Api;
        import com.adoisstudio.helper.H;
        import com.adoisstudio.helper.LoadingDialog;
        import com.adoisstudio.helper.MessageBox;
        import com.adoisstudio.helper.Session;
        import com.facebook.Profile;
        import com.facebook.login.LoginManager;
        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.common.api.ResolvableApiException;
        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationCallback;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationResult;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.LocationSettingsRequest;
        import com.google.android.gms.location.LocationSettingsResponse;
        import com.google.android.gms.location.LocationSettingsStatusCodes;
        import com.google.android.gms.location.SettingsClient;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.smile.delite.BuildConfig;
        import com.smile.delite.R;
        import com.smile.delite.commen.App;
        import com.smile.delite.commen.P;
        import com.smile.delite.fragments.AboutAppFragment;
        import com.smile.delite.fragments.AccountFragment;
        import com.smile.delite.fragments.ItemDetailsFragment;
        import com.smile.delite.fragments.SearchFragment;
        import com.smile.delite.fragments.ContactUsFragment;
        import com.smile.delite.fragments.HomeFragment;
        import com.smile.delite.fragments.MyCartFragment;
        import com.smile.delite.other.JobSchedulerHelper;

        import java.io.IOException;
        import java.text.DateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
        import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class HomeActivity extends AppCompatActivity implements Api.OnLoadingListener, Api.OnErrorListener {

    Button logoutBtnn;
    Profile profile;
    Session session;
    private DrawerLayout drawerLayout;
    public LinearLayout homeButtonLayout, myAccButtonLayout, searchButtonLayout, myCartBtnLayout;
    private FragmentManager fragmentManager;
    private Fragment tempFragment;
    private long l;
    private ArrayList<String> arrayList = new ArrayList<>();
    private int fragmentId;
    private Context context;
    private HomeFragment homeFragment;
    public MyCartFragment myCartFragment;
    private SearchFragment searchFragment;
    public AccountFragment accountFragment;

    GoogleSignInClient mGoogleSignInClient;

    private AboutAppFragment aboutAppFragment;
    private ContactUsFragment contactUsFragment;
    private LoadingDialog loadingDialog;
    int visitorCount;

    private FusedLocationProviderClient client;
    private Handler handler;
    private Runnable runnable;
    //int PLACE_PICKER_REQUEST=1;
    public  static  final String TAG=HomeActivity.class.getName();
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private SettingsClient mSettingsClient;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context= HomeActivity.this;
        //logoutBtnn=(Button)findViewById(R.id.logoutBtn);
        session=new Session(HomeActivity.this);
        //session.addBool(P.not_first_time,true);
        client= LocationServices.getFusedLocationProviderClient(this);

        drawerLayout = findViewById(R.id.drawerlayout);
        homeButtonLayout = findViewById(R.id.homeButtonLayout);
        myAccButtonLayout = findViewById(R.id.myAccountButtonLayout);
        searchButtonLayout = findViewById(R.id.searchButtonLayout);
        myCartBtnLayout = findViewById(R.id.myCartButtonLayout);
        fragmentManager = getSupportFragmentManager();
        handler = new Handler();

        App.IS_NOT_FIRST_TIME_LOGIN=true;

        //requestPermission();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (App.isInternetAvailable(this)) {
            //getAllInfoApiStoreData();
        } else {
            //retrieveData();
        }

        init();

       final LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();


        homeFragment = HomeFragment.newInstance();
        fragmentLoader(homeFragment, getString(R.string.home));


        String apptype = App.IS_DEV ? "DEV - " : "V - ";
        TextView versionTv=(TextView) findViewById(R.id.versionTv);
        versionTv.setText(apptype);
        ((TextView) findViewById(R.id.emailTv)).setText(session.getString(P.login_email));
        //((TextView) findViewById(R.id.mobileNum)).setText(session.getString(P.mobile));



        String appver = BuildConfig.VERSION_NAME;
        versionTv.append(appver);

        /*((TextView) findViewById(R.id.profileName)).setText(session.getString(P.name));
        ((TextView) findViewById(R.id.profileId)).setText(session.getString(P.email));
        ((TextView) findViewById(R.id.mobileNum)).setText(session.getString(P.mobile));
*/
        //handleSearchQuery();
        //handleReportOption();
        //showVersionOnDrawer();

    }
    public void onDrawerBackClick(View view) {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onDrawerMenuClick(View view) {

        if (view.getId() == R.id.drawerHomeLayout) {
            bottomFourFragmentClick(findViewById(R.id.homeButtonLayout));
        }
        else if (view.getId() == R.id.drawerMyProfileLayout) {
            bottomFourFragmentClick(findViewById(R.id.myAccountButtonLayout));
        }
        else if (view.getId() == R.id.drawerNotificationLayout) {
            bottomFourFragmentClick(findViewById(R.id.searchButtonLayout));

        }
        else if (view.getId() == R.id.drawerSyncNowLayout) {

            //hitUploadReport();
            //startJobScheduler();
        } else if (view.getId() == R.id.drawerAboutApplayout) {
            showAboutAppFragment();

        }else if (view.getId() == R.id.drawerContactLayout) {
            showContactUsFragment();
        } else if (view.getId() == R.id.drawerPrivacyPolicyLayout)
        {
           /* Intent intent = new Intent(this,WebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("url","http://greenboxpromotions.com/privacy-policy.php");
            startActivity(intent);*/

        } else if (view.getId() == R.id.drawerLogoutLayout) {
            showLogOutAlert();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void bottomFourFragmentClick(View view) {

        if (view.getId() == R.id.homeButtonLayout) {
            fragmentLoader(homeFragment, getResources().getString(R.string.home));
        }
        else if (view.getId() == R.id.searchButtonLayout) {
            if (searchFragment == null)
                searchFragment = SearchFragment.newInstance();
            fragmentLoader(searchFragment, getResources().getString(R.string.search));
        }

        else if (view.getId() == R.id.myCartButtonLayout) {
            if (myCartFragment == null)
                myCartFragment = MyCartFragment.newInstance();
            fragmentLoader(myCartFragment, getResources().getString(R.string.cart));
        }

        else if (view.getId() == R.id.myAccountButtonLayout) {
            if (accountFragment == null)
                accountFragment = AccountFragment.newInstance();
            fragmentLoader(accountFragment, getResources().getString(R.string.account));
        }

        setBottomSelection(view);
    }

    private void setBottomSelection(View view) {
        LinearLayout parentLayout = findViewById(R.id.bottomLayout);
        LinearLayout childLayout;
        ImageView imageView;

        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = ((LinearLayout) parentLayout.getChildAt(i));

            imageView = (ImageView) childLayout.getChildAt(0);
            imageView.setColorFilter(getResources().getColor(R.color.icongrey));
        }

        childLayout = (LinearLayout) view;
        imageView = (ImageView) childLayout.getChildAt(0);
        imageView.setColorFilter(getResources().getColor(R.color.drawer_border));
    }

    public void onBack(View view) {
        H.log("onBack", "isCalled");
        tempFragment = null;
        Fragment fragment = fragmentManager.findFragmentById(fragmentId);
        if (fragment instanceof HomeFragment)
            drawerLayout.openDrawer(Gravity.LEFT);
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

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = fragmentManager.findFragmentById(fragmentId);
                changeDesigns(fragment);
                decideBottomSelection(fragment);
            }
        });

    }

 /*   private void decideBottomSelection(Fragment fragment) {
        if (fragment instanceof MyCartFragment)
            setBottomSelection(notiButtonLayout);
        else if (fragment instanceof SearchFragment)
            setBottomSelection(searchButtonLayout);
        else if (fragment instanceof AccountFragment)
            setBottomSelection(profileButtonLayout);
        else
            setBottomSelection(homeButtonLayout);
    }*/

    private void decideBottomSelection(Fragment fragment) {
        if (fragment instanceof MyCartFragment)
            setBottomSelection(myCartBtnLayout);
        else if (fragment instanceof SearchFragment)
            setBottomSelection(searchButtonLayout);
        else if (fragment instanceof AccountFragment)
            setBottomSelection(myAccButtonLayout);
        else
            setBottomSelection(homeButtonLayout);
    }

    @Override
    public void onBackPressed() {
        //
        H.log("backStackEntryCountIs", fragmentManager.getBackStackEntryCount() + "");
        if (homeFragment != null && homeFragment.isVisible()) {
            handleExit();
        } else
            onBack(null);
    }
    public void onSearchIconClick(View view) {

        if ((homeFragment != null && homeFragment.isVisible())) {
            if (searchFragment == null)
                searchFragment = searchFragment.newInstance();
            fragmentLoader(searchFragment, "search");
            setBottomSelection(searchButtonLayout);
        } else {
            App.showPopUpMenu(this, findViewById(R.id.searchAndMenuIcon), new App.PopUpMenuInterface() {
                @Override
                public void callBack() {
                    startJobScheduler();
                }
            });
        }
    }
   /* public void onLocIconClick(View view) {

        PlacePicker.IntentBuilder intentBuilder=new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(intentBuilder.build(HomeActivity.this),PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }*/

    public void showLogOutAlert() {
        MessageBox.showYesNoMessage(this, "alert", "Do you really want to exit?", "yes", "no", new MessageBox.OnYesNoListener() {
            @Override
            public void onYesNo(boolean isYes) {
                if (isYes) {
                    if (visitorCount > 0) {
                        MessageBox.showYesNoMessage(HomeActivity.this, "warning", "You have not synced your data yet. After logging out " +
                                "visitor count will be erased permanently.\nDo you still want to logout?", "No", "Yes", new MessageBox.OnYesNoListener() {
                            @Override
                            public void onYesNo(boolean isYes) {
                                if (!isYes)
                                    eraseAppData();
                            }
                        });
                    } else
                        eraseAppData();
                    LoginManager.getInstance().logOut();//for facebook
                    signOut();//for gmail
                }
            }
        });
    }
    public void fragmentLoader(final Fragment fragment, final String string) {
        if (tempFragment == fragment)
            return;
        tempFragment = fragment;

        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .addToBackStack(string)
                .replace(R.id.frameLayout, fragment)
                .commit();

        arrayList.add(string);
        ((TextView) findViewById(R.id.titleName)).setText(string);

        changeDesigns(fragment);
        fragmentId = fragment.getId();
        Log.e("idIs", fragmentId + "");

        decideBottomSelection(fragment);

        H.log("fragmentLoader", "isCalled");

    }
    public void changeDesigns(Fragment fragment) {

        if (fragment instanceof SearchFragment) {
            H.log("fragmentClassNameIs", fragment.getClass().getName());
            showGreenColorSearchBar(true);
            handleTitleName(false);
        } else
            showGreenColorSearchBar(false);

        if (fragment instanceof HomeFragment) {
            showBackButton(false);
            handleTitleBarRightIcon(true);
            handleTitleName(true);

        } else {
            showBackButton(true);
            handleTitleBarRightIcon(false);
            handleTitleName(false);
        }

        /*if (fragment instanceof EventDescFragment || fragment instanceof HallSelectionFragment)
            showTransparentTitleBar(true);
        else
            showTransparentTitleBar(false);*///rethink

        if (findViewById(R.id.searchViewContainer).getVisibility() == View.VISIBLE)
            ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getResources().getColor(R.color.colorWhite));

        if (fragment instanceof HomeFragment || fragment instanceof MyCartFragment || fragment instanceof SearchFragment || fragment instanceof AccountFragment)
            showBottomFourButton(true);
        else
            showBottomFourButton(false);
    }

    private void showBottomFourButton(boolean bool) {
        if (bool)
            findViewById(R.id.cardView).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.cardView).setVisibility(View.GONE);
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

    @Override
    public void onError() {
        H.showMessage(HomeActivity.this, "An error occurred while calling API.");
    }
    private void handleExit() {
        if (homeFragment.isVisible()) {
            if (System.currentTimeMillis() - l < 700) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            } else {
                H.showMessage(this, "Press again to exit.");
                l = System.currentTimeMillis();
            }
        }
    }

    public void startJobScheduler() {
        H.log("iAm", "Executed");
        if (!App.isInternetAvailable(this)) {
            MessageBox.showOkMessage(this, "warning", "Internet not available please try after some time or ensure your internet connection is on.", new MessageBox.OnOkListener() {
                @Override
                public void onOk() {
                    ComponentName componentName = new ComponentName(HomeActivity.this, JobSchedulerHelper.class);
                    JobInfo info = new JobInfo.Builder(314749, componentName)
                            //.setRequiresCharging(true)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setPersisted(true)
                            //.setPeriodic(321)
                            .build();

                    JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    scheduler.cancelAll();

                    int resultCode = scheduler.schedule(info);
                    if (resultCode == JobScheduler.RESULT_SUCCESS) {
                        Log.e("startJobScheduler", "Job scheduled");
                    } else {
                        Log.e("startJobScheduler", "Job scheduling failed");
                    }
                }
            });
        }
    }
    private void showGreenColorSearchBar(boolean bool) {
        if (bool) {
            ImageView imageView = findViewById(R.id.searchAndMenuIcon);

            findViewById(R.id.linearLayout).setBackground(getDrawable(R.drawable.drawer_gradient));
            findViewById(R.id.searchViewContainer).setVisibility(View.VISIBLE);
            getWindow().getDecorView().setSystemUiVisibility(0);
            getWindow().setStatusBarColor(getResources().getColor(R.color.custom_status_bar_color));
            imageView.setVisibility(View.INVISIBLE);

            imageView = findViewById(R.id.drawerMenu);
            imageView.setColorFilter(getResources().getColor((R.color.colorWhite)));
            imageView.setImageDrawable(getDrawable(R.drawable.ic_back_arrow));
            ((TextView) findViewById(R.id.titleName)).setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            LinearLayout linearLayout = findViewById(R.id.searchViewContainer);
            ImageView imageView = findViewById(R.id.drawerMenu);

            findViewById(R.id.linearLayout).setBackgroundColor(getResources().getColor(R.color.colorWhite));
            linearLayout.setVisibility(View.GONE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));

            imageView.setImageDrawable(getDrawable(R.drawable.ic_drawer_menu));
            imageView.setColorFilter(getResources().getColor(R.color.textgrey));
            ((TextView) findViewById(R.id.titleName)).setTextColor(getResources().getColor(R.color.textgrey));
            findViewById(R.id.searchAndMenuIcon).setVisibility(View.VISIBLE);
            setBottomSelection(homeButtonLayout);
        }
    }
    void showBackButton(boolean bool) {
        if (bool)
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getDrawable(R.drawable.ic_back_arrow));
        else
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getDrawable(R.drawable.ic_drawer_menu));
    }
    private void handleTitleBarRightIcon(boolean bool) {
        ImageView imageView = findViewById(R.id.searchAndMenuIcon);

        if (bool)
            imageView.setImageDrawable(getDrawable(R.drawable.ic_search));
        else
            imageView.setImageDrawable(getDrawable(R.drawable.ic_group_146));
    }
    private void handleTitleName(boolean bool)
    {
        TextView titleTextView = findViewById(R.id.titleName);
        TextView locationTextView = findViewById(R.id.locationTv);

        if (bool)
        {
            titleTextView.setVisibility(View.INVISIBLE);
            locationTextView.setVisibility(View.VISIBLE);
        }

        else
        {
            titleTextView.setVisibility(View.VISIBLE);
            locationTextView.setVisibility(View.INVISIBLE);

        }
    }
    private void eraseAppData() {
        Session session = new Session(context);
        session.clear();
        /*db.deleteAllVisitors();
        App.singleEventJson = new Json();
        App.singleDayJson = new Json();
        App.singleHallJson = new Json();
        App.singleEventHallTimeJson = new Json();
        App.eventScanningUserData = new Json();
        App.barcodeString = "";*/
        //new Session(context).addString(P.usertoken, "");
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

       /* // to update sync icon color
        runnable = new Runnable() {
            @Override
            public void run() {
               // changeSyncIconColor();
                handler.postDelayed(runnable, 500);

                ImageView imageView = findViewById(R.id.syncImageView);
                String s = imageView.getTag().toString();
                if (s.equals("0")) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setTag("1");
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    imageView.setTag("0");
                }

            }
        };
        runnable.run();*/
    }

    @Override
    protected void onStop() {
        super.onStop();

        handler.removeCallbacks(runnable);

    }

    public void showAboutAppFragment()
    {
        aboutAppFragment = AboutAppFragment.newInstance();
        fragmentLoader(aboutAppFragment, getResources().getString(R.string.aoutapp));
    }

    public void showContactUsFragment() {
        contactUsFragment = ContactUsFragment.newInstance();
        fragmentLoader(contactUsFragment, getResources().getString(R.string.contactus));
    }
    public void manuallySyncedData(View view) {
        startJobScheduler();
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                locationNikalo();
                            }
                        },5000);

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        //mRequestingLocationUpdates = false;
                        //Toast.makeText(context, "user cancelled", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /*  public void retrieveData() {

        List<BlOBType> blobList = db.getAllBlobs();

        BlOBType blOBType = blobList.get(0);

        String blobdataString = blOBType.getBlobdatastring();

        try {

            Json masterJson = new Json(blobdataString);

            mEventJsonList.clear();
            Json json = new Json();
            for (int j = 0; j < masterJson.length(); j++) {
                json = masterJson.getJson("" + j);// single event
                H.log("singleEventIs", json.toString());
                mEventJsonList.add(json);
            }

            if (eventlistFragment != null)
                eventlistFragment.setAdapter();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
  /*  public void getAllInfoApiStoreData() {
        final Json json = new Json();

        Session session = new Session(context);

        json.addString(P.usertype, session.getString(P.usertype));
        json.addString(P.usertoken, session.getString(P.usertoken));

        RequestModel requestModel = RequestModel.newRequestModel("getallinfo");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel)
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            json = json.getJson(P.data);

                            if (db.truncateAllBlob() >= 0) {

                                if (db.insertBlob(json) > 0) {
                                    retrieveData();
                                    //H.showMessage(context, "data inserted and retrieved");
                                } else {
                                    H.showMessage(context, "data not inserted to blob");
                                }

                            } else {
                                H.showMessage(context, "Data not deleted from table");
                            }

                        }
                        else
                            H.showMessage(context, json.getString(P.msg));
                        *//*else
                            showSessionAlert();*//*
                    }
                })
                .run("hitgetallinfo");
    }*/
    public void onLocIconClick(View view) {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 32);


    }


    private  void locationNikalo()
    {
        client.getLastLocation().addOnSuccessListener(HomeActivity.this,new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null)
                {
                    Toast.makeText(context,""+location,Toast.LENGTH_SHORT).show();
                    double lat=location.getLatitude();
                    double log=location.getLongitude();
                    //Toast.makeText(context,"Lat:"+lat+"\nLog:"+log,Toast.LENGTH_SHORT).show();



                    String errorMessage = "";
                    ////
                    List<Address> addresses = null;
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(
                                lat,
                                log,
                                // In this sample, get just a single address.
                                1);
                    } catch (IOException ioException) {
                        // Catch network or other I/O problems.
                        errorMessage = "Service not available";
                        Log.e(TAG, errorMessage, ioException);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        // Catch invalid latitude or longitude values.
                        errorMessage ="Invalid lat log used";
                        Log.e(TAG, errorMessage + ". " +
                                "Latitude = " + location.getLatitude() +
                                ", Longitude = " +
                                location.getLongitude(), illegalArgumentException);
                    }

                    // Handle case where no address was found.
                    if (addresses == null || addresses.size()  == 0) {
                        if (errorMessage.isEmpty()) {
                            errorMessage = "no address found";
                            Toast.makeText(context, ""+errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, errorMessage);
                        }
                        //deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
                    } else {
                        Address address = addresses.get(0);
                        ArrayList<String> addressFragments = new ArrayList<String>();

                        // Fetch the address lines using getAddressLine,
                        // join them, and send them to the thread.
                        for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                            addressFragments.add(address.getAddressLine(i));
                        }
                        Log.i(TAG,"Address Found");
                        Toast.makeText(context, ""+addressFragments.toString(), Toast.LENGTH_SHORT).show();
                        ((TextView)findViewById(R.id.locationTv)).setText(addressFragments.toString());
                        //deliverResultToReceiver(Constants.SUCCESS_RESULT,
                            /* TextUtils.join(System.getProperty("line.separator"),
                                      addressFragments));*/
                    }

                }
                else
                {
                    Toast.makeText(context,"null loc"+location,Toast.LENGTH_SHORT).show();
                }

            }

        });

        client.getLastLocation().addOnFailureListener(HomeActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==32)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED)
            {

                mSettingsClient
                        .checkLocationSettings(mLocationSettingsRequest)
                        .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                                Log.i(TAG, "All location settings are satisfied.");

                                //Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                                //noinspection MissingPermission
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                        mLocationCallback, Looper.myLooper());

                                //Toast.makeText(HomeActivity.this, "Already on hai", Toast.LENGTH_LONG).show();

                                // updateLocationUI();
                                locationNikalo();
                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                int statusCode = ((ApiException) e).getStatusCode();
                                switch (statusCode) {
                                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                        Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                                "location settings ");
                                        try {
                                            // Show the dialog by calling startResolutionForResult(), and check the
                                            // result in onActivityResult().
                                            ResolvableApiException rae = (ResolvableApiException) e;
                                            rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                                        } catch (IntentSender.SendIntentException sie) {
                                            Log.i(TAG, "PendingIntent unable to execute request.");
                                        }
                                        break;
                                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                        String errorMessage = "Location settings are inadequate, and cannot be " +
                                                "fixed here. Fix in Settings.";
                                        Log.e(TAG, errorMessage);

                                        Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                }

                                //updateLocationUI();
                            }
                        });


                //locationNikalo();
            }
            else
            {
                return;
            }
        }
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                //mCurrentLocation = locationResult.getLastLocation();
                //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                //updateLocationUI();
            }
        };

        //mRequestingLocationUpdates = false;

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

}
