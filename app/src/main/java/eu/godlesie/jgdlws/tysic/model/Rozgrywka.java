package eu.godlesie.jgdlws.tysic.model;

import com.fasterxml.uuid.Generators;

import java.util.Date;
import java.util.UUID;

public class Rozgrywka {
    private UUID mUUID;
    private String mPlayer1, mPlayer2, mPlayer3, mPlayer4 = "";
    private int mWynik1, mWynik2, mWynik3, mWynik4 = 0;
    private int mBomb1,mBomb2,mBomb3,mBomb4 = 0;
    private Date mDate;

    public Rozgrywka() {
        //this(UUID.randomUUID());
        this(Generators.timeBasedGenerator().generate());
    }

    public Rozgrywka(UUID id) {
        mUUID = id;
        mDate = new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getPlayer1() {
        return mPlayer1;
    }

    public void setPlayer1(String player1) {
        mPlayer1 = player1;
    }

    public String getPlayer2() {
        return mPlayer2;
    }

    public void setPlayer2(String player2) {
        mPlayer2 = player2;
    }

    public String getPlayer3() {
        return mPlayer3;
    }

    public void setPlayer3(String player3) {
        mPlayer3 = player3;
    }

    public String getPlayer4() {
        return mPlayer4;
    }

    public void setPlayer4(String player4) {
        mPlayer4 = player4;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getBomb1() {
        return mBomb1;
    }

    public void setBomb1(int bomb1) {
        mBomb1 = bomb1;
    }

    public int getBomb2() {
        return mBomb2;
    }

    public void setBomb2(int bomb2) {
        mBomb2 = bomb2;
    }

    public int getBomb3() {
        return mBomb3;
    }

    public void setBomb3(int bomb3) {
        mBomb3 = bomb3;
    }

    public int getBomb4() {
        return mBomb4;
    }

    public void setBomb4(int bomb4) {
        mBomb4 = bomb4;
    }
}
