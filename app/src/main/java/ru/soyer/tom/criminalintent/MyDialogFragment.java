package ru.soyer.tom.criminalintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by a1 on 28.07.2018.
 */

public class MyDialogFragment extends DialogFragment {

    private static final String ARG_IMG_PATH = "img.path";

    public static MyDialogFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(ARG_IMG_PATH, path);

        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String path = getArguments().getString(ARG_IMG_PATH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_photo, null);

        ImageView mImageView = v.findViewById(R.id.img_scale);
        mImageView.setImageBitmap(BitmapFactory.decodeFile(path));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Увеличенное изображение")
                .create();
    }

}
