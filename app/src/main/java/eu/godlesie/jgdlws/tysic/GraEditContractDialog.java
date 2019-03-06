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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraEditContractDialog extends DialogFragment {
    public static final String EXTRA_CONTRACT1 = "eu.godlesie.jgdlws.contract1";
    public static final String EXTRA_CONTRACT2 = "eu.godlesie.jgdlws.contract2";
    public static final String EXTRA_CONTRACT3 = "eu.godlesie.jgdlws.contract3";
    public static final String EXTRA_CONTRACT4 = "eu.godlesie.jgdlws.contract4";

    public static final int REQUEST_EDIT_CONTRACT = 2;
    private static final String ARGS_UUID = "uuid";
    private static final String ARGS_LP = "gra_lp";

    //private View mView;

    private EditText mEditTextContract1;
    private EditText mEditTextContract2;
    private EditText mEditTextContract3;
    private EditText mEditTextContract4;

    private TextView mTextViewplayer1,mTextViewplayer2,mTextViewplayer3,mTextViewplayer4;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_gra,null);
        mEditTextContract1 = view.findViewById(R.id.edit_text_player1);
        mEditTextContract2 = view.findViewById(R.id.edit_text_player2);
        mEditTextContract3 = view.findViewById(R.id.edit_text_player3);
        mEditTextContract4 = view.findViewById(R.id.edit_text_contract4);

        mTextViewplayer1 = view.findViewById(R.id.text_view_player1);
        mTextViewplayer2 = view.findViewById(R.id.text_view_player2);
        mTextViewplayer3 = view.findViewById(R.id.text_view_player3);
        mTextViewplayer4 = view.findViewById(R.id.text_view_player4);

        TysiacLab tysiacLab = TysiacLab.get(getActivity());

        UUID uuid = (UUID) getArguments().getSerializable( ARGS_UUID);
        Rozgrywka rozgrywka = tysiacLab.getRozgrywka(uuid);
        int lastGra = tysiacLab.getLastGra(uuid);
        Gra gra = tysiacLab.getGra(uuid,lastGra);
        boolean isWynikNoZero= gra.getWynik1() + gra.getWynik2() + gra.getWynik3() + gra.getWynik4() != 0;

        mEditTextContract1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        mEditTextContract1.setText(!rozgrywka.getPlayer1().isEmpty() ? (gra.getContract1() == 0 ? "" : String.valueOf(gra.getContract1())) : "");;
        mEditTextContract2.setText(!rozgrywka.getPlayer2().isEmpty() ? (gra.getContract2() == 0 ? "" : String.valueOf(gra.getContract2())) : "");;
        mEditTextContract3.setText(!rozgrywka.getPlayer3().isEmpty() ? (gra.getContract3() == 0 ? "" : String.valueOf(gra.getContract3())) : "");;
        mEditTextContract4.setText(!rozgrywka.getPlayer4().isEmpty() ? (gra.getContract4() == 0 ? "" : String.valueOf(gra.getContract4())) : "");;

        mEditTextContract1.addTextChangedListener(new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) { mEditTextContract2.setText(""); mEditTextContract3.setText("");mEditTextContract4.setText(""); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        mEditTextContract2.addTextChangedListener(new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) { mEditTextContract1.setText(""); mEditTextContract3.setText("");mEditTextContract4.setText(""); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        mEditTextContract3.addTextChangedListener(new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) { mEditTextContract1.setText(""); mEditTextContract2.setText("");mEditTextContract4.setText(""); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        mEditTextContract4.addTextChangedListener(new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) { mEditTextContract1.setText(""); mEditTextContract2.setText("");mEditTextContract3.setText(""); }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        mTextViewplayer1.setText(rozgrywka.getPlayer1().isEmpty() ?  "" : rozgrywka.getPlayer1());
        mTextViewplayer2.setText(rozgrywka.getPlayer2().isEmpty() ?  "" : rozgrywka.getPlayer2());
        mTextViewplayer3.setText(rozgrywka.getPlayer3().isEmpty() ?  "" : rozgrywka.getPlayer3());
        mTextViewplayer4.setText(rozgrywka.getPlayer4().isEmpty() ?  "" : rozgrywka.getPlayer4());

        mTextViewplayer1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewplayer2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewplayer3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mTextViewplayer4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

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

    public static GraEditContractDialog newInstance(UUID uuid,int graLP) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UUID,uuid);
        args.putSerializable(ARGS_LP,graLP);
        GraEditContractDialog fragment = new GraEditContractDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
