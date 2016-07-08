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

public class ListarPeliculas extends Activity {

    private static final String TAG = ListarPeliculas.class.getSimpleName();
    private static final String url = "https://dl.dropboxusercontent.com/u/27411217/Peliculas.json";
    private ProgressDialog pDialog;
    private List<Pelicula> ListaPelicula = new ArrayList<Pelicula>();
    ListView listView;
    private Adapter adapter;
    private Runnable run;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String CategoriaBungle;
    private String IdCineBungle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buscar_pelicula);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new Adapter(this, ListaPelicula);
        Bundle bundle = this.getIntent().getExtras();

        if(bundle !=null) {
            CategoriaBungle = bundle.getString("SDCategoriasDetallePelicula");
            IdCineBungle = bundle.getString("SDIdCine");
        }
        else {
            CategoriaBungle = null;
            IdCineBungle = null;
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

            TextView twDIdPelicula = (TextView) view.findViewById(R.id.DIdPelicula);
            TextView twDURLFoto = (TextView) view.findViewById(R.id.DURLFoto);
            TextView twDNombrePelicula = (TextView) view.findViewById(R.id.DNombrePelicula);
            TextView twDTiempoDuracion = (TextView) view.findViewById(R.id.DTiempoDuracion);
            TextView twDClasificacionPelicula = (TextView) view.findViewById(R.id.DClasificacionPelicula);

            String SDIdPelicula = twDIdPelicula.getText().toString();
            String SDURLFoto = twDURLFoto.getText().toString();
            String SDNombrePelicula = twDNombrePelicula.getText().toString();
            String SDTiempoDuracion = twDTiempoDuracion.getText().toString();
            String SDClasificacionPelicula = twDClasificacionPelicula.getText().toString();
           //Creamos el Intent
            Intent intent =new Intent(ListarPeliculas.this, DetallePelicula.class);

            Bundle b = new Bundle();
            b.putString("SDIdPelicula", SDIdPelicula);
            b.putString("SDURLFoto", SDURLFoto);
            b.putString("SDNombrePelicula", SDNombrePelicula);
            b.putString("SDTiempoDuracion", SDTiempoDuracion);
            b.putString("SDClasificacionPelicula", SDClasificacionPelicula);
            //Añadimos la información al intent
            intent.putExtras(b);
            //Iniciamos la nueva actividad
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
                ListaPelicula.clear();
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            }
        };
        runOnUiThread(run);
        JsonArrayRequest PeliculaCola = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());
                    hidePDialog();
                    // Parsing json
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Pelicula DPelicula = new Pelicula();
                            String yes = null;
                            DPelicula.setNombrePelicula(obj.getString("NombrePelicula"));
                            DPelicula.setFotoPelicula(obj.getString("URLPelicula"));
                            DPelicula.setClasificacionPelicula(obj.getString("ClasificacionPelicula"));
                            DPelicula.setIdPelicula(obj.getInt("IdPelicula"));
                            DPelicula.setTiempoDuracion(obj.getInt("TiempoDuracion"));
                            DPelicula.setGeneroPelicula(obj.getString("GeneroPelicula"));
                            DPelicula.setURLFoto(obj.getString("URLPelicula"));


                            if (IdCineBungle != null && CategoriaBungle == null) {
                                System.out.println("Solo IdCineBungle");
                                String IdCine= obj.getString("Cines");
                                System.out.println("json" + IdCine);
                                System.out.println("intent" + IdCineBungle);
                                if(IdCine.toLowerCase().contains(IdCineBungle.toLowerCase())){
                                    yes="yes";
                                }
                                else yes="no";
                            }
                            else if (CategoriaBungle != null && IdCineBungle == null) {
                                System.out.println("Solo CategoriaBungle");
                                String Cat= obj.getString("GeneroPelicula");
                                if(Cat.toLowerCase().contains(CategoriaBungle.toLowerCase())){
                                    yes="yes";
                                }
                                else yes="no";
                            }
                            else if (CategoriaBungle == null && IdCineBungle == null) {
                                System.out.println("Ambos Nulos");
                                yes="yes";
                            }
                            System.out.println("Estado " + yes);
                            if (yes=="yes"){
                                ListaPelicula.add(DPelicula);
                                System.out.println("Pelicula Agregada " + DPelicula.getNombrePelicula());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // notifying list adapter about data changes
                    // so that it renders the list view with updated data
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d(TAG, "+Error: " + error.getMessage());
            hidePDialog();
        }
    });
        AppController.getInstance().addToRequestQueue(PeliculaCola);
    }
}