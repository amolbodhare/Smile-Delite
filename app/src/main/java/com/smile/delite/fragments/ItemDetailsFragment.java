package com.smile.delite.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.smile.delite.R;
import com.smile.delite.activities.HomeActivity;
import com.smile.delite.activities.ItemDetailsActivity;
import com.smile.delite.activities.PaymentActivity;


public class ItemDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private Context context;

    // TODO: Rename and change types of parameters
   /* private String mParam1;
    private String mParam2;*/

    //private OnFragmentInteractionListener mListener;

    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ItemDetailsFragment newInstance() {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
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
        view= inflater.inflate(R.layout.fragment_item_details, container, false);

        context=getContext();

        view.findViewById(R.id.order_now_link_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), PaymentActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.add_to_cart_link_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* MyCartFragment searchFragment= new MyCartFragment();
                getActivity().getSupportFragmentManager().
                        beginTransaction()
                        .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit,R.anim.slide_out_left, R.anim.slide_in_right)
                        .replace(R.id.frameLayout, searchFragment, "findThisFragment")
                        .addToBackStack("findThisFragment")
                        .commit();*/


                ((ItemDetailsActivity) context).myCartFragment = MyCartFragment.newInstance();
                ((ItemDetailsActivity) context).fragmentLoader(((ItemDetailsActivity) context).myCartFragment, "Cart");
            }
        });

        return view;
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
        //mListener = null;
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
