package com.abhilash1in.icare;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by dell on 10/15/2016.
 */

public class DialogFragmentClass extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Accept Doctor's request?");
        builder.setMessage("Your doctor wishes to connect with you.");
        // Sets done button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogPositiveClick(DialogFragmentClass.this);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }

    public interface DoctorRequestListener {
        void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog);
        void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    DoctorRequestListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the DialogListener so we can send events to the host
            mListener = (DoctorRequestListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }


}
