package net.sgoliver.android.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment3 extends Fragment {

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment3, container, false);
        Intent intent = new Intent(getActivity(), ListarCines.class);
        startActivity(intent);
        return rootView;
        //return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }
}
