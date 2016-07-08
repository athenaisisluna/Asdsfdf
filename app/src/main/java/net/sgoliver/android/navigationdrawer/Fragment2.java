package net.sgoliver.android.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment2 extends Fragment {

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment2, container, false);
        View rootView = inflater.inflate(R.layout.buscar_pelicula, container, false);
        Intent intent = new Intent(getActivity(), ToolBar.class);
        startActivity(intent);
        return rootView;
    }
}
