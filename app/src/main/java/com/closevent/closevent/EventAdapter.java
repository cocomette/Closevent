package com.closevent.closevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.closevent.closevent.service.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Côme on 05/03/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, List<Event> theEvents) {
        super(context, 0, theEvents);
    }
    private List<Event> allEvents = new ArrayList<>();
    private List<Event> userEvents = new ArrayList<>();

    private static void syncEvents(List<Event> ref, List<Event> old) {
        // Clear deleted ones
        for (int i=0; i < old.size(); i++ ) {
            boolean found = false;
            for( Event e_ref:ref ) {
                if (e_ref.id.equals(old.get(i).id)) {
                    found = true;
                    break;
                }
            }
            if( ! found ) {
                old.remove(i);
            }
        }

        // Add new ones
        for( int i=0; i < ref.size(); i++ ) {
            boolean found = false;
            for( Event e_old:old ) {
                if (ref.get(i).id.equals(e_old.id)) {
                    found = true;
                    break;
                }
            }
            if( ! found ) {
                old.add(ref.get(i));
            }
        }
    }

    private void syncEvents(List<Event> ref) {
        // Clear deleted ones
        for (int i=0; i<this.getCount(); i++ ) {
            Event to_remove = this.getItem(i);
            for( Event e_ref:ref ) {
                if (e_ref.id.equals(to_remove.id)) {
                    to_remove = null;
                    break;
                }
            }
            if( to_remove != null ) {
                this.remove(to_remove);
            }
        }

        // Add new ones
        for( int i=0; i < ref.size(); i++ ) {
            boolean found = false;
            for( int j=0; j<this.getCount(); j++ ) {
                if (ref.get(i).id.equals(this.getItem(j).id)) {
                    found = true;
                    break;
                }
            }
            if( ! found ) {
                this.add(ref.get(i));
            }
        }
    }

    public void updateAllEvents() {

        Call<List<Event>> req = LoginActivity.api.getEvents();
        req.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> req, Response<List<Event>> response) {
                try {
                    if( response.body() != null ) {
                        syncEvents(response.body(), allEvents);
                        syncEvents(allEvents);
                    }
                } catch (Exception e) {
                    System.out.println(response.errorBody());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> req, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void updateUserEvents(String user_id) {

        Call<List<Event>> req = LoginActivity.api.getUserEvents(user_id);
        req.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> req, Response<List<Event>> response) {
                try {
                    if (response.body() != null) {
                        syncEvents(response.body(), userEvents);
                        syncEvents(userEvents);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> req, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_event, parent, false);
        }

        EventViewHolder viewHolder = (EventViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new EventViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.eventName);
            viewHolder.address = (TextView) convertView.findViewById(R.id.eventAddress);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Event oneEvent = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(oneEvent.getName());
        viewHolder.address.setText(oneEvent.getAddress());

        return convertView;
    }

    private class EventViewHolder {
        public TextView name;
        public TextView address;
    }
}
