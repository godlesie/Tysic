package eu.godlesie.jgdlws.tysic.model;

import java.util.UUID;

public  class Gra {
    private int mLp = 0;
    private UUID mUUIDRozgrywka;
    private int mContract1,mContract2,mContract3,mContract4;
    private int mWynik1,mWynik2,mWynik3,mWynik4;
    private int mBomba1,mBomba2,mBomba3,mBomba4;

    public  Gra(UUID uuid) {
        mWynik1 = mWynik2 = mWynik3 = mWynik4 = 0;
        mContract1 = mContract2 = mContract3 = mContract4 = 0;
        mBomba1 = mBomba2 = mBomba3 = mBomba4 = 0;
        mUUIDRozgrywka = uuid;
    }

    public int getLp() {
        return mLp;
    }

    public void setLp(int lp) {
        mLp = lp;
    }

    UUID getUUIDRozgrywka() { return mUUIDRozgrywka; }

    public int getContract1() {
        return mContract1;
    }

    public void setContract1(int contract1) {
        mContract1 = contract1;
    }

    public int getContract2() {
        return mContract2;
    }

    public void setContract2(int contract2) {
        mContract2 = contract2;
    }

    public int getContract3() {
        return mContract3;
    }

    public void setContract3(int contract3) {
        mContract3 = contract3;
    }

    public int getContract4() {
        return mContract4;
    }

    public void setContract4(int contract4) {
        mContract4 = contract4;
    }

    public int getWynik1() {
        return mWynik1;
    }

    public void setWynik1(int wynik1) {
        mWynik1 = wynik1;
    }

    public int getWynik2() {
        return mWynik2;
    }

    public void setWynik2(int wynik2) {
        mWynik2 = wynik2;
    }

    public int getWynik3() {
        return mWynik3;
    }

    public void setWynik3(int wynik3) {
        mWynik3 = wynik3;
    }

    public int getWynik4() {
        return mWynik4;
    }

    public void setWynik4(int wynik4) {
        mWynik4 = wynik4;
    }

    public int getBomba1() {
        return mBomba1;
    }

    public void setBomba1(int bomba1) {
        mBomba1 = bomba1;
    }

    public int getBomba2() {
        return mBomba2;
    }

    public void setBomba2(int bomba2) {
        mBomba2 = bomba2;
    }

    public int getBomba3() {
        return mBomba3;
    }

    public void setBomba3(int bomba3) {
        mBomba3 = bomba3;
    }

    public int getBomba4() {
        return mBomba4;
    }

    public void setBomba4(int bomba4) {
        mBomba4 = bomba4;
    }

}
