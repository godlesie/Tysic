package eu.godlesie.jgdlws.tysic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import eu.godlesie.jgdlws.tysic.database.GraDbSchema;
import eu.godlesie.jgdlws.tysic.model.Gra;
import eu.godlesie.jgdlws.tysic.model.Player;
import eu.godlesie.jgdlws.tysic.model.Rozgrywka;
import eu.godlesie.jgdlws.tysic.model.TysiacLab;

public class GraFragment extends Fragment {
    static final String SET_WYNIK_DIALOG = "set_wynik";
    static final String EDIT_CONTRACT_DIALOG = "edit_contract";

    FloatingActionButton mFab;
    RecyclerView mRecyclerView;
    GraAdapter mAdapter;
    TysiacLab mTysiacLab;
    Rozgrywka mRozgrywka;
    UUID mUUID;
    Button mButtonScore, mButtonContract, mButtonSetBomb;

    Typeface mFontAwesome;

    List<Integer> mRozdajacy = new ArrayList<>();
    int mIloscGraczy = 0;
    int mIloscBomb = 0;

    TextView mTextViewSummaryBomb1,mTextViewSummaryBomb2,mTextViewSummaryBomb3,mTextViewSummaryBomb4;
    TextView mTextViewSummaryScore1,mTextViewSummaryScore2,mTextViewSummaryScore3,mTextViewSummaryScore4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Objects.requireNonNull(getActivity()).getIntent() != null) {
            mUUID = (UUID) getActivity().getIntent().getSerializableExtra(RozgrywkiFragment.EXTRA_ROZGRYWKA_UUID);
        }
        mTysiacLab = TysiacLab.get(getActivity());
        mRozgrywka = mTysiacLab.getRozgrywka(mUUID);
        mFontAwesome = Typeface.createFromAsset(getActivity().getAssets(),"fa-solid-900.ttf");
        if (!mRozgrywka.getPlayer1().isEmpty()) mRozdajacy.add(1);
        if (!mRozgrywka.getPlayer2().isEmpty()) mRozdajacy.add(2);
        if (!mRozgrywka.getPlayer3().isEmpty()) mRozdajacy.add(0);
        if (!mRozgrywka.getPlayer4().isEmpty()) mRozdajacy.add(0);
        mIloscGraczy = mRozdajacy.size();
        mIloscBomb = Integer.valueOf(Objects.requireNonNull(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("bomb_list", "0")));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gry,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerview_gra);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFab = Objects.requireNonNull(getActivity()).findViewById(R.id.fab);
        TableRow tableRowSummaryPlayer3 = view.findViewById(R.id.table_row_summary_player_3);
        tableRowSummaryPlayer3.setVisibility(mRozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
        TableRow  tableRowSummaryPlayer4 = view.findViewById(R.id.table_row_summary_player_4);
        tableRowSummaryPlayer4.setVisibility(mRozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

        TextView textViewSummaryPlayer1 = view.findViewById(R.id.text_view_summary_player_1);
        TextView textViewSummaryPlayer2 = view.findViewById(R.id.text_view_summary_player_2);
        TextView textViewSummaryPlayer3 = view.findViewById(R.id.text_view_summary_player_3);
        TextView textViewSummaryPlayer4 = view.findViewById(R.id.text_view_summary_player_4);

        mTextViewSummaryBomb1 = view.findViewById(R.id.text_view_summary_bomb_1);
        mTextViewSummaryBomb2 = view.findViewById(R.id.text_view_summary_bomb_2);
        mTextViewSummaryBomb3 = view.findViewById(R.id.text_view_summary_bomb_3);
        mTextViewSummaryBomb4 = view.findViewById(R.id.text_view_summary_bomb_4);

        mTextViewSummaryBomb1.setText(String.valueOf(mRozgrywka.getBomb1()));
        mTextViewSummaryBomb2.setText(String.valueOf(mRozgrywka.getBomb2()));
        mTextViewSummaryBomb3.setText(String.valueOf(mRozgrywka.getBomb3()));
        mTextViewSummaryBomb4.setText(String.valueOf(mRozgrywka.getBomb4()));

        mTextViewSummaryScore1 = view.findViewById(R.id.text_view_summary_score_1);
        mTextViewSummaryScore2 = view.findViewById(R.id.text_view_summary_score_2);
        mTextViewSummaryScore3 = view.findViewById(R.id.text_view_summary_score_3);
        mTextViewSummaryScore4 = view.findViewById(R.id.text_view_summary_score_4);

        textViewSummaryPlayer1.setText(mRozgrywka.getPlayer1());
        textViewSummaryPlayer2.setText(mRozgrywka.getPlayer2());
        textViewSummaryPlayer3.setText(mRozgrywka.getPlayer3());
        textViewSummaryPlayer4.setText(mRozgrywka.getPlayer4());
        updateUI();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    @Override
    public void onPause() {
        super.onPause();
        updateUI();
    }
    private void updateUI() {
        List<Gra> gry = mTysiacLab.getGry(mUUID);
        Gra gra = mTysiacLab.getGra(mUUID,mTysiacLab.getLastGra(mUUID));

        Rozgrywka rozgrywka = mTysiacLab.getRozgrywka(mUUID);
        rozgrywka.setWynik1(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_1,mUUID));
        rozgrywka.setWynik2(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_2,mUUID));
        rozgrywka.setWynik3(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_3,mUUID));
        rozgrywka.setWynik4(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_4,mUUID));

        mTysiacLab.updateRozgrywka(rozgrywka);

        mTextViewSummaryScore1.setText(String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_1,mUUID)));
        mTextViewSummaryScore2.setText(String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_2,mUUID)));
        mTextViewSummaryScore3.setText(String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_3,mUUID)));
        mTextViewSummaryScore4.setText(String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.WYNIK_4,mUUID)));

        mTextViewSummaryBomb1.setText(mRozgrywka.getBomb1() == 0 ? "" : String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.BOMBA_1,mUUID)));
        mTextViewSummaryBomb2.setText(mRozgrywka.getBomb2() == 0 ? "" : String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.BOMBA_2,mUUID)));
        mTextViewSummaryBomb3.setText(mRozgrywka.getBomb3() == 0 ? "" : String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.BOMBA_3,mUUID)));
        mTextViewSummaryBomb4.setText(mRozgrywka.getBomb4() == 0 ? "" : String.valueOf(mTysiacLab.getSummaryWynik(GraDbSchema.GraTable.Cols.BOMBA_4,mUUID)));
        if (gry.size() != 0) {
            if (gra.getWynik1() + gra.getWynik2() + gra.getWynik3() + gra.getWynik4() == 0) {
                mFab.hide();
            } else {
                mFab.show();
            }
            if (mRozgrywka.getWynik1() >= 1000 || mRozgrywka.getWynik2() >= 1000 || mRozgrywka.getWynik3() >= 1000 || mRozgrywka.getWynik4() >= 10000) {
                mFab.hide();
            }
        }
        if (mAdapter == null) {
            mAdapter = new GraAdapter(gry);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setGry(gry);
            mAdapter.notifyDataSetChanged();
        }
    }
    private class GraHolder extends RecyclerView.ViewHolder {
        //deklaracja pól widoku
        private TextView mTextViewNumerGry;
        private TextView mTextViewPlayer1,mTextViewPlayer2,mTextViewPlayer3,mTextViewPlayer4;
        private TextView mTextViewContract1,mTextViewContract2,mTextViewContract3,mTextViewContract4;
        TextView mTextViewBomb1,mTextViewBomb2,mTextViewBomb3,mTextViewBomb4;
        private TextView mTextViewScore1,mTextViewScore2,mTextViewScore3,mTextViewScore4;
        private TableRow mTableRowPlayer3,mTableRowPlayer4;
        //pojedyncza gra
        private Gra mGra;

        GraHolder(@NotNull LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_gry,parent,false));
            mTableRowPlayer3 = itemView.findViewById(R.id.table_row_player_3);
            mTableRowPlayer4 = itemView.findViewById(R.id.table_row_player_4);

            mTextViewNumerGry = itemView.findViewById(R.id.text_view_numer_gry);

            mButtonScore = itemView.findViewById(R.id.btn_set_score);
            mButtonContract = itemView.findViewById(R.id.btn_set_contract);
            mButtonSetBomb = itemView.findViewById(R.id.btn_set_bomb);

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
        void bind(Gra gra) {
            mGra = gra;
            Player lPlayer1 = mTysiacLab.getplayer(mGra,1);
            Player lPlayer2 = mTysiacLab.getplayer(mGra,2);
            Player lPlayer3 = mTysiacLab.getplayer(mGra,3);
            Player lPlayer4 = mTysiacLab.getplayer(mGra,4);

            ArrayList<Integer> rozdajacy = new ArrayList<>(mRozdajacy);

            Function<Integer, Integer> setContract = i -> i == null ? 60 : i == 0 ? 60 : 0;
            Function<Integer, Integer> updateBomba = i -> i == 0 ? 0 : 1;
            //Function<Player,Integer> setBomba = (Player p) -> p.getBomba() == 1 ? mRozgrywka.getBomb1() + 1 : mRozgrywka.getBomb1();

            Predicate<Gra> isWynikNoZero = g -> g.getWynik1() + g.getWynik2() + g.getWynik3() + g.getWynik4() != 0;
            Predicate<Gra> isGenugBombs = g -> (g.getContract1() > 0 && g.getBomba1() >= mIloscBomb) ||
                    (g.getContract2() > 0 && g.getBomba2() >= mIloscBomb) ||
                    (g.getContract3() > 0 && g.getBomba3() >= mIloscBomb) ||
                    (g.getContract4() > 0 && g.getBomba4() >= mIloscBomb);

            mButtonSetBomb.setOnClickListener(v -> {
                mGra.setWynik1(setContract.apply(lPlayer1.getContract()));
                mGra.setWynik2(setContract.apply(lPlayer2.getContract()));
                mGra.setWynik3(setContract.apply(lPlayer3.getContract()));
                mGra.setWynik4(setContract.apply(lPlayer4.getContract()));

                mGra.setBomba1(updateBomba.apply(lPlayer1.getContract()));
                mGra.setBomba2(updateBomba.apply(lPlayer2.getContract()));
                mGra.setBomba3(updateBomba.apply(lPlayer3.getContract()));
                mGra.setBomba4(updateBomba.apply(lPlayer4.getContract()));
                mTysiacLab.updateGra(mGra);

                mRozgrywka.setBomb1(lPlayer1.getBomba() == 1 ? mRozgrywka.getBomb1() + 1 : mRozgrywka.getBomb1());
                mRozgrywka.setBomb2(lPlayer2.getBomba() == 1 ? mRozgrywka.getBomb2() + 1 : mRozgrywka.getBomb2());
                mRozgrywka.setBomb3(lPlayer3.getBomba() == 1 ? mRozgrywka.getBomb3() + 1 : mRozgrywka.getBomb3());
                mRozgrywka.setBomb4(lPlayer4.getBomba() == 1 ? mRozgrywka.getBomb4() + 1 : mRozgrywka.getBomb4());

                mTysiacLab.updateRozgrywka(mRozgrywka);
                updateUI();
            });

            mButtonSetBomb.setVisibility(isGenugBombs.test(mGra) || isWynikNoZero.test(mGra) ? View.GONE : View.VISIBLE);
            //mButtonSetBomb.setVisibility(isWynikNoZero.test(mGra) ? View.GONE : View.VISIBLE);

            mButtonContract.setVisibility(isWynikNoZero.test(mGra) ? View.GONE : View.VISIBLE);
            mButtonContract.setOnClickListener(v -> {
                    FragmentManager manager = getFragmentManager();
                    GraEditContractDialog dialog = GraEditContractDialog.newInstance(mUUID,mGra.getLp());
                    dialog.setTargetFragment(GraFragment.this,GraEditContractDialog.REQUEST_EDIT_CONTRACT);
                    assert manager != null;
                    dialog.show(manager,EDIT_CONTRACT_DIALOG);
                     mButtonSetBomb.setVisibility(isGenugBombs.test(mGra) ? View.GONE : View.VISIBLE);
            });
            mButtonScore.setOnClickListener(v -> {
                FragmentManager manager = getFragmentManager();
                GraSetWynikDialog dialog = GraSetWynikDialog.newInstance(mUUID,mGra.getLp());
                dialog.setTargetFragment(GraFragment.this,GraSetWynikDialog.REQUEST_WYNIK);
                assert manager != null;
                dialog.show(manager,SET_WYNIK_DIALOG);
            });

            mTextViewNumerGry.setText(getResources().getString(R.string.number_of_play) + String.valueOf(mGra.getLp()));

            mTableRowPlayer3.setVisibility(mRozgrywka.getPlayer3().isEmpty() ? View.GONE : View.VISIBLE);
            mTableRowPlayer4.setVisibility(mRozgrywka.getPlayer4().isEmpty() ? View.GONE : View.VISIBLE);

            mTextViewPlayer1.setTypeface(mFontAwesome);
            mTextViewPlayer2.setTypeface(mFontAwesome);
            mTextViewPlayer3.setTypeface(mFontAwesome);
            mTextViewPlayer4.setTypeface(mFontAwesome);

            Collections.rotate(rozdajacy, mGra.getLp()-1);

            mTextViewPlayer1.setText(!lPlayer1.getName().isEmpty() ? lPlayer1.getName() + " " +
                    (rozdajacy.get(0) ==1 ? Objects.requireNonNull(getActivity()).getResources().getString(R.string.fa_hand) : " ")
                    : "");
            mTextViewPlayer2.setText(!lPlayer2.getName().isEmpty() ? lPlayer2.getName() + " " +
                    (rozdajacy.get(1) ==1 ? getActivity().getResources().getString(R.string.fa_hand) : " ")
                    : "");
            mTextViewPlayer3.setText(!lPlayer3.getName().isEmpty() ? lPlayer3.getName() + " " +
                    (rozdajacy.get(2) ==1 ? getActivity().getResources().getString(R.string.fa_hand) : " ")
                    : "");
            mTextViewPlayer4.setText(!lPlayer4.getName().isEmpty() ? lPlayer4.getName() + " " +
                    (rozdajacy.get(3) ==1 ? getActivity().getResources().getString(R.string.fa_hand) : " ")
                    : "");

            mTextViewBomb1.setText(lPlayer1.getBomba() == 0 ? "" : String.valueOf(lPlayer1.getBomba()));
            mTextViewBomb1.setText(lPlayer2.getBomba() == 0 ? "" : String.valueOf(lPlayer2.getBomba()));
            mTextViewBomb1.setText(lPlayer3.getBomba() == 0 ? "" : String.valueOf(lPlayer3.getBomba()));
            mTextViewBomb1.setText(lPlayer4.getBomba() == 0 ? "" : String.valueOf(lPlayer4.getBomba()));

            mTextViewContract1.setText(lPlayer1.getContract() == 0 ? "" : String.valueOf(lPlayer1.getContract()));
            mTextViewContract2.setText(lPlayer2.getContract() == 0 ? "" : String.valueOf(lPlayer2.getContract()));
            mTextViewContract3.setText(lPlayer3.getContract() == 0 ? "" : String.valueOf(lPlayer3.getContract()));
            mTextViewContract4.setText(lPlayer4.getContract() == 0 ? "" : String.valueOf(lPlayer4.getContract()));

            mTextViewScore1.setText(lPlayer1.getScore() == 0 ? "" : String.valueOf(lPlayer1.getScore()));
            mTextViewScore2.setText(lPlayer2.getScore() == 0 ? "" : String.valueOf(lPlayer2.getScore()));
            mTextViewScore3.setText(lPlayer3.getScore() == 0 ? "" : String.valueOf(lPlayer3.getScore()));
            mTextViewScore4.setText(lPlayer4.getScore() == 0 ? "" : String.valueOf(lPlayer4.getScore()));

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }
        if (requestCode ==GraActivity.ADD_GRA_DIALOG) {
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
        if (requestCode == GraSetWynikDialog.REQUEST_WYNIK) {
            updateUI();
        }
        if (requestCode == GraEditContractDialog.REQUEST_EDIT_CONTRACT) {
            String sContract1 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT1);
            String sContract2 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT2);
            String sContract3 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT3);
            String sContract4 = (String) data.getSerializableExtra(GraAddDialog.EXTRA_CONTRACT4);
            Gra gra = mTysiacLab.getGra(mUUID,mTysiacLab.getLastGra(mUUID));
            gra.setContract1(sContract1.isEmpty() ? 0 : Integer.parseInt(sContract1));
            gra.setContract2(sContract2.isEmpty() ? 0 : Integer.parseInt(sContract2));
            gra.setContract3(sContract3.isEmpty() ? 0 : Integer.parseInt(sContract3));
            gra.setContract4(sContract4.isEmpty() ? 0 : Integer.parseInt(sContract4));
            mTysiacLab.updateGra(gra);
            updateUI();

        }
    }

    private class GraAdapter extends RecyclerView.Adapter<GraHolder> {
        private List<Gra> mGry;
        GraAdapter(List<Gra> gry) { mGry = gry; }

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

        void setGry(List<Gra> gry) {
            mGry = gry;
        }
    }
}
