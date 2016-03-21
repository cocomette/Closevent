package com.closevent.closevent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.closevent.closevent.service.Event;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class EventsFragment extends ListFragment {
    int mCurCheckPosition = 0;

    private AbsListView mListView;
    public static EventAdapter userEvents;
    public static EventAdapter allEvents;

    public static Fragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEvents = new EventAdapter(this.getContext(), new ArrayList<Event>());
        allEvents = new EventAdapter(this.getContext(), new ArrayList<Event>());
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
                        if (tab.getPosition() == 0) {
                            mListView.setAdapter(userEvents);
                            userEvents.updateUserEvents();
                            userEvents.notifyDataSetChanged();
                        } else {
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

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showThread(position);
    }

    void showThread(int position) {
        mCurCheckPosition = position;
        Intent intent = new Intent();
        intent.setClass(getActivity(), ThreadActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}