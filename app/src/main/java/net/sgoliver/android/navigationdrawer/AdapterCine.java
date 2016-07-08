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



public class AdapterCine extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Cine> CineItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public AdapterCine(Activity activity, List<Cine> CineItems) {
        this.activity = activity;
        this.CineItems = CineItems;
    }

    @Override
    public int getCount() {
        return CineItems.size();
    }

    @Override
    public Object getItem(int location) {
        return CineItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_cine, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.DFotoCine);

        TextView NombreCine = (TextView) convertView.findViewById(R.id.DNombreCine);
        TextView IdCine = (TextView) convertView.findViewById(R.id.DIdCine);
        TextView URLFoto = (TextView) convertView.findViewById(R.id.DURLFoto);

        Cine m = CineItems.get(position);
        thumbNail.setImageUrl(m.getFotoCine(), imageLoader);

        NombreCine.setText(m.getNombreCine());
        IdCine.setText(String.valueOf(m.getIdCine()));
        URLFoto.setText(String.valueOf(m.getURLFoto()));
        return convertView;
    }

}