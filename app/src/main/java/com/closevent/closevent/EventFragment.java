package com.closevent.closevent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.closevent.closevent.dummy.DummyContent;

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


    private List<Event> genererEvents(){
        List<Event> theEvents = new ArrayList<Event>();
        theEvents.add(new Event("123","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("456","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("789","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("741","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("852","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("963","24 heure de l'insa", "01/05/2016", "03/05/2016", "20 avenue Albert Einstein", false));
        theEvents.add(new Event("159","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("753","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("483","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("267","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        return theEvents;
    }
    private List<Event> genererEvents2(){
        List<Event> theEvents = new ArrayList<Event>();
        theEvents.add(new Event("123","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("456","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        return theEvents;
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ONGLET = "onglet";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int selectOnglet;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

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

        if (getArguments() != null) {
            selectOnglet = getArguments().getInt(ARG_ONGLET);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(selectOnglet==2) {
            List<Event> theEvents = genererEvents();
            // TODO: Change Adapter to display your content
            mAdapter = new EventAdapter(this.getContext(), theEvents);
        }
        else{
            List<Event> theEvents = genererEvents2();
            // TODO: Change Adapter to display your content
            mAdapter = new EventAdapter(this.getContext(), theEvents);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listEvents);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
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
            mListener.onFragmentInteraction(DummyContent.ITEMS1.get(position).id);
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
