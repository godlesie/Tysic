package eu.godlesie.jgdlws.tysic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TysiacBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "tysiac";

    public TysiacBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +
                RozgrywkaDbSchema.RozgrywkaTable.NAME + " ( " +
                "_id integer primary key autoincrement, " +

                RozgrywkaDbSchema.RozgrywkaTable.Cols.DATE + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_1 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_2 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_3 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_4 + ", " +

                RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_1 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_2 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_3 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_4 + ", " +

                RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_1 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_2 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_3 + ", " +
                RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_4 + ", " +

                RozgrywkaDbSchema.RozgrywkaTable.Cols.UUID +
                ")"
        );
        db.execSQL("create table " + GraDbSchema.GraTable.NAME + "( " +
                "_id integer primary key autoincrement, " +

                GraDbSchema.GraTable.Cols.LP + ", " +

                GraDbSchema.GraTable.Cols.CONTRACT_1 + ", " +
                GraDbSchema.GraTable.Cols.CONTRACT_2 + ", " +
                GraDbSchema.GraTable.Cols.CONTRACT_3 + ", " +
                GraDbSchema.GraTable.Cols.CONTRACT_4 + ", " +

                GraDbSchema.GraTable.Cols.WYNIK_1 + ", " +
                GraDbSchema.GraTable.Cols.WYNIK_2 + ", " +
                GraDbSchema.GraTable.Cols.WYNIK_3 + ", " +
                GraDbSchema.GraTable.Cols.WYNIK_4 + ", " +

                GraDbSchema.GraTable.Cols.BOMBA_1 + ", " +
                GraDbSchema.GraTable.Cols.BOMBA_2 + ", " +
                GraDbSchema.GraTable.Cols.BOMBA_3 + ", " +
                GraDbSchema.GraTable.Cols.BOMBA_4 + ", " +

                GraDbSchema.GraTable.Cols.ROZGRYWKA_UUID +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
