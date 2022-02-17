package com.smile.delite.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adoisstudio.helper.Session;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.smile.delite.R;
import com.smile.delite.commen.App;
import com.smile.delite.commen.P;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    Button BtncreateAcc,fbBtn;
    static  LoginButton BtnSignUpWithFacebook;
    private CallbackManager callbackManager;
    Session session;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    public  static  final String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session=new Session(this);
        setContentView(R.layout.activity_login);
        BtncreateAcc=(Button)findViewById(R.id.createAccountBtn);
        BtnSignUpWithFacebook=(LoginButton)findViewById(R.id.login_button);
        fbBtn=(Button)findViewById(R.id.fb);
        signInButton=(SignInButton)findViewById(R.id.mailSignUpBtn);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnSignUpWithFacebook.performClick();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

        Log.v("fb_fisrtime",String.valueOf(session.getBool(P.not_first_time)));
        Log.v("fb_login",String.valueOf(session.getBool(P.fb_login)));

        BtncreateAcc.setPaintFlags(BtncreateAcc.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        BtncreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        callbackManager=CallbackManager.Factory.create();

        BtnSignUpWithFacebook.setReadPermissions(Arrays.asList("email","public_profile"));
        //Toast.makeText(this, ""+String .valueOf(session.getBool(P.fb_login)), Toast.LENGTH_SHORT).show();



        checkLoginStatus();
        checkGmailSignInStatus();

       /* if(session.getBool(P.first_time)&& !session.getBool(P.fb_login))
        {
            BtnSignUpWithFacebook.performClick();
        }

        if(!session.getBool(P.first_time)&& !session.getBool(P.fb_login)  )
        {
            //checkLoginStatus();
        }

        if(session.getBool(P.first_time)&&session.getBool(P.fb_login))
        {
            checkLoginStatus();

        }
        if(!session.getBool(P.first_time)&&session.getBool(P.fb_login))
        {
            Toast.makeText(this, "Exceptional Case", Toast.LENGTH_SHORT).show();

        }*/


        BtnSignUpWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.


    }

    AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken==null)
            {
                Toast.makeText(LoginActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loadUserProfile(currentAccessToken);
            }
        }
    };
    private  void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest request=GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name=object.getString("first_name");
                    String last_name=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");
                    String image_url="https://graph.facebook.com/"+id+ "/picture?type=normal";

                    Toast.makeText(LoginActivity.this, ""+first_name+last_name, Toast.LENGTH_SHORT).show();

                    session.addString(P.login_email,email);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters=new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private  void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            session.addString(P.login_email,personEmail);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            Toast.makeText(LoginActivity.this,"sign in successfully",Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this,"sign in failed",Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }
    private  void checkGmailSignInStatus()
    {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // This is check ligin status for google mail
       if(account==null)
       {
           //Toast.makeText(LoginActivity.this,"SignIn failed",Toast.LENGTH_SHORT).show();
       }
       else
       {
           startActivity(new Intent(LoginActivity.this, HomeActivity.class));
           //Toast.makeText(LoginActivity.this,"SignIn Successfully",Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGmailSignInStatus();
    }
}
