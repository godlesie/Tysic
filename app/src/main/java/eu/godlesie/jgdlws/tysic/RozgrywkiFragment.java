package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class RozgrywkiFragment extends Fragment {
    RecyclerView mRecyclerView;
    RozgrywkaAdapter mAdapter;
    TysiacLab tysiacLab;

    public static final String EXTRA_ROZGRYWKA_UUID = "eu.godlesie.jgdlws.rozgrywka_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rozgrywki,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerview_rozgrywki);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tysiacLab = TysiacLab.get(getActivity());
        updateUI();
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RozgrywkiActivity.REQUEST_DIALOG) {
                String player1 = (String) data.getSerializableExtra(RozgrywkaAddDialog.EXTRA_PLAYER1);
                String player2 = (String) data.getSerializableExtra(RozgrywkaAddDialog.EXTRA_PLAYER2);
                String player3 = (String) data.getSerializableExtra(RozgrywkaAddDialog.EXTRA_PLAYER3);
                String player4 = (String) data.getSerializableExtra(RozgrywkaAddDialog.EXTRA_PLAYER4);
                Rozgrywka rozgrywka = new Rozgrywka();
                rozgrywka.setPlayer1(player1);
                rozgrywka.setPlayer2(player2);
                rozgrywka.setPlayer3(player3);
                rozgrywka.setPlayer4(player4);
                tysiacLab.addRozgrywka(rozgrywka);
                updateUI();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateUI() {
        List<Rozgrywka> rozgrywki = tysiacLab.getRozgrywki();
        if (mAdapter == null) {
            mAdapter = new RozgrywkaAdapter(rozgrywki);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRozgrywki(rozgrywki);
            mAdapter.notifyDataSetChanged();
        }

    }


    private class RozgrywkaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //deklaracja pól widoku
        private TextView mTextViewNumerRozgrywki;

        private TextView mTextViewPlayer1, mTextViewPlayer2, mTextViewPlayer3, mTextViewPlayer4;
        private TextView mTextViewBomb1,mTextViewBomb2,mTextViewBomb3,mTextViewBomb4;
        private TextView mTextViewScore1,mTextViewScore2,mTextViewScore3,mTextViewScore4;

        private TableRow mTableRowPlayer3,mTableRowPlayer4;
        private TableLayout mTableLayoutPlayers;

        private Button mButtonDelete;

        private Rozgrywka mRozgrywka;
        private static final String DATE_FORMAT = "EEE, MMM dd: HH:MM";


        RozgrywkaHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_rozgywki, parent,false));

            mTableLayoutPlayers = itemView.findViewById(R.id.table_summary_players);
            mTableRowPlayer3 = itemView.findViewById(R.id.table_row_player_3);
            mTableRowPlayer4 = itemView.findViewById(R.id.table_row_player_4);

            mTextViewNumerRozgrywki = itemView.findViewById(R.id.text_view_numer_gry);
            mTextViewPlayer1 = itemView.findViewById(R.id.text_view_player_1);
            mTextViewBomb1 = itemView.findViewById(R.id.text_view_bomb_1);
            mTextViewScore1 = itemView.findViewById(R.id.text_view_score_1);

            mTextViewPlayer2 = itemView.findViewById(R.id.text_view_player_2);
            mTextViewBomb2 = itemView.findViewById(R.id.text_view_bomb_2);
            mTextViewScore2 = itemView.findViewById(R.id.text_view_score_2);

            mTextViewPlayer3 = itemView.findViewById(R.id.text_view_player_3);
            mTextViewBomb3 = itemView.findViewById(R.id.text_view_bomb_3);
            mTextViewScore3 = itemView.findViewById(R.id.text_view_score_3);

            mTextViewPlayer4 = itemView.findViewById(R.id.text_view_player_4);
            mTextViewBomb4 = itemView.findViewById(R.id.text_view_bomb_4);
            mTextViewScore4 = itemView.findViewById(R.id.text_view_score_4);

            mButtonDelete = itemView.findViewById(R.id.btn_delete);
            mTableLayoutPlayers.setOnClickListener(this);

        }
        void bind(Rozgrywka rozgrywka) {
            mRozgrywka = rozgrywka;
            String scoreTitle = getResources().getString(R.string.score_title) + " " + DateFormat.format(DATE_FORMAT, rozgrywka.getDate());
            mTextViewNumerRozgrywki.setText(scoreTitle);
            mTextViewPlayer1.setText(rozgrywka.getPlayer1());
            mTextViewPlayer1.setTypeface(mTextViewPlayer1.getTypeface(),rozgrywka.getWynik1()>=1000 ? Typeface.BOLD : Typeface.NORMAL);
            mTextViewPlayer1.setTextColor(rozgrywka.getWynik1() >= 1000 ? Color.GREEN : Color.BLACK);
            mTextViewBomb1.setText(rozgrywka.getBomb1() == 0 ? "" : String.valueOf(rozgrywka.getBomb1()));
            mTextViewScore1.setText(String.valueOf(rozgrywka.getWynik1()));

            mTextViewPlayer2.setText(rozgrywka.getPlayer2());
            mTextViewPlayer2.setTypeface(mTextViewPlayer2.getTypeface(),rozgrywka.getWynik2()>=1000 ? Typeface.BOLD : Typeface.NORMAL);
            mTextViewPlayer2.setTextColor(rozgrywka.getWynik2() >= 1000 ? Color.GREEN : Color.BLACK);
            mTextViewBomb2.setText(rozgrywka.getBomb2() == 0 ? "" : String.valueOf(rozgrywka.getBomb2()));
            mTextViewScore2.setText(String.valueOf(rozgrywka.getWynik2()));

            mTableRowPlayer3.setVisibility(rozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
            mTextViewPlayer3.setText(rozgrywka.getPlayer3());
            mTextViewPlayer3.setTypeface(mTextViewPlayer3.getTypeface(),rozgrywka.getWynik3()>=1000 ? Typeface.BOLD : Typeface.NORMAL);
            mTextViewPlayer3.setTextColor(rozgrywka.getWynik3() >= 1000 ? Color.GREEN : Color.BLACK);
            mTextViewBomb3.setText(rozgrywka.getBomb3() == 0 ? "" : String.valueOf(rozgrywka.getBomb3()));
            mTextViewScore3.setText(String.valueOf(rozgrywka.getWynik3()));

            mTableRowPlayer4.setVisibility(rozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);
            mTextViewPlayer4.setText(rozgrywka.getPlayer4());
            mTextViewPlayer4.setTypeface(mTextViewPlayer4.getTypeface(),rozgrywka.getWynik4()>=1000 ? Typeface.BOLD : Typeface.NORMAL);
            mTextViewPlayer4.setTextColor(rozgrywka.getWynik4() >= 1000 ? Color.GREEN : Color.BLACK);
            mTextViewBomb4.setText(rozgrywka.getBomb4() == 0 ? "" : String.valueOf(rozgrywka.getBomb4()));
            mTextViewScore4.setText(String.valueOf(rozgrywka.getWynik4()));
            mButtonDelete.setOnClickListener(v -> new AlertDialog.Builder(getActivity()
            ).setTitle("test")
            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                tysiacLab.deleteRozgrywka(rozgrywka);
                updateUI();
            })
            .setNegativeButton(android.R.string.cancel,null)
            .create().show());

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), GraActivity.class);
            UUID uuid = mRozgrywka.getUUID();
            intent.putExtra(EXTRA_ROZGRYWKA_UUID,uuid);
            startActivity(intent);
        }
    }
    private class RozgrywkaAdapter extends RecyclerView.Adapter<RozgrywkaHolder> {
        private List<Rozgrywka> mRozgrywki;
        RozgrywkaAdapter(List<Rozgrywka> rozgrywki) {
            mRozgrywki = rozgrywki;
        }

        @NonNull
        @Override
        public RozgrywkaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RozgrywkaHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RozgrywkaHolder rozgrywkaHolder, int i) {
            Rozgrywka rozgrywka = mRozgrywki.get(i);

            rozgrywkaHolder.bind(rozgrywka);
        }

        @Override
        public int getItemCount() {
            return mRozgrywki.size();
        }

        void setRozgrywki(List<Rozgrywka> rozgrywki) {
            mRozgrywki = rozgrywki;
        }
    }
}
