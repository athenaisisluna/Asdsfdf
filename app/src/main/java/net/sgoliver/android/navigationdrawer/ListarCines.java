package net.sgoliver.android.navigationdrawer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class ListarCines extends Activity {

    private static final String TAG = ListarCines.class.getSimpleName();
    private static final String url = "https://dl.dropboxusercontent.com/u/27411217/Cines.json";
    private ProgressDialog pDialog;
    private List<Cine> ListaCine = new ArrayList<Cine>();
    ListView listView;
    private AdapterCine adapter;
    private Runnable run;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String CineBungle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buscar_cine);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new AdapterCine(this, ListaCine);
        Bundle bundle = this.getIntent().getExtras();

        if(bundle !=null) {
            CineBungle = bundle.getString("SDCategoriasDetallePelicula");
        }
        else {
            CineBungle = null;
        }
        mSwipeRefreshLayout.setEnabled(false);
        Update_ListView();
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeRefreshLayout.setEnabled(enable);
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //handling swipe refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        //dialogo();
                        Update_ListView();
                        // Adding request to request queue
                    }
                }, 2000);
            }
        });

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView fontAwesomeAndroidIcon = (TextView) findViewById(R.id.Ver);
        fontAwesomeAndroidIcon.setTypeface(fontAwesomeFont);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                TextView twDIdCine = (TextView) view.findViewById(R.id.DIdCine);
                TextView twDURLFoto = (TextView) view.findViewById(R.id.DURLFoto);
                TextView twDNombreCine = (TextView) view.findViewById(R.id.DNombreCine);

                String SDIdCine = twDIdCine.getText().toString();
                String SDURLFoto = twDURLFoto.getText().toString();
                String SDNombreCine = twDNombreCine.getText().toString();
                //Creamos el Intent
                Intent intent =new Intent(ListarCines.this, ListarPeliculas.class);
                Bundle b = new Bundle();
                b.putString("SDIdCine", SDIdCine);
                b.putString("SDURLFoto", SDURLFoto);
                b.putString("SDNombreCine", SDNombreCine);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    private void Update_ListView(){

        run = new Runnable() {
            public void run() {
                //reload content
                ListaCine.clear();
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            }
        };
        runOnUiThread(run);
        JsonArrayRequest CineCola = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Cine DCine = new Cine();

                                DCine.setNombreCine(obj.getString("NombreCine"));
                                DCine.setFotoCine(obj.getString("URLCine"));
                                DCine.setIdCine(obj.getInt("IdCine"));
                                DCine.setURLFoto(obj.getString("URLCine"));

                                ListaCine.add(DCine);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "+Error: " + error.getMessage());
                hidePDialog();

            }
        });
        AppController.getInstance().addToRequestQueue(CineCola);
    }

}