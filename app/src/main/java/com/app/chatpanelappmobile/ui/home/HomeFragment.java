package com.app.chatpanelappmobile.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.chatpanelappmobile.R;

public class HomeFragment extends Fragment {
    TextView textView;
    Animation animation;
    private HomeViewModel homeViewModel;
    Context thiscontext;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        thiscontext = container.getContext();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.prueba);

        animation = AnimationUtils.loadAnimation(thiscontext,
                R.anim.together);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(animation);

            }
        });


        globales.toolbar.setTitle("Inicio");
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}