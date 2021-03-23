package com.app.chatpanelappmobile.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.chatpanelappmobile.R;

import pl.droidsonroids.gif.GifImageView;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    GifImageView construccion;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        construccion = root.findViewById(R.id.construccion);
        construccion.setImageResource(R.drawable.cargandodef);
        globales.toolbar.setTitle("Sección en construcción");
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}