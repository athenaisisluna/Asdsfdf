package net.sgoliver.android.navigationdrawer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.RatingBar;
import android.widget.TextView.BufferType;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class DetallePelicula extends AppCompatActivity {
    private ImageView targetImage;
    private RatingBar rb_customColor;
    private String urlJsonArry = "https://dl.dropboxusercontent.com/u/27411217/Peliculas.json";
    private static String TAG = DetallePelicula.class.getSimpleName();
    private ProgressDialog pDialog;
    LinearLayout linearMain;
    private String G_IDPelicula;
    private int G_NumPersonas;
    private String G_Duracion;
    private String G_NombrePelicula;
    private String G_Categoria;
    private TextView PrimerCard;
    private URL onLineURL;
    Context context;
    private YouTubePlayerView youTubePlayerView;
    private CollapsingToolbarLayout collapsingToolbar;
    TextView tv;
    TextView categorias;
    Button b;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelicula_detalle);
        rb_customColor = (RatingBar) findViewById(R.id.ratingBar5);

        /*
         * For custom color only using layerdrawable to fill the star colors
         */
        LayerDrawable stars = (LayerDrawable) rb_customColor
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#26ce61"),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(Color.parseColor("#FFFF00"),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(Color.CYAN,
                PorterDuff.Mode.SRC_ATOP); // for empty stars

        tv= (TextView)findViewById(R.id.cardSinopsis);
        Bundle bundle = this.getIntent().getExtras();

        String ID_Pelicula = bundle.getString("SDIdPelicula");
        String NombrePelicula = bundle.getString("SDNombrePelicula");
        String Duracion = bundle.getString("SDTiempoDuracion");
        String CATEGORIA = bundle.getString("SDClasificacionPelicula");

        G_IDPelicula = ID_Pelicula;
        G_NumPersonas = 0;
        G_NombrePelicula = NombrePelicula;
        G_Categoria = CATEGORIA;
        G_Duracion = Duracion;

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(NombrePelicula);

        PrimerCard = (TextView) findViewById(R.id.cardUno);
        //PrimerCard.setText(G_Categoria + "\n\n"+ G_Duracion);
        targetImage = (ImageView)findViewById(R.id.ImagenFondo);
        //String onLineImage = bundle.getString("SDURLFoto");

        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.barraExpandida);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.barraColapse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando detalle de la pelicula");
        pDialog.setCancelable(false);

        makeJsonArrayRequest();
    }

    private void makeJsonArrayRequest() {
        linearMain = (LinearLayout) findViewById(R.id.linearMain);
        final TextView cardSinopsis = (TextView) findViewById(R.id.cardSinopsis);

        final View.OnClickListener onclicklistener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle b = new Bundle();
                String CategoriaDetallePelicula = ((TextView) v).getText().toString();

                b.putString("SDCategoriasDetallePelicula", CategoriaDetallePelicula);
                //Añadimos la información al intent
                Intent intent = new Intent(DetallePelicula.this, ListarPeliculas.class);
                intent.putExtras(b);
                //Iniciamos la nueva actividad
                startActivity(intent);
                Toast.makeText(DetallePelicula.this,((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        };
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject Movies = (JSONObject) response
                                        .get(i);
                                if(Objects.equals(Movies.getString("IdPelicula"),G_IDPelicula)) {

                                    String NombrePelicula =  Movies.getString("NombrePelicula");
                                    collapsingToolbar.setTitle(NombrePelicula);
                                    String Descripcion = Movies.getString("Descripcion");
                                    tv.setText(Descripcion);
                                    String delims = ",";

                                    String GeneroPelicula =  Movies.getString("GeneroPelicula");
                                    String ClasificacionPelicula =  Movies.getString("ClasificacionPelicula");
                                    String FechaEstreno =  Movies.getString("FechaEstreno");
                                    String TiempoDuracion =  Movies.getString("TiempoDuracion");
                                    String[] tokens;
                                    G_NombrePelicula=NombrePelicula;
                                    tokens = GeneroPelicula.split(delims);
                                    float rating = Float.parseFloat(Movies.getString("Ranking"));
                                    rb_customColor.setRating(rating);
                                    PrimerCard.setText("Clasificación: "+ClasificacionPelicula + "\n"
                                            + "Tiempo de duración: "+TiempoDuracion+ "mins."+ "\n"
                                            + "Fecha de Estreno: "+FechaEstreno+ "\n"
                                    );
                                    String URLPelicula =  Movies.getString("URLPelicula");
                                    try {
                                        onLineURL = new URL(URLPelicula);
                                        new MyNetworkTask(targetImage).execute(onLineURL);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }

                                    LinearLayout myLayout = (LinearLayout) findViewById(R.id.TextViewCategoria);
                                    LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
                                    TextView[] pairs=new TextView[tokens.length];

                                    for(int l=0; l<tokens.length; l++){
                                        pairs[l] = new TextView(DetallePelicula.this);
                                        pairs[l].setTextSize(15);
                                        pairs[l].setLayoutParams(lp);
                                        pairs[l].setId(l);
                                        pairs[l].setPaintFlags(pairs[l].getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                        pairs[l].setText(tokens[l]);
                                        pairs[l].setOnClickListener(onclicklistener);
                                        myLayout.addView(pairs[l]);

                                    }
                                    String Video= Movies.getString("YouTube");
                                    PlayerYouTubeFrag myFragment = PlayerYouTubeFrag.newInstance(Video);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_youtube_player, myFragment).commit();
                                    makeTextViewResizable(tv, 3, "Ver más", true);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class MyNetworkTask extends AsyncTask<URL, Void, Bitmap>{

        ImageView tIV;

        public MyNetworkTask(ImageView iv){
            tIV = iv;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

            Bitmap networkBitmap = null;

            URL networkUrl = urls[0]; //Load the first element
            try {
                networkBitmap = BitmapFactory.decodeStream(
                        networkUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return networkBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            tIV.setImageBitmap(result);
        }
    }
        public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

            if (tv.getTag() == null) {
                tv.setTag(tv.getText());
            }
            ViewTreeObserver vto = tv.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {

                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (maxLine == 0) {
                        int lineEndIndex = tv.getLayout().getLineEnd(0);
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore), BufferType.SPANNABLE);
                    } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore), BufferType.SPANNABLE);
                    } else {
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore), BufferType.SPANNABLE);
                    }
                }
            });

        }

        private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                                final int maxLine, final String spanableText, final boolean viewMore) {
            String str = strSpanned.toString();
            SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

            if (str.contains(spanableText)) {
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {

                        if (viewMore) {
                            tv.setLayoutParams(tv.getLayoutParams());
                            tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                            tv.invalidate();
                            makeTextViewResizable(tv, -1, "Ver menos", false);
                        } else {
                            tv.setLayoutParams(tv.getLayoutParams());
                            tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                            tv.invalidate();
                            makeTextViewResizable(tv, 3, "Ver más", true);
                        }

                    }
                }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

            }
            return ssb;

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








}