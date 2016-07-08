package net.sgoliver.android.navigationdrawer;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import net.sgoliver.android.navigationdrawer.R;
import net.sgoliver.android.navigationdrawer.AppController;



public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Pelicula> PeliculaItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Adapter(Activity activity, List<Pelicula> PeliculaItems) {
        this.activity = activity;
        this.PeliculaItems = PeliculaItems;
    }

    @Override
    public int getCount() {
        return PeliculaItems.size();
    }

    @Override
    public Object getItem(int location) {
        return PeliculaItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.DFotoPelicula);

        TextView NombrePelicula = (TextView) convertView.findViewById(R.id.DNombrePelicula);
        TextView ClasificacionPelicula = (TextView) convertView.findViewById(R.id.DClasificacionPelicula);
        TextView TiempoDuracion = (TextView) convertView.findViewById(R.id.DTiempoDuracion);
        TextView IdPelicula = (TextView) convertView.findViewById(R.id.DIdPelicula);
        TextView CategoriaPelicula = (TextView) convertView.findViewById(R.id.DCategoriaPelicula);
        TextView URLFoto = (TextView) convertView.findViewById(R.id.DURLFoto);

        Pelicula m = PeliculaItems.get(position);
        thumbNail.setImageUrl(m.getFotoPelicula(), imageLoader);
        NombrePelicula.setText(m.getNombrePelicula());
        ClasificacionPelicula.setText("Clasificación: " + String.valueOf(m.getClasificacionPelicula()));
        TiempoDuracion.setText("Duración: " + String.valueOf(m.getTiempoDuracion()) + " min.");
        CategoriaPelicula.setText("Categoria: " + String.valueOf(m.getGeneroPelicula()));
        IdPelicula.setText(String.valueOf(m.getIdPelicula()));

        URLFoto.setText(String.valueOf(m.getURLFoto()));



        return convertView;
    }

}