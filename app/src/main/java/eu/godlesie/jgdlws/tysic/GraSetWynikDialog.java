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

import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraSetWynikDialog extends DialogFragment {
    public static final String EXTRA_WYNIK1 = "eu.godlesie.jgdlws.wynik1";
    public static final String EXTRA_WYNIK2 = "eu.godlesie.jgdlws.wynik2";
    public static final String EXTRA_WYNIK3 = "eu.godlesie.jgdlws.wynik3";
    public static final String EXTRA_WYNIK4 = "eu.godlesie.jgdlws.wynik4";
    public static final int REQUEST_WYNIK = 1;


    private EditText mEditTextWynik1;
    private EditText mEditTextWynik2;
    private EditText mEditTextWynik3;
    private EditText mEditTextWynik4;

    private static final String ARGS_UUID = "uuid";
    private static final String ARGS_LP = "gra_lp";

    private UUID mUUID;
    private int lp;
    private TysiacLab mTysiacLab;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_set_wynik,null);
        mUUID = (UUID) getArguments().getSerializable(ARGS_UUID);
        lp = (int) getArguments().getSerializable(ARGS_LP);
        mEditTextWynik1 = view.findViewById(R.id.edit_text_player1);
        mEditTextWynik2 = view.findViewById(R.id.edit_text_player2);
        mEditTextWynik3 = view.findViewById(R.id.edit_text_player3);
        mEditTextWynik4 = view.findViewById(R.id.edit_text_wynik4);

        mTysiacLab = TysiacLab.get(getActivity());
        Rozgrywka rozgrywka = mTysiacLab.getRozgrywka(mUUID);

        mEditTextWynik1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.dialog_set_wynik)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> sendResult())
                .setNeutralButton(android.R.string.cancel,null)
                .create();
    }
    private void sendResult() {
        Gra gra = mTysiacLab.getGra(mUUID,lp);
        int wynik1 = Integer.parseInt(mEditTextWynik1.getText().toString().isEmpty() ? "0" : mEditTextWynik1.getText().toString());
        int wynik2 = Integer.parseInt(mEditTextWynik2.getText().toString().isEmpty() ? "0" : mEditTextWynik2.getText().toString());
        int wynik3 = Integer.parseInt(mEditTextWynik3.getText().toString().isEmpty() ? "0" : mEditTextWynik3.getText().toString());
        int wynik4 = Integer.parseInt(mEditTextWynik4.getText().toString().isEmpty() ? "0" : mEditTextWynik4.getText().toString());
        gra.setWynik1(wynik1);
        gra.setWynik2(wynik2);
        gra.setWynik3(wynik3);
        gra.setWynik4(wynik4);
        mTysiacLab.updateGra(gra);
        if (getTargetFragment() == null) { return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WYNIK1, mEditTextWynik1.getText().equals("") ? "0" : mEditTextWynik1.getText().toString());
        intent.putExtra(EXTRA_WYNIK2, mEditTextWynik2.getText().equals("") ? "0" : mEditTextWynik2.getText().toString());
        intent.putExtra(EXTRA_WYNIK3, mEditTextWynik3.getText().equals("") ? "0" : mEditTextWynik3.getText().toString());
        intent.putExtra(EXTRA_WYNIK4, mEditTextWynik4.getText().equals("") ? "0" : mEditTextWynik4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }

    public static GraSetWynikDialog newInstance(UUID uuid, int graLp) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UUID,uuid);
        args.putSerializable(ARGS_LP,graLp);
        GraSetWynikDialog fragment = new GraSetWynikDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
