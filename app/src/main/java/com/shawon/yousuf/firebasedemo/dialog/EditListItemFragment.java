package com.shawon.yousuf.firebasedemo.dialog;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.shawon.yousuf.firebasedemo.R;
import com.shawon.yousuf.firebasedemo.utils.Constants;

import java.util.HashMap;

/**
 * Created by user on 6/13/2016.
 */
public class EditListItemFragment extends DialogFragment {

    EditText mEditTextListName;
    String mListItemId, itemName;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static EditListItemFragment newInstance(String name, String itemId) {
        EditListItemFragment editListDialogFragment = new EditListItemFragment();

           Bundle bundle = new Bundle();
        bundle.putString(Constants.FIREBASE_PROPERTY_ITEM_NAME, name);
        bundle.putString(Constants.KEY_LIST_ITEM_ID, itemId);
        editListDialogFragment.setArguments(bundle);

        return editListDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListItemId = getArguments().getString(Constants.KEY_LIST_ITEM_ID);
        itemName = getArguments().getString(Constants.FIREBASE_PROPERTY_ITEM_NAME);

    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_edit_item, null);
        mEditTextListName = (EditText) rootView.findViewById(R.id.edit_text_list_dialog);

        if (itemName != null) {
            mEditTextListName.setText(itemName);
        }



        /**
         * Call editListItem() when user taps "Done" keyboard action
         */
        mEditTextListName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editListItem();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editListItem();
                    }
                });

        return builder.create();
    }

    /**
     * Edit list
     */
    public void editListItem() {
        String name = mEditTextListName.getText().toString();

        /**
         * If EditText input is not empty
         */
        if (!name.equals("")  ) {


            if( mListItemId != null ) {

                Firebase itemRef = new Firebase(Constants.FIREBASE_URL_DATA_LIST).
                        child(mListItemId);
                    /* Make a Hashmap for the specific properties you are changing */
                HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
                updatedProperties.put(Constants.FIREBASE_PROPERTY_ITEM_NAME, name);

                    /* Add the timestamp for last changed to the updatedProperties Hashmap */
                HashMap<String, Object> changedTimestampMap = new HashMap<>();
                changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    /* Add the updated timestamp */
                updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

                    /* Do the update */
                itemRef.updateChildren(updatedProperties);

            }



        }


            /* Close the dialog fragment */
        EditListItemFragment.this.getDialog().cancel();
    }
}


