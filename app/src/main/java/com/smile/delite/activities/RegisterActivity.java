package com.smile.delite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.smile.delite.R;
import com.smile.delite.commen.P;
import com.smile.delite.commen.RequestModel;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements Api.OnLoadingListener, Api.OnErrorListener
{

    EditText edfname;
    EditText edlname;
    EditText eduname;
    EditText edmob;
    EditText edcpass;
    Button regBtn;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        edfname=(EditText)findViewById(R.id.edFName);
        edlname=(EditText)findViewById(R.id.edLName);
        eduname=(EditText)findViewById(R.id.edUname);
        edmob=(EditText)findViewById(R.id.edMob);
        edcpass=(EditText)findViewById(R.id.edConfPass);
        regBtn=(Button) findViewById(R.id.regBtn);
        loadingDialog = new LoadingDialog(this);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRegisterJson();
            }
        });

    }
     private void makeRegisterJson()
    {
        /*final Json json = new Json();

        String string = ((EditText) findViewById(R.id.edFName)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter first name");
            findViewById(R.id.edFName).requestFocus();
            return;
        }

        //json.addString(P.fname, string);
        json.addString(P.name, string);


        string=((EditText) findViewById(R.id.edLName)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter last name");
            findViewById(R.id.edLName).requestFocus();
            return;
        }
        //json.addString(P.lname, string);



        string=((EditText) findViewById(R.id.edMob)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter mobile number");
            findViewById(R.id.edMob).requestFocus();
            return;
        }
        json.addString(P.mobile, string);


        string=((EditText) findViewById(R.id.edUname)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter username");
            findViewById(R.id.edUname).requestFocus();
            return;
        }
        //json.addString(P.uname, string);
        json.addString(P.email, "mailtobodhare@gmail.com");



        string=((EditText) findViewById(R.id.edPass)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter password");
            findViewById(R.id.edPass).requestFocus();
            return;
        }

        json.addString(P.pass, string);




        string=((EditText) findViewById(R.id.edConfPass)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter confirm password");
            findViewById(R.id.edConfPass).requestFocus();
            return;
        }
        json.addString(P.cpass, string);

        if(((EditText) findViewById(R.id.edPass)).getText().toString().equals(((EditText) findViewById(R.id.edConfPass)).getText().toString()))
        {
            string=((EditText) findViewById(R.id.edPass)).getText().toString();

            *//*if (string.isEmpty()) {
                H.showMessage(this, "Please enter confirm password");
                findViewById(R.id.edConfPass).requestFocus();
                return;
            }*//*
            json.addString(P.pass, string);
            json.addString(P.cpass, string);

        }
        else
        {
            H.showMessage(this, "Re Enter Confirm Password");
            return;

        }*/

        //RequestModel requestModel = RequestModel.newRequestModel("signup");
        //requestModel.addJSON(P.data, json);

        /*Json create_josn=new Json();
        //create_josn.addString("id","2");
        create_josn.addString("first_name","Zahid");
        create_josn.addString("last_name","Ansari");
        create_josn.addString("user_name","zahidansari");
        create_josn.addString("password","123456");
        create_josn.addString("mobile_num","9823480690");*/

       /* Json create_josn_new=new Json();
        create_josn_new.addString("fname","sunil");
        create_josn_new.addString("lname","Chaturvedi");
        create_josn_new.addString("email","sunilchaturvedi@gmail.com");
        create_josn_new.addString("password","1234567");*/

       String mob="9921538998";
        Json mvc_json=new Json();
        /*mvc_json.addString("name","Amol");
        mvc_json.addString("mobile","7045154988");*/

        mvc_json.addString("usr","Administrator");
        mvc_json.addString("pwd","admin");

        /*mvc_json.addString("fname","administrator");
        mvc_json.addString("lname","admin");
        mvc_json.addString("email","administrator");
        mvc_json.addString("password","admin");*/


        //mvc_json.addString("email","mailtobodhare@gmail.com");
        //mvc_json.addString("password","amol123");
        //mvc_json.addString("confirm_password","amol123");

        Api.newApi(this, P.baseUrl).addJson(mvc_json)
        //Api.newApi(this, P.createUrl).addJson(json)
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {
                        Toast.makeText(RegisterActivity.this, ""+json, Toast.LENGTH_SHORT).show();
                        /*if (json.getInt(P.status) == 1)
                        {
                            *//*json = json.getJson(P.data);
                            Session session = new Session(RegisterActivity.this);

                            String str = json.getString(P.fname);
                            if (str != null)
                                session.addString(P.fname, str);*//*

                            *//*str = json.getString(P.usertype);

                            if (str != null)
                                session.addString(P.usertype, str);

                            str = json.getString(P.name);

                            if (str != null)
                                session.addString(P.name, str);

                            str = json.getString(P.email);

                            if (str != null)
                                session.addString(P.email, str);

                            str = json.getString(P.mobile);

                            if (str != null)
                                session.addString(P.mobile, str);
*//*
                            H.showMessage(RegisterActivity.this, "register successfully");
                            Intent i=new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(i);
                        }

                        else
                        {
                            H.showMessage(RegisterActivity.this, "register failed");
                            //H.showMessage(RegisterActivity.this, json.getString(P.msg));
                        }*/

                    }
                })
                .run("signup");
    }

    @Override
    public void onError() {
        H.showMessage(RegisterActivity.this, "An error occurred while Registering.");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

}
