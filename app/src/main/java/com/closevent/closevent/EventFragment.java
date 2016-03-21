package com.closevent.closevent;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.closevent.closevent.dummy.DummyContent;
import com.closevent.closevent.service.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class EventFragment extends Fragment implements AbsListView.OnItemClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ONGLET = "onglet";

    // TODO: Rename and change types of parameters
    public int selectOnglet;
    public int cpt = 0;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;


    private AbsListView mListView;
    public static EventAdapter userEvents;
    public static EventAdapter allEvents;


    // TODO: Rename and change types of parameters
    public static EventFragment newInstance(int onglet) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ONGLET, onglet);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEvents = new EventAdapter(this.getContext(), new ArrayList<Event>());
        allEvents = new EventAdapter(this.getContext(), new ArrayList<Event>());
        if (getArguments() != null) {
            selectOnglet = getArguments().getInt(ARG_ONGLET);
        }
        userEvents.updateUserEvents();
        userEvents.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.container);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        System.out.println("TAB SELECTED");
                        selectOnglet = tab.getPosition();
                        System.out.println(selectOnglet);
                        if( selectOnglet == 0 ) {
                            mListView.invalidateViews();
                            mListView.setAdapter(userEvents);
                            userEvents.updateUserEvents();
                            userEvents.notifyDataSetChanged();
                            mListView.invalidateViews();
                        } else {
                            mListView.invalidateViews();
                            mListView.setAdapter(allEvents);
                            allEvents.updateAllEvents();
                            allEvents.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events, container, false);
        // Set the adapter
        mListView = (AbsListView)view.findViewById(R.id.listEvents);
        mListView.setAdapter(userEvents);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(((Event) mListView.getItemAtPosition(position)).getId());
            System.out.println("onclick");
        }
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        selectOnglet = tabLayout.getSelectedTabPosition();
        System.out.println(selectOnglet);
            if(selectOnglet == 0){
                Intent intent = new Intent(this.getContext(), EventActivity.class);
                startActivity(intent);
                TweetFragment.event = (Event) mListView.getAdapter().getItem(position);
                System.out.println("flux");
            }
            else if(selectOnglet == 1){
                Intent intent = new Intent(this.getContext(), CodeActivity.class);
                startActivity(intent);
                System.out.println("Code");
            }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
