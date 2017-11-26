package com.effectivedev.dialogwrapper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Dmitriy Korobeinikov on 23.07.2016.
 */
public class DialogWrapper extends DialogFragment {
    private static final String ARG_DIALOG_ID = "ARG_DIALOG_ID";

    private int mDialogId;

    /**
     * Display dialog fragment.
     * @param invoker  The fragment which will serve as {@link AlertDialog} alert dialog provider
     * @param dialogId The ID of dialog that should be shown
     */
    public static <T extends Fragment & DialogProvider> void show(T invoker, int dialogId) {
        Bundle args = new Bundle();
        args.putInt(ARG_DIALOG_ID, dialogId);
        DialogWrapper dialogWrapper = new DialogWrapper();
        dialogWrapper.setArguments(args);
        dialogWrapper.setTargetFragment(invoker, 0);
        dialogWrapper.show(invoker.getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogId = getArguments().getInt(ARG_DIALOG_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getDialogProvider().getDialog(mDialogId);
    }

    private DialogProvider getDialogProvider() {
        return (DialogProvider) getTargetFragment();
    }

    public interface DialogProvider {
        Dialog getDialog(int dialogId);
    }
}
