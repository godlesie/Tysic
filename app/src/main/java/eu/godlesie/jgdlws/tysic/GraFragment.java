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
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraFragment extends Fragment {
    RecyclerView mRecyclerView;
    GraAdapter mAdapter;
    TysiacLab mTysiacLab;
    Rozgrywka mRozgrywka;
    UUID mUUID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gry,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerview_gra);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTysiacLab = TysiacLab.get(getActivity());
        mUUID = (UUID) getActivity().getIntent().getSerializableExtra(RozgrywkiFragment.EXTRA_ROZGRYWKA_UUID);
        mRozgrywka = mTysiacLab.getRozgrywka(mUUID);
        updateUI();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateUI() {
        List<Gra> gry = mTysiacLab.getGry(mUUID);
        if (mAdapter == null) {
            mAdapter = new GraAdapter(gry);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setGry(gry);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class GraHolder extends RecyclerView.ViewHolder {
        //deklaracja pól widoku
        private TextView mTextViewNumerGry;
        private TextView mTextViewPlayer1,mTextViewPlayer2,mTextViewPlayer3,mTextViewPlayer4;
        private TextView mTextViewContract1,mTextViewContract2,mTextViewContract3,mTextViewContract4;
        private TextView mTextViewBomb1,mTextViewBomb2,mTextViewBomb3,mTextViewBomb4;
        private TextView mTextViewScore1,mTextViewScore2,mTextViewScore3,mTextViewScore4;
        private TableRow mTableRowPlayer3,mTableRowPlayer4;
        //pojedyncza gra
        private Gra mGra;

        public GraHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.item_gry,parent,false));
            mTableRowPlayer3 = itemView.findViewById(R.id.table_row_player_3);
            mTableRowPlayer4 = itemView.findViewById(R.id.table_row_player_4);

            mTextViewNumerGry = itemView.findViewById(R.id.text_view_numer_gry);

            mTextViewPlayer1 = itemView.findViewById(R.id.text_view_player_1);
            mTextViewPlayer2 = itemView.findViewById(R.id.text_view_player_2);
            mTextViewPlayer3 = itemView.findViewById(R.id.text_view_player_3);
            mTextViewPlayer4 = itemView.findViewById(R.id.text_view_player_4);
            mTextViewContract1 = itemView.findViewById(R.id.text_view_contract_1);
            mTextViewContract2 = itemView.findViewById(R.id.text_view_contract_2);
            mTextViewContract3 = itemView.findViewById(R.id.text_view_contract_3);
            mTextViewContract4 = itemView.findViewById(R.id.text_view_contract_4);
            mTextViewBomb1 = itemView.findViewById(R.id.text_view_bomb_1);
            mTextViewBomb2 = itemView.findViewById(R.id.text_view_bomb_2);
            mTextViewBomb3 = itemView.findViewById(R.id.text_view_bomb_3);
            mTextViewBomb4 = itemView.findViewById(R.id.text_view_bomb_4);
            mTextViewScore1 = itemView.findViewById(R.id.text_view_score_1);
            mTextViewScore2 = itemView.findViewById(R.id.text_view_score_2);
            mTextViewScore3 = itemView.findViewById(R.id.text_view_score_3);
            mTextViewScore4 = itemView.findViewById(R.id.text_view_score_4);
        }
        public void bind(Gra gra) {
            mGra = gra;
            mTextViewNumerGry.setText("Numer gry: " + String.valueOf(gra.getLp()));
            //ilość graczy
            mTableRowPlayer3.setVisibility(mRozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
            mTableRowPlayer4.setVisibility(mRozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);
            //gracze
            mTextViewPlayer1.setText(mRozgrywka.getPlayer1());
            mTextViewPlayer2.setText(mRozgrywka.getPlayer2());
            mTextViewPlayer3.setText(mRozgrywka.getPlayer3());
            mTextViewPlayer4.setText(mRozgrywka.getPlayer4());

            mTextViewBomb1.setText(mGra.getBomba1() == 0 ? "" : String.valueOf(mGra.getBomba1()));
            mTextViewBomb2.setText(mGra.getBomba2() == 0 ? "" : String.valueOf(mGra.getBomba2()));
            mTextViewBomb3.setText(mGra.getBomba3() == 0 ? "" : String.valueOf(mGra.getBomba3()));
            mTextViewBomb4.setText(mGra.getBomba4() == 0 ? "" : String.valueOf(mGra.getBomba4()));

            mTextViewContract1.setText(mGra.getContract1() == 0 ? "" : String.valueOf(mGra.getContract1()));
            mTextViewContract2.setText(mGra.getContract2() == 0 ? "" : String.valueOf(mGra.getContract2()));
            mTextViewContract3.setText(mGra.getContract3() == 0 ? "" : String.valueOf(mGra.getContract3()));
            mTextViewContract4.setText(mGra.getContract4() == 0 ? "" : String.valueOf(mGra.getContract4()));

            mTextViewScore1.setText(mGra.getWynik1() == 0 ? "" : String.valueOf(mGra.getWynik1()));
            mTextViewScore2.setText(mGra.getWynik2() == 0 ? "" : String.valueOf(mGra.getWynik2()));
            mTextViewScore3.setText(mGra.getWynik3() == 0 ? "" : String.valueOf(mGra.getWynik3()));
            mTextViewScore4.setText(mGra.getWynik4() == 0 ? "" : String.valueOf(mGra.getWynik4()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode ==GraActivity.REQUEST_DIALOG) {
                String sContract1 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT1);
                String sContract2 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT2);
                String sContract3 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT3);
                String sContract4 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT4);
                Gra gra = new Gra(mUUID);
                gra.setLp(mTysiacLab.getLastGra(mUUID)+1);
                gra.setContract1(sContract1.isEmpty() ? 0 : Integer.parseInt(sContract1));
                gra.setContract2(sContract2.isEmpty() ? 0 : Integer.parseInt(sContract2));
                gra.setContract3(sContract3.isEmpty() ? 0 : Integer.parseInt(sContract3));
                gra.setContract4(sContract4.isEmpty() ? 0 : Integer.parseInt(sContract4));
                mTysiacLab.addGra(gra);
                updateUI();
            }
        }
    }

    private class GraAdapter extends RecyclerView.Adapter<GraHolder> {
        private List<Gra> mGry;
        public GraAdapter(List<Gra> gry) { mGry = gry; }

        @NonNull
        @Override
        public GraHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GraHolder(inflater,viewGroup);
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

        public void setGry(List<Gra> gry) {
            mGry = gry;
        }
    }
}