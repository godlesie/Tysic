package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_rozgrywka,null);
        //mEditTextPlayer1 = mView.findViewById(R.id.edit_text_contract1);
        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.dialog_add_rozgrywka)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> sendResult())
                .setNeutralButton(android.R.string.cancel,null)
                .create();
    }
    private void sendResult() {
        EditText editTextPlayer1 = mView.findViewById(R.id.edit_text_player1);
        EditText editTextPlayer2 = mView.findViewById(R.id.edit_text_player2);
        EditText editTextPlayer3 = mView.findViewById(R.id.edit_text_player3);
        EditText editTextPlayer4 = mView.findViewById(R.id.edit_text_player4);
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER1,editTextPlayer1.getText().toString());
        intent.putExtra(EXTRA_PLAYER2,editTextPlayer2.getText().toString());
        intent.putExtra(EXTRA_PLAYER3,editTextPlayer3.getText().toString());
        intent.putExtra(EXTRA_PLAYER4,editTextPlayer4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }
}
