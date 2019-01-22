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

import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraAddDialog extends DialogFragment {
    public static final String EXTRA_CONTRACT1 = "eu.godlesie.jgdlws.contract1";
    public static final String EXTRA_CONTRACT2 = "eu.godlesie.jgdlws.contract2";
    public static final String EXTRA_CONTRACT3 = "eu.godlesie.jgdlws.contract3";
    public static final String EXTRA_CONTRACT4 = "eu.godlesie.jgdlws.contract4";

    //private View mView;

    private EditText mEditTextContract1;
    private EditText mEditTextContract2;
    private EditText mEditTextContract3;
    private EditText mEditTextContract4;

    //private UUID mUUID;
    //private TysiacLab mTysiacLab;
    //private Rozgrywka mRozgrywka;

    //TODO - przekaż do dialogu dane uuid z rozgrywki i dobierz ilość zawodników
    //TODO - zablokuj klawisz ok jeśli nic nie podasz.

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_gra,null);
        mEditTextContract1 = view.findViewById(R.id.edit_text_player1);
        mEditTextContract2 = view.findViewById(R.id.edit_text_player2);
        mEditTextContract3 = view.findViewById(R.id.edit_text_player3);
        mEditTextContract4 = view.findViewById(R.id.edit_text_contract4);

        TysiacLab tysiacLab = TysiacLab.get(getActivity());

        UUID uuid = (UUID) getArguments().getSerializable( GraActivity.ARGS_NUM_ROZGRYWKA);
        Rozgrywka rozgrywka = tysiacLab.getRozgrywka(uuid);

        mEditTextContract1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.dialog_add_gra)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> sendResult())
                .setNeutralButton(android.R.string.cancel,null)
                .create();
    }
    private void sendResult() {

        if (getTargetFragment() == null) { return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTRACT1,mEditTextContract1.getText().equals("") ? "0" : mEditTextContract1.getText().toString());
        intent.putExtra(EXTRA_CONTRACT2,mEditTextContract2.getText().equals("") ? "0" : mEditTextContract2.getText().toString());
        intent.putExtra(EXTRA_CONTRACT3,mEditTextContract3.getText().equals("") ? "0" : mEditTextContract3.getText().toString());
        intent.putExtra(EXTRA_CONTRACT4,mEditTextContract4.getText().equals("") ? "0" : mEditTextContract4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }
}
