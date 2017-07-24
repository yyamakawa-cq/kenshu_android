package com.example.ichi.kenshu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class EditBookFragment extends Fragment {

    private static final String ARGS_POSITION = "args_position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_book, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() instanceof MainActivity) {
            final MainActivity parent = (MainActivity) getActivity();

            parent.actionMenu.getItem(0).setVisible(false);//追加:非表示
            parent.actionMenu.getItem(1).setVisible(true);//保存:表示
            parent.actionMenu.getItem(2).setVisible(true);//戻る:表示

            Button button = getActivity().findViewById(R.id.button_upload);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parent.pickImageView();
                }
            });

            if (parent.imageViewUpload != null) {
                ImageView imageViewUpload = getActivity().findViewById(R.id.imageView_upload);
                imageViewUpload.setImageBitmap(parent.imageViewUpload);
            }

            parent.editTextName = getActivity().findViewById(R.id.editText_name);
            parent.editTextPrice = getActivity().findViewById(R.id.editText_price);
            parent.textViewPurchaseDate = getActivity().findViewById(R.id.textView_purchaseDate);

        }
    }

    public static EditBookFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_POSITION, position);
        EditBookFragment editBookFragment = new EditBookFragment();
        editBookFragment.setArguments(bundle);
        return editBookFragment;
    }
}
