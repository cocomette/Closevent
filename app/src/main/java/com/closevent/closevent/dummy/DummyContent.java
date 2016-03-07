package com.closevent.closevent.dummy;

import android.widget.ListView;

import com.closevent.closevent.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Event> ITEMS1 = new ArrayList<Event>();

    /*ListView mListView;
    private List<Event> genererEvents(){
        List<Event> theEvents = new ArrayList<Event>();
        theEvents.add(new Event("123","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        theEvents.add(new Event("159","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        return theEvents;
    }*/

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Event> ITEM_MAP = new HashMap<String, Event>();

    static {
        // Add 3 sample items.
        addItem(new Event("123","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
        addItem(new Event("159","24 heure de l'insa","01/05/2016","03/05/2016","20 avenue Albert Einstein",false));
    }

    private static void addItem(Event item) {
        ITEMS1.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */

}

