package com.example.robert.parksmart.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.HistoryListService;

/**
 * Created by ortiz on 11/1/2017.
 */

public class DeleteHistoryItemDialogFragment extends BaseDialog implements View.OnClickListener{

    public static final String EXTRA_HISTORY_ITEM_ID = "EXTRA_HISTORY_ITEM_ID";
    public static final String EXTRA_BOOLEAN = "EXTRA_BOOLEAN";

    private String mHistoryItemId;
    private boolean mIsLongClicked;

    public static DeleteHistoryItemDialogFragment newInstance(String histoyItemId, boolean isLongClicked){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_HISTORY_ITEM_ID,histoyItemId);
        arguments.putBoolean(EXTRA_BOOLEAN,isLongClicked);

        DeleteHistoryItemDialogFragment dialogFragment = new DeleteHistoryItemDialogFragment();
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistoryItemId = getArguments().getString(EXTRA_HISTORY_ITEM_ID);
        mIsLongClicked = getArguments().getBoolean(EXTRA_BOOLEAN);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_delete_item,null))
                .setPositiveButton("Delete",null)
                .setNegativeButton("Cancel",null)
                .show();

        Button nButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button pButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);


        nButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pButton.setTextColor(getResources().getColor(R.color.colorAccent));

        pButton.setOnClickListener(this);
        return dialog;

    }

    @Override
    public void onClick(View view) {
        if(mIsLongClicked){
            dismiss();
            bus.post(new HistoryListService.DeleteHistoryListRequest(userEmail,mHistoryItemId));
        }
    }
}

