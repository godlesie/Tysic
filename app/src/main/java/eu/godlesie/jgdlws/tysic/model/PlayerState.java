package eu.godlesie.jgdlws.tysic.model;

import java.util.function.Predicate;

public class PlayerState {
    private final Player mPlayer;
    private final int maxBomb;

    private PlayerState(Builder pBuilder) {
        mPlayer = pBuilder.player;
        maxBomb = pBuilder.maxBomb;
    }

    public boolean isBomba() {
        return mPlayer.getBomba() >= maxBomb ? false : true;
    }

    public static class Builder {
        private final Player player;
        private int maxBomb = 0; Builder maxBomb(int value) { maxBomb = value; return this; }

        public Builder(Player pPlayer) {
            player = pPlayer;
        }
        public PlayerState build() {
            return new PlayerState(this);
        }
    }
}
