package eu.godlesie.jgdlws.tysic.database;

public class RozgrywkaDbSchema {
    public static final class RozgrywkaTable {
        public static final String NAME = "rozgrywka";

        public static final class Cols {
            public static final String UUID = "uuid";

            public static final String PLAYER_1 = "player1";
            public static final String PLAYER_2 = "player2";
            public static final String PLAYER_3 = "player3";
            public static final String PLAYER_4 = "player4";

            public static final String WYNIK_1 = "wynik1";
            public static final String WYNIK_2 = "wynik2";
            public static final String WYNIK_3 = "wynik3";
            public static final String WYNIK_4 = "wynik4";

            public static final String BOMBA_1 = "bomba_1";
            public static final String BOMBA_2 = "bomba_2";
            public static final String BOMBA_3 = "bomba_3";
            public static final String BOMBA_4 = "bomba_4";

            public static final String DATE = "date";
        }
    }
}
