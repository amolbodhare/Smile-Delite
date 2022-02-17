package com.smile.delite.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.LoadingDialog;
import com.smile.delite.R;
import com.smile.delite.adapters.CartListAdapter;
import com.smile.delite.commen.App;

public class MyCartFragment extends Fragment {
    private View fragMentView;
    private LoadingDialog loadingDialog;

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static RelativeLayout searchViewLayout;

    CartListAdapter cartListAdapter;
    Context context;


    public MyCartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
  /*  public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
        Bundle args = news Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    public static MyCartFragment newInstance() {
        MyCartFragment fragment = new MyCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragMentView == null) {
            fragMentView = inflater.inflate(R.layout.fragment_my_cart, container, false);
            context = getContext();
            loadingDialog = new LoadingDialog(context);
            H.log("MyProfileFragment", "displayed");

            //myOnClickListener = new MyOnClickListener(getActivity());
            searchViewLayout = fragMentView.findViewById(R.id.searchViewContainer);
            recyclerView = fragMentView.findViewById(R.id.eventListRecyclerView);

            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            cartListAdapter = new CartListAdapter(context);
            recyclerView.setAdapter(cartListAdapter);
            //hitEventListApi();


            //hitEventListApi(searchName);

            //eventListAdapter.notifyDataSetChanged();

            final SwipeRefreshLayout swipeRefreshLayout = fragMentView.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    /*if (App.isInternetAvailable(context))
                        cartItemApi();
                    else
                        H.showMessage(context,"Internet not available");
  */              }
            });
        }

        return fragMentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
