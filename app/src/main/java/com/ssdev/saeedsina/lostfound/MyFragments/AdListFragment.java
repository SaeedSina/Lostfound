package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.Item;
import com.ssdev.saeedsina.lostfound.MyClasses.ItemAdapter;
import com.ssdev.saeedsina.lostfound.MyClasses.MyApplication;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyClasses.MyQuery;
import com.ssdev.saeedsina.lostfound.MyClasses.Tag;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdListFragment extends Fragment  implements View.OnClickListener {

    private MyApplication mMyApplication;
    private OnMenuItemSelectedListener mListener;
    private MyHelper myHelper;
    private LoadingDialog loadingDialog;
    ArrayList itemsArray;
    ItemAdapter adapter;
    private MyQuery myQuery;
    private RecyclerView recList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mListener = (OnMenuItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMenuItemSelectedListener");
        }
    }

    public AdListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onClick(View view) {
        if(view instanceof ImageButton){
            loadingDialog.show();
            Backendless.Persistence.of(Item.class).remove((Item) view.getTag(), new AsyncCallback<Long>() {
                @Override
                public void handleResponse(Long response) {
                    myHelper.Toast("Item deleted");
                    loadingDialog.hide();
                    prepareData();
                }
                @Override
                public void handleFault(BackendlessFault fault) {
                    myHelper.Toast("Something went wrong");
                    loadingDialog.hide();
                }
            });
        }
        else if(view.getTag()!=null && view.getTag().toString() == "pref"){
            mListener.onMenuItemSelected("pref","adlist",null);
        }
        else{
            mMyApplication.setForDetail((Item) view.getTag());
            mListener.onMenuItemSelected("detail","adlist",(Item) view.getTag());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_ad_list, container, false);
        mMyApplication = (MyApplication)getActivity().getApplication();
        myHelper = new MyHelper(getContext());
        loadingDialog = new LoadingDialog(getContext());
        recList = (RecyclerView) myView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        //LinearLayoutManager llm = new LinearLayoutManager(getContext());
        GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        glm.setSmoothScrollbarEnabled(true);
        recList.setLayoutManager(glm);
        myQuery = mMyApplication.getQuery();
        ((MainActivity) getActivity()).setActionBarTitle("AD List");
        prepareData();
        return myView;
    }

    private void prepareData() {
        loadingDialog.show();
        switch (myQuery.getType()){
            case 0:
                Backendless.Persistence.of( Item.class).find( new AsyncCallback<BackendlessCollection<Item>>(){
                    @Override
                    public void handleResponse( BackendlessCollection<Item> foundItems )
                    {
                        itemsArray = new ArrayList(foundItems.getData());
                        loadingDialog.hide();
                        setAdapter();
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                    }
                });
                break;
            case 1:
                String whereClause = "description LIKE '%"+myQuery.getDesc().toLowerCase()+"%'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                dataQuery.setWhereClause( whereClause );
                Backendless.Persistence.of( Item.class ).find( dataQuery,
                        new AsyncCallback<BackendlessCollection<Item>>(){
                            @Override
                            public void handleResponse( BackendlessCollection<Item> foundItems )
                            {
                                itemsArray = new ArrayList(foundItems.getData());
                                loadingDialog.hide();
                                setAdapter();
                            }
                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                break;
            case 2:
                Backendless.Persistence.of( Item.class).find( new AsyncCallback<BackendlessCollection<Item>>(){
                    @Override
                    public void handleResponse( BackendlessCollection<Item> foundItems )
                    {
                        itemsArray = new ArrayList(0);
                        String[] tags= myQuery.getTags().split(",");
                        for(Item item : foundItems.getData()){
                            for(Tag t: item.getTags()){
                                for(String s:tags){
                                    if(t.getTagText().equals(s)){
                                        itemsArray.add(item);
                                    }
                                }
                            }
                        }
                        loadingDialog.hide();
                        setAdapter();
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                    }
                });
                break;
            case 3:
                String whereClause2 = "distance( "+myQuery.getLocation().getLatitude()+","+ myQuery.getLocation().getLongitude()+", location.latitude, location.longitude ) < km(5)";
                BackendlessDataQuery dataQuery2 = new BackendlessDataQuery( whereClause2 );
                QueryOptions queryOptions = new QueryOptions();
                queryOptions.setRelationsDepth( 1 );
                dataQuery2.setQueryOptions( queryOptions );
                Backendless.Persistence.of( Item.class ).find( dataQuery2,
                        new AsyncCallback<BackendlessCollection<Item>>(){
                            @Override
                            public void handleResponse( BackendlessCollection<Item> foundItems )
                            {
                                itemsArray = new ArrayList(foundItems.getData());
                                loadingDialog.hide();
                                setAdapter();
                            }
                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                break;
        }

    }

    private void setAdapter() {
        adapter = new ItemAdapter(itemsArray, new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item, String type) {
                if (type == "detail") {
                    mMyApplication.setForDetail(item);
                    mListener.onMenuItemSelected("detail", "adlist", null);
                } else {
                    loadingDialog.show();
                    Backendless.Persistence.of(Item.class).remove(item,
                            new AsyncCallback<Long>() {
                                public void handleResponse(Long response) {
                                    myHelper.Toast("Item Removed");
                                    refreshData();
                                    loadingDialog.hide();
                                }

                                public void handleFault(BackendlessFault fault) {
                                    myHelper.Toast("Something went wrong");
                                    refreshData();
                                    loadingDialog.hide();
                                }
                            });
                }
            }
        });
        recList.setAdapter(adapter);
    }

    private void refreshData() {
        //TODO check again for the refresh of recyclerView
        adapter.notifyDataSetChanged();
    }
}
