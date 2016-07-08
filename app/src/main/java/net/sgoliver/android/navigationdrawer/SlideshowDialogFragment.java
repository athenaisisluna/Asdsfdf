package net.sgoliver.android.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import net.sgoliver.android.navigationdrawer.R;
import net.sgoliver.android.navigationdrawer.Pelicula;


public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Pelicula> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate,lblIdPelicula;
    private int selectedPosition = 0;

    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblIdPelicula = (TextView) v.findViewById(R.id.DIdPelicula);
        lblDate = (TextView) v.findViewById(R.id.date);
        images = (ArrayList<Pelicula>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");
        Log.e(TAG, "Posición: " + selectedPosition);
        Log.e(TAG, "Tamaño de Imagen: " + images.size());
        Log.e(TAG, "ID de Imagen: " + images.get(selectedPosition).getIdPelicula());
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        //Pelicula();
        Toast.makeText(getActivity(), "Long Click...", Toast.LENGTH_SHORT).show();
        setCurrentItem(selectedPosition);
        return v;
    }
    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " de " + images.size());
        Pelicula image = images.get(position);
        System.out.println("intent" + image.getIdPelicula());
        int IdPeli= image.getIdPelicula();
        String strI = String.valueOf(IdPeli);
        lblIdPelicula.setText(strI);
        lblTitle.setText(image.getNombrePelicula());
        lblDate.setText(image.getTimestamp());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            lblIdPelicula = (TextView) view.findViewById(R.id.DIdPelicula);
            Pelicula image = images.get(position);
            Glide.with(getActivity()).load(image.getLarge())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private void Pelicula(){

        int Id = images.get(selectedPosition).getIdPelicula();
        String IDPeli = String.valueOf(Id);
        Intent intent = new Intent(getActivity(), DetallePelicula.class);
        Bundle b = new Bundle();
        b.putString("SDIdPelicula", IDPeli);
        intent.putExtras(b);
        startActivity(intent);
        //return true;
    }



}