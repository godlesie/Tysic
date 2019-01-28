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

import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class RozgrywkaEditDialog extends DialogFragment {
    public static final String EXTRA_PLAYER1 = "eu.godlesie.jgdlws.player1";
    public static final String EXTRA_PLAYER2 = "eu.godlesie.jgdlws.player2";
    public static final String EXTRA_PLAYER3 = "eu.godlesie.jgdlws.player3";
    public static final String EXTRA_PLAYER4 = "eu.godlesie.jgdlws.player4";
    public static final String EXTRA_UUID = "eu.godlesie.jgdlws.uuid";

    public static final int REQUEST_ROZGRYWKA_EDIT = 1;

    private EditText mEditTextPlayer1,mEditTextPlayer2,mEditTextPlayer3,mEditTextPlayer4;
    private static final String ARGS_UUID = "uuid";

    private UUID mUUID;
    private TysiacLab mTysiacLab;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_rozgrywka,null);
        mUUID = (UUID)getArguments().getSerializable(ARGS_UUID);
        mEditTextPlayer1 = view.findViewById(R.id.edit_text_player1);
        mEditTextPlayer2 = view.findViewById(R.id.edit_text_player2);
        mEditTextPlayer3 = view.findViewById(R.id.edit_text_player3);
        mEditTextPlayer4 = view.findViewById(R.id.edit_text_player4);
        mTysiacLab = TysiacLab.get(getActivity());
        Rozgrywka rozgrywka = mTysiacLab.getRozgrywka(mUUID);
        List<Gra> gry = mTysiacLab.getGry(mUUID);
        mEditTextPlayer1.setText(rozgrywka.getPlayer1());
        mEditTextPlayer2.setText(rozgrywka.getPlayer2());
        mEditTextPlayer3.setText(rozgrywka.getPlayer3());
        mEditTextPlayer4.setText(rozgrywka.getPlayer4());
        if (gry.size() != 0) {
            mEditTextPlayer1.setVisibility(rozgrywka.getPlayer1().toString().isEmpty() ? View.GONE : View.VISIBLE);
            mEditTextPlayer2.setVisibility(rozgrywka.getPlayer2().toString().isEmpty() ? View.GONE : View.VISIBLE);
            mEditTextPlayer3.setVisibility(rozgrywka.getPlayer3().toString().isEmpty() ? View.GONE : View.VISIBLE);
            mEditTextPlayer4.setVisibility(rozgrywka.getPlayer4().toString().isEmpty() ? View.GONE : View.VISIBLE);
        }
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok,((dialog, which) -> sendResult()))
                .setNegativeButton(android.R.string.cancel,null)
                .create();
    }

    private void sendResult() {
        //Rozgrywka rozgrywka = mTysiacLab.getRozgrywka(mUUID);
        String player1 = mEditTextPlayer1.getText().toString().isEmpty() ? " " : mEditTextPlayer1.getText().toString();
        String player2 = mEditTextPlayer2.getText().toString().isEmpty() ? " " : mEditTextPlayer2.getText().toString();
        String player3 = mEditTextPlayer3.getText().toString().isEmpty() ? " " : mEditTextPlayer3.getText().toString();
        String player4 = mEditTextPlayer4.getText().toString().isEmpty() ? " " : mEditTextPlayer4.getText().toString();
        /*rozgrywka.setPlayer1(player1);
        rozgrywka.setPlayer2(player2);
        rozgrywka.setPlayer3(player3);
        rozgrywka.setPlayer4(player4);
        mTysiacLab.updateRozgrywka(rozgrywka);*/
        if (getTargetFragment() == null) { return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_UUID,mUUID);
        intent.putExtra(EXTRA_PLAYER1, mEditTextPlayer1.getText().equals("") ? "0" : mEditTextPlayer1.getText().toString());
        intent.putExtra(EXTRA_PLAYER2, mEditTextPlayer2.getText().equals("") ? "0" : mEditTextPlayer2.getText().toString());
        intent.putExtra(EXTRA_PLAYER3, mEditTextPlayer3.getText().equals("") ? "0" : mEditTextPlayer3.getText().toString());
        intent.putExtra(EXTRA_PLAYER4, mEditTextPlayer4.getText().equals("") ? "0" : mEditTextPlayer4.getText().toString());
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    public static RozgrywkaEditDialog newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UUID,uuid);
        RozgrywkaEditDialog fragment = new RozgrywkaEditDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
