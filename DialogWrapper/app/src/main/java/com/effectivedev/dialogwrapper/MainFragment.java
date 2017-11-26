package com.effectivedev.dialogwrapper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Dmitriy Korobeinikov on 23.07.2016.
 */
public class MainFragment extends Fragment implements DialogWrapper.DialogProvider {
    private static final int ID_CONFIRMATION_DIALOG = 0;

    private ShoppingCartManager mCartManager;
    private boolean mIsDiscountAvailable = true;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartManager = new ShoppingCartManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button btnHello = (Button) view.findViewById(R.id.btnConfirm);
        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWrapper.show(MainFragment.this, ID_CONFIRMATION_DIALOG);
            }
        });
    }

    private AlertDialog createConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("Are you sure?")
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //logic related to internal fields can be very complicated
                        mCartManager.confirmOrder(mIsDiscountAvailable);
                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public Dialog getDialog(int dialogId) {
        switch (dialogId) {
            case ID_CONFIRMATION_DIALOG:
                return createConfirmationDialog();
            default:
                throw new IllegalArgumentException("Unknown dialog id: " + dialogId);
        }
    }
}
