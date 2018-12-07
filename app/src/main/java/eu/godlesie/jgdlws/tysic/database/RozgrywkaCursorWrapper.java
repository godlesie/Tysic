package eu.godlesie.jgdlws.tysic.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Rozgrywka;

public class RozgrywkaCursorWrapper extends CursorWrapper {
    public RozgrywkaCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Rozgrywka getRozgrywka() {
        String uuidString = getString(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.UUID));

        String player1 = getString(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_1));
        String player2 = getString(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_2));
        String player3 = getString(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_3));
        String player4 = getString(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.PLAYER_4));

        int wynik1 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_1));
        int wynik2 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_2));
        int wynik3 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_3));
        int wynik4 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.WYNIK_4));

        int bomba1 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_1));
        int bomba2 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_2));
        int bomba3 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_3));
        int bomba4 = getInt(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.BOMBA_4));

        long date = getLong(getColumnIndex(RozgrywkaDbSchema.RozgrywkaTable.Cols.DATE));

        Rozgrywka rozgrywka = new Rozgrywka(UUID.fromString(uuidString));
        rozgrywka.setPlayer1(player1);
        rozgrywka.setPlayer2(player2);
        rozgrywka.setPlayer3(player3);
        rozgrywka.setPlayer4(player4);

        rozgrywka.setWynik1(wynik1);
        rozgrywka.setWynik2(wynik2);
        rozgrywka.setWynik3(wynik3);
        rozgrywka.setWynik4(wynik4);

        rozgrywka.setBomb1(bomba1);
        rozgrywka.setBomb2(bomba2);
        rozgrywka.setBomb3(bomba3);
        rozgrywka.setBomb4(bomba4);

        rozgrywka.setDate(new Date(date));

        return rozgrywka;
    }
}
