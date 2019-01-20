package eu.godlesie.jgdlws.tysic.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.database.GraCursorWrapper;
import eu.godlesie.jgdlws.tysic.database.GraDbSchema;
import eu.godlesie.jgdlws.tysic.database.RozgrywkaCursorWrapper;
import eu.godlesie.jgdlws.tysic.database.RozgrywkaDbSchema;
import eu.godlesie.jgdlws.tysic.database.TysiacBaseHelper;

import static eu.godlesie.jgdlws.tysic.database.GraDbSchema.*;
import static eu.godlesie.jgdlws.tysic.database.RozgrywkaDbSchema.*;

public class TysiacLab {

    private static TysiacLab sTysiacLab;

    //do bazy danych
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //wartości tabeli rozgrywki
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
    //wartości tabeli gra
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
        //bazy danych
        mContext = context.getApplicationContext();
        mDatabase = new TysiacBaseHelper(mContext).getWritableDatabase();

    }
    public static TysiacLab get(Context context) {
        if (sTysiacLab == null) {
            sTysiacLab = new TysiacLab(context);
        }
        return sTysiacLab;
    }

    public Rozgrywka getRozgrywka(UUID uuid) {
        RozgrywkaCursorWrapper cursor = queryRozgrywka(
                RozgrywkaTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() }
        );
        try {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            return cursor.getRozgrywka();
        } finally {
            cursor.close();
        }
    }
    public Gra getGra(UUID uuid,int lp) {
        String whereClause = GraTable.Cols.ROZGRYWKA_UUID + " = ? AND  " + GraTable.Cols.LP + " = ?";
        GraCursorWrapper cursor = queryGra(
                GraTable.Cols.ROZGRYWKA_UUID + " = ? AND  " + GraTable.Cols.LP + " LIKE ?",
                new String[] { uuid.toString(), String.valueOf(lp)}
        );
        try {
            int ccc = cursor.getCount();
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            return cursor.getGra();
        } finally {
            cursor.close();
        }
    }

    public List<Rozgrywka> getRozgrywki() {
        List<Rozgrywka> rozgrywki = new ArrayList<>();
        RozgrywkaCursorWrapper cursor = queryRozgrywka(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rozgrywki.add(cursor.getRozgrywka());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return rozgrywki;
    }

    public List<Gra> getGry(UUID uuid) {
        List<Gra> gry = new ArrayList<>();
        GraCursorWrapper cursor = queryGra(
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[] { uuid.toString() });
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                gry.add(cursor.getGra());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
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
    public void deleteGra(Gra gra) {

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
        int lastGra = 0;
        Cursor cursor = mDatabase.query(
                GraTable.NAME,
                new String[] { GraTable.Cols.LP },
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[] { uuid.toString() },
                null,null,
                GraTable.Cols.LP + " ASC"
                );

        try {
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToLast();
                lastGra = cursor.getInt(0);
            } else {
                lastGra = 0;
            }
        } finally {
            cursor.close();
        }
        return lastGra;
    }
    public int getSummaryWynik(String columnIndex, UUID uuid) {
        int summary = 0;
        Cursor cursor = mDatabase.query(GraTable.NAME, new String[] {columnIndex},
                GraTable.Cols.ROZGRYWKA_UUID + " = ?",
                new String[] {uuid.toString()},
                null,null,null
                );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                summary += cursor.getInt(0);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return summary;
    }

    private RozgrywkaCursorWrapper queryRozgrywka(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(RozgrywkaTable.NAME, null,whereClause,whereArgs,null,null,
                RozgrywkaTable.Cols.DATE + " DESC");
        return new RozgrywkaCursorWrapper(cursor);
    }
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
}
