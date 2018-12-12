package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RozgrywkaAddDialog extends DialogFragment {
    public static final String EXTRA_PLAYER1 = "eu.godlesie.jgdlws.player1";
    public static final String EXTRA_PLAYER2 = "eu.godlesie.jgdlws.player2";
    public static final String EXTRA_PLAYER3 = "eu.godlesie.jgdlws.player3";
    public static final String EXTRA_PLAYER4 = "eu.godlesie.jgdlws.player4";

    private View mView;

    private EditText mEditTextPlayer1;
    private EditText mEditTextPlayer2;
    private EditText mEditTextPlayer3;
    private EditText mEditTextPlayer4;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_rozgrywka,null);
        //mEditTextPlayer1 = mView.findViewById(R.id.edit_text_contract1);
        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.dialog_add_rozgrywka)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNeutralButton(android.R.string.cancel,null)
                .create();
    }
    private void sendResult(int resultCode) {
        mEditTextPlayer1 = mView.findViewById(R.id.edit_text_wynik1);
        mEditTextPlayer2 = mView.findViewById(R.id.edit_text_wynik2);
        mEditTextPlayer3 = mView.findViewById(R.id.edit_text_wynik3);
        mEditTextPlayer4 = mView.findViewById(R.id.edit_text_player4);
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER1,mEditTextPlayer1.getText().toString());
        intent.putExtra(EXTRA_PLAYER2,mEditTextPlayer2.getText().toString());
        intent.putExtra(EXTRA_PLAYER3,mEditTextPlayer3.getText().toString());
        intent.putExtra(EXTRA_PLAYER4,mEditTextPlayer4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
