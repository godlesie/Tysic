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

    private TextView mTextViewplayer1,mTextViewplayer2,mTextViewplayer3,mTextViewplayer4;

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

        mTextViewplayer1 = view.findViewById(R.id.text_view_player1);
        mTextViewplayer2 = view.findViewById(R.id.text_view_player2);
        mTextViewplayer3 = view.findViewById(R.id.text_view_player3);
        mTextViewplayer4 = view.findViewById(R.id.text_view_player4);

        TysiacLab tysiacLab = TysiacLab.get(getActivity());

        UUID uuid = (UUID) getArguments().getSerializable( GraActivity.ARGS_NUM_ROZGRYWKA);
        Rozgrywka rozgrywka = tysiacLab.getRozgrywka(uuid);
        int lastGra = tysiacLab.getLastGra(uuid);
        List<Integer> rozdajacy = new ArrayList<>();
        if (!rozgrywka.getPlayer1().isEmpty()) rozdajacy.add(1);
        if (!rozgrywka.getPlayer2().isEmpty()) rozdajacy.add(2);
        if (!rozgrywka.getPlayer3().isEmpty()) rozdajacy.add(0);
        if (!rozgrywka.getPlayer4().isEmpty()) rozdajacy.add(0);
        Collections.rotate(rozdajacy,lastGra);

        mEditTextContract1.setVisibility(rozgrywka.getPlayer1().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract2.setVisibility(rozgrywka.getPlayer2().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        mEditTextContract4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        mEditTextContract1.setText(!rozgrywka.getPlayer1().isEmpty() ? (rozdajacy.get(0) == 2 ? "100" : "") : "");
        mEditTextContract2.setText(!rozgrywka.getPlayer2().isEmpty() ? (rozdajacy.get(1) == 2 ? "100" : "") : "");
        mEditTextContract3.setText(!rozgrywka.getPlayer3().isEmpty() ? (rozdajacy.get(2) == 2 ? "100" : "") : "");
        mEditTextContract4.setText(!rozgrywka.getPlayer4().isEmpty() ? (rozdajacy.get(3) == 2 ? "100" : "") : "");

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
}
