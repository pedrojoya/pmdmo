package com.androidbook.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class UserListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.setListAdapter(getAdapter());
        setInitialView(getListView());
        this.populateUserNameList();
    }

    private void setInitialView(ListView lv) {
        View beginView = this.getEmptyBeginView();
        beginView.setVisibility(View.GONE);
        ((ViewGroup) (lv.getParent())).addView(beginView);
        lv.setEmptyView(beginView);
    }

    private void setErrorView(String errorMessage) {
        TextView emptyView = (TextView) findViewById(R.id.EmptyWordListTextViewId);
        emptyView.setText(errorMessage);
    }

    private ListAdapter getAdapter() {
        String[] listItems = new String[] { "Item 1", "Item 2", "Item 3",
                "Item 4", "Item 5", "Item 6", };

        ArrayAdapter<String> listItemAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        return listItemAdapter;
    }

    private void populateUserNameList() {
        ParseQuery query = ParseUser.getQuery();
        this.turnOnProgressDialog("Going to get users",
                "Patience. Be Right back");
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                turnOffProgressDialog();
                if (e == null) {
                    // The query was successful.
                    successfulQuery(objects);
                } else {
                    // Something went wrong.
                    queryFailure(e);
                }
            }

            @Override
            void internalDone(Object arg0, ParseException arg1) {
                // TODO Auto-generated method stub
                
            }
        });
    }

    private void successfulQuery(List<ParseObject> objects) {
        ArrayList<ParseUserWrapper> userList = new ArrayList<ParseUserWrapper>();
        for (ParseObject po : objects) {
            ParseUser pu = (ParseUser) po;
            ParseUserWrapper puw = new ParseUserWrapper(pu);
            userList.add(puw);
        }

        ArrayAdapter<ParseUserWrapper> listItemAdapter = new ArrayAdapter<ParseUserWrapper>(
                this, android.R.layout.simple_list_item_1, userList);
        this.setListAdapter(listItemAdapter);
    }

    private void queryFailure(ParseException x) {
        this.setErrorView(x.getMessage());
    }

    private View getEmptyBeginView() {
        LayoutInflater lif = LayoutInflater.from(this);
        View v = lif.inflate(R.layout.list_empty_begin_layout, null);
        return v;
    }

    // Utility functions
    private ProgressDialog pd;

    public void turnOnProgressDialog(String title, String message) {
        pd = ProgressDialog.show(this, title, message);
    }

    public void turnOffProgressDialog() {
        pd.cancel();
    }

}// eof-class

class ParseUserWrapper {
    private ParseUser pu = null;

    public ParseUserWrapper(ParseUser p) {
        pu = p;
    }

    @Override
    public String toString() {
        String username = pu.getUsername();
        String email = pu.getEmail();
        return username + "(" + email + ")";
    }
}
