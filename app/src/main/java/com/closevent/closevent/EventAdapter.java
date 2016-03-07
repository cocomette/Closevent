package com.closevent.closevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Côme on 05/03/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, List<Event> theEvents) {
        super(context, 0, theEvents);
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
