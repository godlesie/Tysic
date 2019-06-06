package eu.godlesie.jgdlws.tysic.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.database.GraCursorWrapper;
import eu.godlesie.jgdlws.tysic.database.RozgrywkaCursorWrapper;
import eu.godlesie.jgdlws.tysic.database.TysiacBaseHelper;

import static eu.godlesie.jgdlws.tysic.database.GraDbSchema.GraTable;
import static eu.godlesie.jgdlws.tysic.database.RozgrywkaDbSchema.RozgrywkaTable;

public class TysiacLab {

    @SuppressLint("StaticFieldLeak")
    private static TysiacLab sTysiacLab = null;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private static ContentValues getRozgrywkaValues(Rozgrywka rozgrywka) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RozgrywkaTable.Cols.UUID,rozgrywka.getUUID().toString());

        contentValues.put(RozgrywkaTable.Cols.PLAYER_1,rozgrywka.getPlayer1());
        contentValues.put(RozgrywkaTable.Cols.PLAYER_2,rozgrywka.getPlayer2());
        contentValues.put(RozgrywkaTable.Cols.PLAYER_3,rozgrywka.getPlayer3());
        contentValues.put(RozgrywkaTable.Cols.PLAYER_4,rozgrywka.getPlayer4());

        contentValues.put(RozgrywkaTable.Cols.WYNIK_1,rozgrywka.getWynik1());
        contentValues.put(RozgrywkaTable.Cols.WYNIK_2,rozgrywka.getWynik2());
        contentValues.put(RozgrywkaTable.Cols.WYNIK_3,rozgrywka.getWynik3());
        contentValues.put(RozgrywkaTable.Cols.WYNIK_4,rozgrywka.getWynik4());

        contentValues.put(RozgrywkaTable.Cols.BOMBA_1,rozgrywka.getBomb1());
        contentValues.put(RozgrywkaTable.Cols.BOMBA_2,rozgrywka.getBomb2());
        contentValues.put(RozgrywkaTable.Cols.BOMBA_3,rozgrywka.getBomb3());
        contentValues.put(RozgrywkaTable.Cols.BOMBA_4,rozgrywka.getBomb4());

        contentValues.put(RozgrywkaTable.Cols.DATE,rozgrywka.getDate().getTime());

        return contentValues;
    }
    private static ContentValues getGraValues(Gra gra) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(GraTable.Cols.ROZGRYWKA_UUID,gra.getUUIDRozgrywka().toString());
        contentValues.put(GraTable.Cols.LP,gra.getLp());

        contentValues.put(GraTable.Cols.CONTRACT_1,gra.getContract1());
        contentValues.put(GraTable.Cols.CONTRACT_2,gra.getContract2());
        contentValues.put(GraTable.Cols.CONTRACT_3,gra.getContract3());
        contentValues.put(GraTable.Cols.CONTRACT_4,gra.getContract4());

        contentValues.put(GraTable.Cols.WYNIK_1,gra.getWynik1());
        contentValues.put(GraTable.Cols.WYNIK_2,gra.getWynik2());
        contentValues.put(GraTable.Cols.WYNIK_3,gra.getWynik3());
        contentValues.put(GraTable.Cols.WYNIK_4,gra.getWynik4());

        contentValues.put(GraTable.Cols.BOMBA_1,gra.getBomba1());
        contentValues.put(GraTable.Cols.BOMBA_2,gra.getBomba2());
        contentValues.put(GraTable.Cols.BOMBA_3,gra.getBomba3());
        contentValues.put(GraTable.Cols.BOMBA_4,gra.getBomba4());

        return contentValues;
    }
    private TysiacLab(Context context) {
        mContext = context;
        mDatabase = new TysiacBaseHelper(mContext.getApplicationContext()).getWritableDatabase();
    }

    public static TysiacLab get(Context context) {
        if (sTysiacLab == null) {
            sTysiacLab = new TysiacLab(context);
        }
        return sTysiacLab;
    }

    public Rozgrywka getRozgrywka(UUID uuid) {
        try (RozgrywkaCursorWrapper cursor = queryRozgrywka(RozgrywkaTable.Cols.UUID + " = ?", new String[]{uuid.toString()}))
        {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            return cursor.getRozgrywka();
        }
    }
    public Gra getGra(UUID uuid,int lp) {
        try (GraCursorWrapper cursor = queryGra(
                GraTable.Cols.ROZGRYWKA_UUID + " = ? AND  " + GraTable.Cols.LP + " LIKE ?",
                new String[]{uuid.toString(), String.valueOf(lp)}
        )) {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            return cursor.getGra();
        }
    }
    public List<Rozgrywka> getRozgrywki() {
        List<Rozgrywka> rozgrywki = new ArrayList<>();
        try (RozgrywkaCursorWrapper cursor = queryRozgrywka(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rozgrywki.add(cursor.getRozgrywka());
                cursor.moveToNext();
            }
        }
        return rozgrywki;
    }
    public List<Gra> getGry(UUID uuid) {
        List<Gra> gry = new ArrayList<>();
        try (GraCursorWrapper cursor = queryGra(
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[]{uuid.toString()})) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                gry.add(cursor.getGra());
                cursor.moveToNext();
            }
        }
        return gry;
    }
    public void addRozgrywka(Rozgrywka rozgrywka) {
        ContentValues contentValues = getRozgrywkaValues(rozgrywka);
        mDatabase.insert(RozgrywkaTable.NAME,null,contentValues);
    }
    public void addGra(Gra gra) {
        ContentValues contentValues = getGraValues(gra);
        mDatabase.insert(GraTable.NAME,null,contentValues);
    }
    public void updateRozgrywka(Rozgrywka rozgrywka) {
        String uuidString = rozgrywka.getUUID().toString();
        ContentValues contentValues = getRozgrywkaValues(rozgrywka);
        mDatabase.update(RozgrywkaTable.NAME,contentValues,
                RozgrywkaTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }
    public void updateGra(Gra gra) {
        String lpString = String.valueOf(gra.getLp());
        String rozgrywkaUUID = gra.getUUIDRozgrywka().toString();
        ContentValues contentValues = getGraValues(gra);
        mDatabase.update(GraTable.NAME, contentValues,
                GraTable.Cols.LP + " LIKE ? AND " + GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[] { lpString, rozgrywkaUUID });
    }
    public void deleteRozgrywka(Rozgrywka rozgrywka) {
        mDatabase.delete(RozgrywkaTable.NAME,
                RozgrywkaTable.Cols.UUID + " = ?",
                new String[] {rozgrywka.getUUID().toString()}
        );
        mDatabase.delete(GraTable.NAME,
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[] {rozgrywka.getUUID().toString()}
        );
    }
    public int getLastGra(UUID uuid) {
        int lastGra;
        try (Cursor cursor = mDatabase.query(
                GraTable.NAME,
                new String[]{GraTable.Cols.LP},
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[]{uuid.toString()},
                null, null,
                GraTable.Cols.LP + " ASC"
        )) {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToLast();
                lastGra = cursor.getInt(0);
            } else {
                lastGra = 0;
            }
        }
        return lastGra;
    }
    public int getSummaryWynik(String columnIndex, UUID uuid) {
        int summary = 0;
        try (Cursor cursor = mDatabase.query(GraTable.NAME, new String[]{columnIndex},
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[]{uuid.toString()},
                null, null, null
        )) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                summary += cursor.getInt(0);
                cursor.moveToNext();
            }
        }
        return summary;
    }
    @NotNull
    private RozgrywkaCursorWrapper queryRozgrywka(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(RozgrywkaTable.NAME, null,whereClause,whereArgs,null,null,
                RozgrywkaTable.Cols.DATE + " DESC");
        return new RozgrywkaCursorWrapper(cursor);
    }
    @NotNull
    private GraCursorWrapper queryGra(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(GraTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                GraTable.Cols.LP + " DESC"
                );
        return new GraCursorWrapper(cursor);
    }

    public GraStatus getGraStatus(Gra pGra) {
        int limit = Integer.valueOf(Objects.requireNonNull(PreferenceManager.getDefaultSharedPreferences(mContext).getString("bomb_list", "0")));
        Rozgrywka lRozgrywka = this.getRozgrywka(pGra.getUUIDRozgrywka());
        if (pGra.getLp() < getLastGra(pGra.getUUIDRozgrywka())) return GraStatus.NOBUTTON;
        if ((pGra.getBomba1() + pGra.getBomba2() + pGra.getBomba3() + pGra.getBomba3() > 0) ) return GraStatus.ONLYBOMB;
        if (pGra.getWynik1() + pGra.getWynik2() + pGra.getWynik3() + pGra.getWynik4() == 0) {
            if ( (pGra.getContract1() > 0 && (limit - lRozgrywka.getBomb1()) <= 0) ||
                     (pGra.getContract2() > 0 && (limit - lRozgrywka.getBomb2()) <= 0) ||
                     (pGra.getContract3() > 0 && (limit - lRozgrywka.getBomb3()) <= 0) ||
                     (pGra.getContract4() > 0 && (limit - lRozgrywka.getBomb4()) <= 0)) {  return GraStatus.NOBOMBS;
            } else return  GraStatus.ALLES;
        } else return  GraStatus.ONLYSCORE;
    }
}
