package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
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

    private static final String ARGS_UUID = "uuid";
    private static final String ARGS_LP = "gra_lp";

    private int nieDopisujemy;

    private UUID mUUID;
    private int lp;
    private TysiacLab mTysiacLab;

    private EditText mEditTextWynik1, mEditTextWynik2,mEditTextWynik3,mEditTextWynik4;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TextView mTextViewPlayer1,mTextViewPlayer2,mTextViewPlayer3,mTextViewPlayer4;
        CheckBox mCheckBoxPlayer1,mCheckBoxPlayer2,mCheckBoxPlayer3,mCheckBoxPlayer4;

        nieDopisujemy = Integer.valueOf(Objects.requireNonNull(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("nie_dopisujemy", "0")));

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_set_wynik,null);
        assert getArguments() != null;
        mUUID = (UUID) getArguments().getSerializable(ARGS_UUID);
        lp = (int) getArguments().getSerializable(ARGS_LP);
        mEditTextWynik1 = view.findViewById(R.id.edit_text_player1);
        mEditTextWynik2 = view.findViewById(R.id.edit_text_player2);
        mEditTextWynik3 = view.findViewById(R.id.edit_text_player3);
        mEditTextWynik4 = view.findViewById(R.id.edit_text_player4);

        mTextViewPlayer1 = view.findViewById(R.id.text_view_player1);
        mTextViewPlayer2 = view.findViewById(R.id.text_view_player2);
        mTextViewPlayer3 = view.findViewById(R.id.text_view_player3);
        mTextViewPlayer4 = view.findViewById(R.id.text_view_player4);

        mCheckBoxPlayer1 = view.findViewById(R.id.check_box_player1);
        mCheckBoxPlayer2 = view.findViewById(R.id.check_box_player2);
        mCheckBoxPlayer3 = view.findViewById(R.id.check_box_player3);
        mCheckBoxPlayer4 = view.findViewById(R.id.check_box_player4);

        mTysiacLab = TysiacLab.get(getActivity());
        Rozgrywka rozgrywka = mTysiacLab.getRozgrywka(mUUID);

        Gra gra = mTysiacLab.getGra(mUUID,lp);

        mEditTextWynik1.setEnabled(rozgrywka.getWynik1() - nieDopisujemy >= 0 ? false : gra.getContract1() > 0 ? false : true);
        mEditTextWynik2.setEnabled(rozgrywka.getWynik2() - nieDopisujemy >= 0 ? false : gra.getContract2() > 0 ? false : true);
        mEditTextWynik3.setEnabled(rozgrywka.getWynik3() - nieDopisujemy >= 0 ? false : gra.getContract3() > 0 ? false : true);
        mEditTextWynik4.setEnabled(rozgrywka.getWynik4() - nieDopisujemy >= 0 ? false : gra.getContract4() > 0 ? false : true);

        mEditTextWynik1.setText(gra.getContract1() > 0 ? String.valueOf(gra.getContract1()) : String.valueOf(gra.getWynik1()));
        mEditTextWynik2.setText(gra.getContract2() > 0 ? String.valueOf(gra.getContract2()) : String.valueOf(gra.getWynik2()));
        mEditTextWynik3.setText(gra.getContract3() > 0 ? String.valueOf(gra.getContract3()) : String.valueOf(gra.getWynik3()));
        mEditTextWynik4.setText(gra.getContract4() > 0 ? String.valueOf(gra.getContract4()) : String.valueOf(gra.getWynik4()));

        mEditTextWynik1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextWynik4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        mTextViewPlayer1.setText(rozgrywka.getPlayer1());
        mTextViewPlayer2.setText(rozgrywka.getPlayer2());
        mTextViewPlayer3.setText(rozgrywka.getPlayer3());
        mTextViewPlayer4.setText(rozgrywka.getPlayer4());

        mTextViewPlayer1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewPlayer2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewPlayer3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewPlayer4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        mCheckBoxPlayer1.setVisibility(gra.getContract1() > 0 ? View.VISIBLE : View.INVISIBLE);
        mCheckBoxPlayer2.setVisibility(gra.getContract2() > 0 ? View.VISIBLE : View.INVISIBLE);
        mCheckBoxPlayer3.setVisibility(gra.getContract3() > 0 ? View.VISIBLE : View.INVISIBLE);
        mCheckBoxPlayer4.setVisibility(gra.getContract4() > 0 ? View.VISIBLE : View.INVISIBLE);

        mCheckBoxPlayer1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mEditTextWynik1.setText(isChecked ? String.valueOf(gra.getContract1()) : String.valueOf(-gra.getContract1()));
        });
        mCheckBoxPlayer2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mEditTextWynik2.setText(isChecked ? String.valueOf(gra.getContract2()) : String.valueOf(-gra.getContract2()));
        });
        mCheckBoxPlayer3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mEditTextWynik3.setText(isChecked ? String.valueOf(gra.getContract3()) : String.valueOf(-gra.getContract3()));
        });
        mCheckBoxPlayer4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mEditTextWynik4.setText(isChecked ? String.valueOf(gra.getContract4()) : String.valueOf(-gra.getContract4()));
        });

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
