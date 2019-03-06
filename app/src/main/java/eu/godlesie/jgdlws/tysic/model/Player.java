package eu.godlesie.jgdlws.tysic.model;

import org.jetbrains.annotations.NotNull;

public class Player {
    private final String mName;
    private final int mContract;
    private final int mBomba;
    private final int mScore;

    public static class Builder {
        private final String mName;
        private int mContract = 0;
        private int mBomba = 0;
        private int mScore = 0;
        public Builder contract(int pContract) { this.mContract = pContract; return this; }
        public Builder bomba(int pBomba) { this.mBomba = pBomba; return  this; }
        public Builder score(int pScore) { this.mScore = pScore; return  this; }
        public Builder(String pName) {  mName = pName; }
        public Player build() { return new Player(this); }
    }
    private Player(@NotNull Builder pBuilder) {
        this.mName = pBuilder.mName;
        this.mContract = pBuilder.mContract;
        this.mBomba = pBuilder.mBomba;
        this.mScore = pBuilder.mScore;
    }

    public String getName() {
        return mName;
    }

    public int getContract() {
        return mContract;
    }

    public int getBomba() {
        return mBomba;
    }

    public int getScore() {
        return mScore;
    }
}
