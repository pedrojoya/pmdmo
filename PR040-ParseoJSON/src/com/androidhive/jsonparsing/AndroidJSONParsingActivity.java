package com.androidhive.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	// URL donde se realizarán las peticiones.
	private static String url = "http://api.androidhive.info/contacts/";
	
	// Nombres de los nodos JSON.
	private static final String TAG_CONTACTOS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NOMBRE = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_DIRECCION = "address";
	private static final String TAG_GENERO = "gender";
	private static final String TAG_TELEFONO = "phone";
	private static final String TAG_TELEFONO_MOVIL = "mobile";
	private static final String TAG_TELEFONO_CASA = "home";
	private static final String TAG_TELEFONO_OFICINA = "office";

	// contacts JSONArray
	JSONArray contacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			// Getting Array of Contacts
			contacts = json.getJSONArray(TAG_CONTACTOS);
			
			// looping through All Contacts
			for(int i = 0; i < contacts.length(); i++){
				JSONObject c = contacts.getJSONObject(i);
				
				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NOMBRE);
				String email = c.getString(TAG_EMAIL);
				String address = c.getString(TAG_DIRECCION);
				String gender = c.getString(TAG_GENERO);
				
				// Phone number is agin JSON Object
				JSONObject phone = c.getJSONObject(TAG_TELEFONO);
				String mobile = phone.getString(TAG_TELEFONO_MOVIL);
				String home = phone.getString(TAG_TELEFONO_CASA);
				String office = phone.getString(TAG_TELEFONO_OFICINA);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NOMBRE, name);
				map.put(TAG_EMAIL, email);
				map.put(TAG_TELEFONO_MOVIL, mobile);

				// adding HashList to ArrayList
				contactList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_NOMBRE, TAG_EMAIL, TAG_TELEFONO_MOVIL }, new int[] {
						R.id.name, R.id.email, R.id.mobile });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_NOMBRE, name);
				in.putExtra(TAG_EMAIL, cost);
				in.putExtra(TAG_TELEFONO_MOVIL, description);
				startActivity(in);

			}
		});
		
		

	}

}