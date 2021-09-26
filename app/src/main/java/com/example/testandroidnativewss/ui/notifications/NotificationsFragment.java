package com.example.testandroidnativewss.ui.notifications;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testandroidnativewss.CustomAdapter;
import com.example.testandroidnativewss.DatePickerFragment;
import com.example.testandroidnativewss.R;
import com.example.testandroidnativewss.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private static final int Image_Capture_Code = 1;

    ImageView imgView;
    Button btnSelectImage;
    Button btnCamera;
    Button btnDatePicker;
    static Uri selectedImageUri=null;
    static Bitmap bp=null;

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int [] prgmImages={R.drawable.ic_dashboard_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_notifications_black_24dp,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_notifications_black_24dp,R.drawable.ic_notifications_black_24dp,R.drawable.ic_dashboard_black_24dp};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Button
        btnSelectImage = (Button) root.findViewById(R.id.btnSelectImage);
        btnCamera = (Button) root.findViewById(R.id.btnCamera);
        btnDatePicker = (Button) root.findViewById(R.id.btnDatePicker);

        // Preview Image
        imgView = (ImageView) root.findViewById(R.id.imgView);

        //imgView.setImageURI(imageUri);
        if(selectedImageUri!=null) {
            imgView.setImageURI(selectedImageUri);
        }

        // handle the Choose Image button to trigger
        // the image chooser function
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

       // ListView Start

        context=getActivity();

        lv=(ListView) root.findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(getActivity(), prgmNameList,prgmImages));

        // Listview End
        return root;
    }

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
                // selects the image from the imageChooser
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {

                // compare the resultCode with the
                // SELECT_PICTURE constant
                if (requestCode == SELECT_PICTURE) {
                    // Get the url of the image from data
                    selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // update the preview image in the layout
                        imgView.setImageURI(selectedImageUri);
                    }
                } else if (requestCode == Image_Capture_Code) {
                    bp = (Bitmap) data.getExtras().get("data");
                    imgView.setImageBitmap(bp);
                }
            } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}