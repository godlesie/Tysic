package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraFragment extends Fragment {

    //TODO - dodaj górny widok z podsumowaniem
    //TODO - zapisz dane z kontraktu do bazy
    private UUID mUUID;
    private RecyclerView mRecyclerViewGra;
    private GraAdapter mAdapter;
    private TysiacLab tysiacLab;

    public static final String ROZGRYWKA_ID = "rozgrywka_id";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gry,container,false);
        mRecyclerViewGra = view.findViewById(R.id.recyclerview_gra);
        mRecyclerViewGra.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUUID = (UUID) getActivity().getIntent().getSerializableExtra(RozgrywkiFragment.EXTRA_ROZGRYWKA_UUID);
        tysiacLab = TysiacLab.get(getActivity());
        updateUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_OK) {
            if (requestCode == GraActivity.REQUEST_DIALOG) {
                int contract1 = Integer.parseInt((String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT1));
                int contract2 = Integer.parseInt((String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT2));
                int contract3 = Integer.parseInt((String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT3));
                int contract4 = Integer.parseInt((String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT4));
                Gra gra = new Gra(mUUID);
                gra.setContract1(contract1);
                gra.setContract2(contract2);
                gra.setContract3(contract3);
                gra.setContract4(contract4);
                tysiacLab.addGra(gra);
                updateUI();
            }
        }
    }

    private void updateUI() {
        List<Gra> gry = tysiacLab.getGry(mUUID);
        mAdapter = new GraAdapter(gry);
        mRecyclerViewGra.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private class GraHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewGra;
        private TextView mTextViewPlayer1,mTextViewPlayer2,mTextViewPlayer3,mTextViewPlayer4;
        private TextView mTextViewContract1,mTextViewContract2,mTextViewContract3,mTextViewContract4;
        private TextView mTextViewBomba1,mTextViewBomba2,mTextViewBomba3,mTextViewBomba4;
        private TextView mTextViewScore1,mTextViewScore2,mTextViewScore3,mTextViewScore4;

        private TableRow mTableRowPlayer1,mTableRowPlayer2,mTableRowPlayer3,mTableRowPlayer4;

        private Gra mGra;
        private Rozgrywka mRozgrywka;

        public GraHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_gry,parent,false));

            mRozgrywka = tysiacLab.getRozgrywka(mUUID);

            mTableRowPlayer1 = itemView.findViewById(R.id.table_row_player_1);
            mTableRowPlayer2 = itemView.findViewById(R.id.table_row_player_2);
            mTableRowPlayer3 = itemView.findViewById(R.id.table_row_player_3);
            mTableRowPlayer4 = itemView.findViewById(R.id.table_row_player_4);

            mTextViewGra = itemView.findViewById(R.id.text_view_numer_gry);

            mTextViewPlayer1 = itemView.findViewById(R.id.text_view_player_1);
            mTextViewPlayer2 = itemView.findViewById(R.id.text_view_player_2);
            mTextViewPlayer3 = itemView.findViewById(R.id.text_view_player_3);
            mTextViewPlayer4 = itemView.findViewById(R.id.text_view_player_4);

            mTextViewContract1 = itemView.findViewById(R.id.text_view_contract_1);
            mTextViewContract2 = itemView.findViewById(R.id.text_view_contract_2);
            mTextViewContract3 = itemView.findViewById(R.id.text_view_contract_3);
            mTextViewContract4 = itemView.findViewById(R.id.text_view_contract_4);

            mTextViewBomba1 = itemView.findViewById(R.id.text_view_bomb_1);
            mTextViewBomba2 = itemView.findViewById(R.id.text_view_bomb_2);
            mTextViewBomba3 = itemView.findViewById(R.id.text_view_bomb_3);
            mTextViewBomba4 = itemView.findViewById(R.id.text_view_bomb_4);

            mTextViewScore1 = itemView.findViewById(R.id.text_view_score_1);
            mTextViewScore2 = itemView.findViewById(R.id.text_view_score_2);
            mTextViewScore3 = itemView.findViewById(R.id.text_view_score_3);
            mTextViewScore4 = itemView.findViewById(R.id.text_view_score_4);
        }
        public void bind(Gra gra) {
            mGra = gra;

            mTableRowPlayer3.setVisibility(mRozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
            mTableRowPlayer4.setVisibility(mRozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

            mTextViewGra.setText("Gra numer: " + gra.getLp());
            mTextViewPlayer1.setText(mRozgrywka.getPlayer1());
            mTextViewPlayer2.setText(mRozgrywka.getPlayer2());
            mTextViewPlayer3.setText(mRozgrywka.getPlayer3());
            mTextViewPlayer4.setText(mRozgrywka.getPlayer4());

            mTextViewContract1.setText(String.valueOf(mGra.getContract1()));
            mTextViewContract2.setText(String.valueOf(mGra.getContract2()));
            mTextViewContract3.setText(String.valueOf(mGra.getContract3()));
            mTextViewContract4.setText(String.valueOf(mGra.getContract4()));

            mTextViewBomba1.setText(String.valueOf(mGra.getBomba1()));
            mTextViewBomba2.setText(String.valueOf(mGra.getBomba2()));
            mTextViewBomba3.setText(String.valueOf(mGra.getBomba3()));
            mTextViewBomba4.setText(String.valueOf(mGra.getBomba4()));

            mTextViewScore1.setText(String.valueOf(gra.getWynik1()));
            mTextViewScore2.setText(String.valueOf(gra.getWynik2()));
            mTextViewScore3.setText(String.valueOf(gra.getWynik3()));
            mTextViewScore4.setText(String.valueOf(gra.getWynik4()));
        }
    }

    private class GraAdapter extends RecyclerView.Adapter<GraHolder> {
        private List<Gra> mGry;
        public GraAdapter(List<Gra> gry) { mGry = gry; }
        @NonNull
        @Override
        public GraHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GraHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull GraHolder graHolder, int i) {
            Gra gra = mGry.get(i);
            graHolder.bind(gra);

        }

        @Override
        public int getItemCount() {
            return mGry.size();
        }
    }
}
