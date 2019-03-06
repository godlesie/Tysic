package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RozgrywkaAddDialog extends DialogFragment {
    public static final String EXTRA_PLAYER1 = "eu.godlesie.jgdlws.player1";
    public static final String EXTRA_PLAYER2 = "eu.godlesie.jgdlws.player2";
    public static final String EXTRA_PLAYER3 = "eu.godlesie.jgdlws.player3";
    public static final String EXTRA_PLAYER4 = "eu.godlesie.jgdlws.player4";

    EditText mEditTextPlayer1,mEditTextPlayer2,mEditTextPlayer3,mEditTextPlayer4;

    private View mView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_rozgrywka,null);
        //mEditTextPlayer1 = mView.findViewById(R.id.edit_text_contract1);
        mEditTextPlayer1 = mView.findViewById(R.id.edit_text_player1);
        mEditTextPlayer2 = mView.findViewById(R.id.edit_text_player2);
        mEditTextPlayer3 = mView.findViewById(R.id.edit_text_player3);
        mEditTextPlayer4 = mView.findViewById(R.id.edit_text_player4);

        mEditTextPlayer1.setEnabled(true);
        mEditTextPlayer2.setEnabled(false);
        mEditTextPlayer3.setEnabled(false);
        mEditTextPlayer4.setEnabled(false);

        mEditTextPlayer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) mEditTextPlayer2.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mEditTextPlayer2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) mEditTextPlayer3.setEnabled(true);
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        mEditTextPlayer3.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) mEditTextPlayer4.setEnabled(true);
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.dialog_add_rozgrywka)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> sendResult())
                .setNeutralButton(android.R.string.cancel,null)
                .create();
    }
    private void sendResult() {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER1,mEditTextPlayer1.getText().toString());
        intent.putExtra(EXTRA_PLAYER2,mEditTextPlayer2.getText().toString());
        intent.putExtra(EXTRA_PLAYER3,mEditTextPlayer3.getText().toString());
        intent.putExtra(EXTRA_PLAYER4,mEditTextPlayer4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }
}
