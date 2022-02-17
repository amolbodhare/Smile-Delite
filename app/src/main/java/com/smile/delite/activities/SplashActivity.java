package com.smile.delite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.adoisstudio.helper.Session;
import com.smile.delite.R;
import com.smile.delite.commen.App;
import com.smile.delite.commen.P;

public class SplashActivity extends AppCompatActivity {

    private  int SPLASH_DISPLAY_LENGTH = 1000;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session=new Session(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 31);
        //startNewActivity();
    }
    private void startNewActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                if(App.IS_NOT_FIRST_TIME_LOGIN)
                {
                    intent=new Intent(SplashActivity.this,LoginActivity.class);
                    finish();
                }
                else
                {
                    intent=new Intent(SplashActivity.this,WalkThroughActivity.class);
                    finish();
                }


              /*String string = session.getString(P.tokenData);
                H.log("tokenIs", string);
                if (!sharableLink.isEmpty()) {
                    intent = new Intent(SplashActivity.this, WebViewActivity.class);
                    intent.putExtra("url", sharableLink);
                } else if (string == null || string.isEmpty())
                    intent = new Intent(SplashActivity.this, WalkThroughActivity.class);
                else if (session.getInt(P.full_register) == 0)
                    intent = new Intent(SplashActivity.this, RegSecondPageActivity.class);
                else
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
*/

                //for deep linking
                /*if (!profileId.isEmpty())
                    intent.putExtra(P.profile_id,profileId);*/

                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==31)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED && grantResults[2]== PackageManager.PERMISSION_GRANTED && grantResults[3]== PackageManager.PERMISSION_GRANTED)
            {
                startNewActivity();

            }
            else
            {
                return;

            }
        }
    }
}
