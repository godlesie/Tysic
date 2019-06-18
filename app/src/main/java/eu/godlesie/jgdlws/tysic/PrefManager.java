package eu.godlesie.jgdlws.tysic;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "tysiac";
    private static final String IS_FIRST_LAUNCH = "isFirstLaunch";

    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }
    public void setIsFirstLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_LAUNCH,true);
    }
    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }
}
