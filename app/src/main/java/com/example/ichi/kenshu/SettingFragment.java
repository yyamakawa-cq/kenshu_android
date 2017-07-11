package com.example.ichi.kenshu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {
    private MainActivity parent;

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button button = getActivity().findViewById(R.id.button_goToAccount);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                parent.moveToAccountActivity();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        parent = (MainActivity) context;
        super.onAttach(context);
    }
}
