package com.closevent.closevent;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import android.content.Context;
import android.os.Bundle;
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
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int selectOnglet;
    public int cpt = 0;
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
    private EventAdapter mAdapter;

    public void getAllEvents(){
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        // TODO: R.Strings ...
        String url = "http://rocky-mesa-88769.herokuapp.com/event";
        //String url = new StringBuilder(R.string.server_base_url).append("event").toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                            JSONArray jArray = jObject.getJSONArray("response");
                            mAdapter.clear();
                            for (int i=0; i < jArray.length(); ++i) {
                                JSONObject ev = jArray.getJSONObject(i);
                                String id = ev.getJSONObject("_id").getString("$oid");
                                String name = ev.getString("name");
                                String address = ev.getString("address");
                                String dateDebut = Integer.toString(ev.getJSONObject("start_date").getInt("$date"));
                                String dateFin = Integer.toString(ev.getJSONObject("end_date").getInt("$date"));
                                boolean evPrivate = ev.getBoolean("private");
                                mAdapter.add(new Event(id, name, dateDebut, dateFin, address, evPrivate));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getMyEvents(){
        mAdapter.add(new Event("123", "24 heures de l'insa", "01/05/2016", "03/05/2016", "20 avenue Albert Einstein", false));
        mAdapter.add(new Event("456", "24 heure de l'insa", "01/05/2016", "03/05/2016", "20 avenue Albert Einstein", false));
    }

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

    public void updateEvents() {
        if(selectOnglet==2) {
            getAllEvents();
        }
        else{
            getMyEvents();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new EventAdapter(this.getContext(), new ArrayList<Event>());
        mAdapter.notifyDataSetChanged();
        if (getArguments() != null) {
            selectOnglet = getArguments().getInt(ARG_ONGLET);
        }
        updateEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events, container, false);

        // Set the adapter
        updateEvents();
        mListView = (AbsListView)view.findViewById(R.id.listEvents);

        mListView.setAdapter(mAdapter);

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
