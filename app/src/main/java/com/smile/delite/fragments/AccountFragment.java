package com.smile.delite.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.MessageBox;
import com.adoisstudio.helper.Session;
import com.facebook.login.LoginManager;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.smile.delite.R;
import com.smile.delite.activities.HomeActivity;
import com.smile.delite.activities.LoginActivity;
import com.smile.delite.commen.P;


public class AccountFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Context context;
    private Session session;

    int visitorCount;
    GoogleSignInClient mGoogleSignInClient;

    RelativeLayout email_address_link_layout,mobile_number_link_layout,my_address_link_layout;
    ExpandableRelativeLayout email_address_exp_layout,mobile_number_exp_layout,my_address_exp_layout;
    Button logOutBtn;

    View fragmentView;

    //private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        session = new Session(getActivity());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        //loadingDialog = new LoadingDialog(context);
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_account, null, false);

            /*edit_password_tv_btn = fragmentView.findViewById(R.id.edit_password_tv_btn);
            change_password_edt = fragmentView.findViewById(R.id.change_password_edt);*/

            email_address_link_layout = fragmentView.findViewById(R.id.email_address_link_layout);
            mobile_number_link_layout = fragmentView.findViewById(R.id.mobile_number_link_layout);
            my_address_link_layout = fragmentView.findViewById(R.id.my_address_link_layout);

            email_address_exp_layout = fragmentView.findViewById(R.id.email_address_exp_layout);
            mobile_number_exp_layout = fragmentView.findViewById(R.id.mobile_number_exp_layout);
            my_address_exp_layout = fragmentView.findViewById(R.id.my_address_exp_layout);

            logOutBtn = fragmentView.findViewById(R.id.logoutBtn);



            email_address_link_layout.setOnClickListener(this);
            mobile_number_link_layout.setOnClickListener(this);
            my_address_link_layout.setOnClickListener(this);

            email_address_exp_layout.setOnClickListener(this);
            mobile_number_exp_layout.setOnClickListener(this);
            my_address_exp_layout.setOnClickListener(this);

            logOutBtn.setOnClickListener(this);


           /* fragmentView.findViewById(R.id.saveButon).setOnClickListener(this);
            fragmentView.findViewById(R.id.hideButton).setOnClickListener(this);
            fragmentView.findViewById(R.id.deleteButton).setOnClickListener(this);*/


            email_address_exp_layout.collapse();
            mobile_number_exp_layout.collapse();
            my_address_exp_layout.collapse();

            //String string = new Session(context).getString(P.email);
            //((TextView)fragmentView.findViewById(R.id.email_address_exp_link_tv)).setText(string);


           /* change_password_edt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        //this is for backspace
                    }
                    return false;
                }
            });*/

        }
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.email_address_link_layout)
            email_address_exp_layout.toggle();

        if (v.getId() == R.id.mobile_number_link_layout)
            mobile_number_exp_layout.toggle();

        if (v.getId() == R.id.my_address_link_layout)
            my_address_exp_layout.toggle();

        if (v.getId() == R.id.saveoredit_btn)
            my_address_exp_layout.toggle();
        if (v.getId() == R.id.logoutBtn)
           showLogOutAlert();
    }



    public void showLogOutAlert() {
        MessageBox.showYesNoMessage(context, "alert", "Do you really want to exit?", "yes", "no", new MessageBox.OnYesNoListener() {
            @Override
            public void onYesNo(boolean isYes) {
                if (isYes) {
                    if (visitorCount > 0) {
                        MessageBox.showYesNoMessage(context, "warning", "You have not synced your data yet. After logging out " +
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
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ((getActivity())).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        getActivity().finish();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
