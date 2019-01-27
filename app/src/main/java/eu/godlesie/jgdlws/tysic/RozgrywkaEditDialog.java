package eu.godlesie.jgdlws.tysic;

import android.app.AlertDialog;
import android.app.Dialog;
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

public class RozgrywkaEditDialog extends DialogFragment {
    public static final String EXTRA_PLAYER1 = "eu.godlesie.jgdlws.player1";
    public static final String EXTRA_PLAYER2 = "eu.godlesie.jgdlws.player2";
    public static final String EXTRA_PLAYER3 = "eu.godlesie.jgdlws.player3";
    public static final String EXTRA_PLAYER4 = "eu.godlesie.jgdlws.player4";
    public static final int REQUEST_WYNIK = 1;

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
        mEditTextPlayer1.setText(rozgrywka.getPlayer1());
        mEditTextPlayer2.setText(rozgrywka.getPlayer2());
        mEditTextPlayer3.setText(rozgrywka.getPlayer3());
        mEditTextPlayer4.setText(rozgrywka.getPlayer4());
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok,null)
                .setNegativeButton(android.R.string.cancel,null)
                .create();
    }
    public static RozgrywkaEditDialog newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UUID,uuid);
        RozgrywkaEditDialog fragment = new RozgrywkaEditDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
