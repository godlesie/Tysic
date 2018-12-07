package eu.godlesie.jgdlws.tysic.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import eu.godlesie.jgdlws.tysic.model.Gra;

import static eu.godlesie.jgdlws.tysic.database.GraDbSchema.*;

public class GraCursorWrapper extends CursorWrapper {

    public GraCursorWrapper(Cursor cursor) { super(cursor); }

    public Gra getGra() {
        int lp = getInt(getColumnIndex(GraTable.Cols.LP));
        String uuidRozgrywka = getString(getColumnIndex(GraTable.Cols.ROZGRYWKA_UUID));

        int contract1 = getInt(getColumnIndex(GraTable.Cols.CONTRACT_1));
        int contract2 = getInt(getColumnIndex(GraTable.Cols.CONTRACT_2));
        int contract3 = getInt(getColumnIndex(GraTable.Cols.CONTRACT_3));
        int contract4 = getInt(getColumnIndex(GraTable.Cols.CONTRACT_4));

        int wynik1 = getInt(getColumnIndex(GraTable.Cols.WYNIK_1));
        int wynik2 = getInt(getColumnIndex(GraTable.Cols.WYNIK_2));
        int wynik3 = getInt(getColumnIndex(GraTable.Cols.WYNIK_3));
        int wynik4 = getInt(getColumnIndex(GraTable.Cols.WYNIK_4));

        int bomba1 = getInt(getColumnIndex(GraTable.Cols.BOMBA_1));
        int bomba2 = getInt(getColumnIndex(GraTable.Cols.BOMBA_2));
        int bomba3 = getInt(getColumnIndex(GraTable.Cols.BOMBA_3));
        int bomba4 = getInt(getColumnIndex(GraTable.Cols.BOMBA_4));

        Gra gra = new Gra(UUID.fromString(uuidRozgrywka));
        gra.setContract1(contract1);
        gra.setContract2(contract2);
        gra.setContract3(contract3);
        gra.setContract4(contract4);

        gra.setWynik1(wynik1);
        gra.setWynik2(wynik2);
        gra.setWynik3(wynik3);
        gra.setWynik4(wynik4);

        gra.setBomba1(bomba1);
        gra.setBomba2(bomba2);
        gra.setBomba3(bomba3);
        gra.setBomba4(bomba4);

        return null;
    }
}
