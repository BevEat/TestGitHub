package com.example.testandroidnativewss.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testandroidnativewss.LatLng;
import com.example.testandroidnativewss.LatLngDao;
import com.example.testandroidnativewss.MainActivity;
import com.example.testandroidnativewss.R;
import com.example.testandroidnativewss.databinding.FragmentHomeBinding;
import com.example.testandroidnativewss.ui.dashboard.DashboardFragment;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    TextView textRetrieve;
    TextView textDelete;
    TextView animateHeight;
    Button btnTall;
    Button btnShort;

    private LatLngDao latlngDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latlngDao = new LatLngDao(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //AnimateHeight Start

        animateHeight = (TextView) root.findViewById(R.id.animateHeight);

        btnTall = (Button) root.findViewById(R.id.btnTall);
        btnTall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                animateHeight.setHeight(150);
            }
        });

        btnShort = (Button) root.findViewById(R.id.btnShort);
        btnShort.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                animateHeight.setHeight(50);
            }
        });

        //AnimateHeight End

        //Set text Lat Long Start
        textRetrieve = (TextView) root.findViewById(R.id.textRetrieve);

        Button btnRetrieve = (Button) root.findViewById(R.id.btnRetrieve);
        btnRetrieve.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Log.d("Retrieve", String.valueOf(latlngDao.getLatLngArray()));
                textRetrieve.setText(String.valueOf(latlngDao.getLatLngArray()));
            }
        });
        //Set text Lat Long End

        //Confirm Delete Dialog Start
        textDelete = (TextView) root.findViewById(R.id.textDelete);

        Button btnDelete = (Button) root.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Delete?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        textDelete.setText("Yes");
                                    }
                                });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDelete.setText("No");
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Confirm Delete Dialog End

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}