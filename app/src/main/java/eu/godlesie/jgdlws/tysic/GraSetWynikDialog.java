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

public class GraSetWynikDialog extends DialogFragment {
    public static final String EXTRA_WYNIK1 = "eu.godlesie.jgdlws.wynik1";
    public static final String EXTRA_WYNIK2 = "eu.godlesie.jgdlws.wynik2";
    public static final String EXTRA_WYNIK3 = "eu.godlesie.jgdlws.wynik3";
    public static final String EXTRA_WYNIK4 = "eu.godlesie.jgdlws.wynik4";

    private View mView;

    private EditText mEditTextWynik1;
    private EditText mEditTextWynik2;
    private EditText mEditTextWynik3;
    private EditText mEditTextWynik4;

    private UUID mUUID;
    private TysiacLab mTysiacLab;
    private Rozgrywka mRozgrywka;

    //TODO - przekaż do dialogu dane uuid z rozgrywki i dobierz ilość zawodników
    //TODO - zablokuj klawisz ok jeśli nic nie podasz.

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_gra,null);
        mEditTextWynik1 = mView.findViewById(R.id.edit_text_wynik1);
        mEditTextWynik2 = mView.findViewById(R.id.edit_text_wynik2);
        mEditTextWynik3 = mView.findViewById(R.id.edit_text_wynik3);
        mEditTextWynik4 = mView.findViewById(R.id.edit_text_contract4);

        mTysiacLab = TysiacLab.get(getActivity());
/*
        mRozgrywka = mTysiacLab.getRozgrywka(mUUID);

        mEditTextWynik1.setVisibility(mRozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik2.setVisibility(mRozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik3.setVisibility(mRozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik4.setVisibility(mRozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);
*/

        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.dialog_add_gra)
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

        if (getTargetFragment() == null) { return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WYNIK1, mEditTextWynik1.getText().equals("") ? "0" : mEditTextWynik1.getText().toString());
        intent.putExtra(EXTRA_WYNIK2, mEditTextWynik2.getText().equals("") ? "0" : mEditTextWynik2.getText().toString());
        intent.putExtra(EXTRA_WYNIK3, mEditTextWynik3.getText().equals("") ? "0" : mEditTextWynik3.getText().toString());
        intent.putExtra(EXTRA_WYNIK4, mEditTextWynik4.getText().equals("") ? "0" : mEditTextWynik4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
