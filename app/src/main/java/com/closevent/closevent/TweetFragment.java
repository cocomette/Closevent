package com.closevent.closevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.closevent.closevent.service.Event;
import com.closevent.closevent.service.Tweet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Côme on 05/03/2016.
 */
public class TweetFragment extends Fragment implements AbsListView.OnItemClickListener {

    private TweetAdapter org_thread;
    private TweetAdapter main_thread;
    public static Event event;

    private List<Tweet> genererTweets(){
        List<Tweet> tweets = new ArrayList<Tweet>();
        return tweets;
    }
    private List<Tweet> genererTweets2(){
        List<Tweet> tweets = new ArrayList<Tweet>();
        return tweets;
    }

    private TextView editPost;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    // TODO: Rename and change types of parameters
    public static TweetFragment newInstance(int param1) {
        TweetFragment fragment = new TweetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TweetFragment() {
    }
    public void updateMain() {
        Call<Event> req = LoginActivity.api.getEvent(event.id);
        System.out.println("CALL UPDATE");
        final Event e = new Event();
        req.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> req, Response<Event> response) {
                try {
                    if (response.body() != null) {
                        TweetFragment.event.set(response.body());
                        main_thread.updateOrgTweets(event);
                        mListView.setAdapter(main_thread);
                        mListView.invalidateViews();
                        main_thread.notifyDataSetChanged();
                    } else {
                        Log.e("Err", response.errorBody().string());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Event> req, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void updateOrg() {
        Call<Event> req = LoginActivity.api.getEvent(event.id);
        System.out.println("CALL UPDATE");
        final Event e = new Event();
        req.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> req, Response<Event> response) {
                try {
                    if (response.body() != null) {
                        event.set(response.body());
                        org_thread.updateOrgTweets(event);
                        mListView.setAdapter(org_thread);
                        mListView.invalidateViews();
                        org_thread.notifyDataSetChanged();
                    } else {
                        Log.e("Err", response.errorBody().string());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Event> req, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org_thread = new TweetAdapter(this.getContext(), event.org_thread);
        main_thread = new TweetAdapter(this.getContext(), event.main_thread);

        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.container);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        System.out.println("TAB SELECTED");
                        int pos = tab.getPosition();
                        if( pos == 0 ) {
                            updateOrg();
                        } else {
                            updateMain();
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(mParam1==2) {
            View view = inflater.inflate(R.layout.onglet_user, container, false);
            editPost = (TextView) view.findViewById(R.id.editPost);
            editPost.requestFocus();
            // Set the adapter
            mListView = (AbsListView) view.findViewById(R.id.listPost);
            ((AdapterView<ListAdapter>) mListView).setAdapter(org_thread);

            // Set OnItemClickListener so we can be notified on item clicks
            //mListView.setOnItemClickListener(this);
            editPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(),PostActivity.class);
                    startActivity(intent);
                }
            });
            return view;

        }
        else {
            View view = inflater.inflate(R.layout.onglet_admin, container, false);

            // Set the adapter
            mListView = (AbsListView) view.findViewById(R.id.listPost);
            ((AdapterView<ListAdapter>) mListView).setAdapter(main_thread);

            // Set OnItemClickListener so we can be notified on item clicks
            //mListView.setOnItemClickListener(this);
            return view;
        }

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
            //mListener.onFragmentInteraction(DummyContent.ITEMS1.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
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
