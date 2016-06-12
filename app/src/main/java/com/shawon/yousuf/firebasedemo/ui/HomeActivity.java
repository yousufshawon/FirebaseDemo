package com.shawon.yousuf.firebasedemo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.shawon.yousuf.firebasedemo.R;
import com.shawon.yousuf.firebasedemo.adapter.DataListAdapter;
import com.shawon.yousuf.firebasedemo.dialog.AddListDialogFragment;
import com.shawon.yousuf.firebasedemo.dialog.EditListItemFragment;
import com.shawon.yousuf.firebasedemo.model.DataItem;
import com.shawon.yousuf.firebasedemo.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.list_view)
    ListView listView;

    Firebase dataListRef;
    DataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
                showAddListDialog();

            }

        });



        setProperty();


    }


    private void setProperty(){

         dataListRef = new Firebase(Constants.FIREBASE_URL_DATA_LIST);

         adapter = new DataListAdapter(this, DataItem.class, R.layout.single_active_list_item, dataListRef);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DataItem dataItem = adapter.getItem(position);
                String name = dataItem.getName();
                String itemId = adapter.getRef(position).getKey();

                if (dataItem != null) {

                    showEditListItemNameDialog( name,  itemId);
                }


                return false;
            }
        });

    }


    /**
     * Create an instance of the AddList dialog fragment and show it
     */
    public void showAddListDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddListDialogFragment.newInstance();
        dialog.show(HomeActivity.this.getSupportFragmentManager(), "Add Item");
    }

    public void showEditListItemNameDialog(String itemName, String itemId) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListItemFragment.newInstance( itemName, itemId);

        dialog.show(this.getSupportFragmentManager(), "Edit Item");
    }


}
