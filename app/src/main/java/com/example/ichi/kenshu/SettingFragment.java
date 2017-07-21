package com.example.ichi.kenshu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

    private MainActivity parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof MainActivity) {
            parent = (MainActivity) getActivity();
        }

        parent.actionMenu.getItem(0).setVisible(false);//追加:非表示
        parent.actionMenu.getItem(1).setVisible(false);//保存:非表示
        parent.actionMenu.getItem(2).setVisible(false);//戻る:非表示

        Button button = getActivity().findViewById(R.id.button_goToAccount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.moveToAccountActivity();
            }
        });
    }
}
