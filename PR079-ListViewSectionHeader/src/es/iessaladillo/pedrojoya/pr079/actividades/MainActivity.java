package es.iessaladillo.pedrojoya.pr079.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr079.R;
import es.iessaladillo.pedrojoya.pr079.listas.EntryAdapter;
import es.iessaladillo.pedrojoya.pr079.listas.EntryItem;
import es.iessaladillo.pedrojoya.pr079.listas.Item;
import es.iessaladillo.pedrojoya.pr079.listas.SectionItem;

public class MainActivity extends Activity implements OnItemClickListener {

    ArrayList<Item> items = new ArrayList<Item>();
    ListView listview = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView_main);

        items.add(new SectionItem("My Friends"));
        items.add(new EntryItem("Abhi Tripathi", "Champpu"));
        items.add(new EntryItem("Sandeep Pal", "Sandy kaliya"));
        items.add(new EntryItem("Amit Verma", "Budhiya"));
        items.add(new EntryItem("Awadhesh Diwaker ", "Dadda"));

        items.add(new SectionItem("Android Version"));
        items.add(new EntryItem("Jelly Bean", "android 4.2"));
        items.add(new EntryItem("IceCream Sandwich", "android 4.0"));
        items.add(new EntryItem("Honey Comb", "android 3.0"));
        items.add(new EntryItem("Ginger Bread ", "android 2.2"));

        items.add(new SectionItem("Android Phones"));
        items.add(new EntryItem("Samsung", "Gallexy"));
        items.add(new EntryItem("Sony Ericson", "Xperia"));
        items.add(new EntryItem("Nokiya", "Lumia"));

        EntryAdapter adapter = new EntryAdapter(this, items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView arg0, View arg1, int position, long arg3) {

        EntryItem item = (EntryItem) items.get(position);
        Toast.makeText(this, "You clicked " + item.title, Toast.LENGTH_SHORT)
                .show();
    }
}
