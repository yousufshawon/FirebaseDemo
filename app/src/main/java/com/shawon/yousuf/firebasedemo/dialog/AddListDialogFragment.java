package com.shawon.yousuf.firebasedemo.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.shawon.yousuf.firebasedemo.model.DataItem;
import com.shawon.yousuf.firebasedemo.utils.Constants;

import java.util.HashMap;

/**
 * Adds a new item
 */
public class AddListDialogFragment extends DialogFragment {

    EditText mEditTextListName;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddListDialogFragment newInstance() {
        AddListDialogFragment addListDialogFragment = new AddListDialogFragment();

        return addListDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.dialog_add_list, null);
        mEditTextListName = (EditText) rootView.findViewById(R.id.edit_text_list_name);

        /**
         * Call addItem() when user taps "Done" keyboard action
         */
        mEditTextListName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addItem();
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
                        addItem();
                    }
                });

        return builder.create();
    }

    /**
     * Add new active list
     */
    public void addItem() {
        String name = mEditTextListName.getText().toString();

        /**
         * If EditText input is not empty
         */
        if (!name.equals("")) {

            /**
             * Create Firebase references
             */
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_DATA_LIST);

            Firebase newListRef = listsRef.push();

            /* Save listsRef.push() to maintain same random Id */
            final String listId = newListRef.getKey();

            /**
             * Set raw version of date to the ServerValue.TIMESTAMP value and save into
             * timestampCreatedMap
             */
            HashMap<String, Object> timestampCreated = new HashMap<>();
            timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            /* Build the data item */
            DataItem item = new DataItem(name,
                    timestampCreated);

            /* Add the item */
            newListRef.setValue(item);
          //  listsRef.setValue(item);

            /* Close the dialog fragment */
            AddListDialogFragment.this.getDialog().cancel();
        }

    }
}

