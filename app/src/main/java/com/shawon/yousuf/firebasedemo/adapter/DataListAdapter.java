package com.shawon.yousuf.firebasedemo.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.shawon.yousuf.firebasedemo.R;
import com.shawon.yousuf.firebasedemo.model.DataItem;
import com.shawon.yousuf.firebasedemo.utils.Constants;

import java.util.HashMap;

/**
 * Created by user on 6/13/2016.
 */
public class DataListAdapter extends FirebaseListAdapter<DataItem> {

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public DataListAdapter(Activity activity, Class<DataItem> modelClass, int modelLayout,
                             Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_active_list.xml
     * populateView also handles data changes and updates the listView accordingly
     */
    @Override
    protected void populateView(View view, DataItem list, int position) {

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewListName = (TextView) view.findViewById(R.id.text_view_active_list_item_name);
        ImageButton buttonRemoveItem = (ImageButton) view.findViewById(R.id.button_remove_item);

        /* Set the list name and owner */
        textViewListName.setText(list.getName());

                /* Gets the id of the item to remove */
        final String itemToRemoveId = this.getRef(position).getKey();


        buttonRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity, R.style.CustomTheme_Dialog)
                        .setTitle("Warning")
                        .setMessage("Are you want to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                removeItem(itemToRemoveId);
                                /* Dismiss the dialog */
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                /* Dismiss the dialog */
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });

    }


    private void removeItem(String itemId) {
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        /* Make a map for the removal */
        HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        /* Remove the item by passing null */
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_DATA_LIST + "/" + itemId, null);


        /* Do the update */
        firebaseRef.updateChildren(updatedRemoveItemMap);
    }




}
