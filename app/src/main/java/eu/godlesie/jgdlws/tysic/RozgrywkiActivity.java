package eu.godlesie.jgdlws.tysic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class RozgrywkiActivity extends AppCompatActivity {

    private static final String DIALOG_ADD_ROZGRYWKA = "dialog_add_rozgrywka";
    public static final int REQUEST_ROZGRYWKA_ADD_DIALOG = 0;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozgrywki);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(R.id.container_rozgrywki);
        if (mFragment == null) {
            mFragment = new RozgrywkiFragment();
            fm.beginTransaction()
                    .add(R.id.container_rozgrywki,mFragment)
                    .commit();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            FragmentManager manager = getSupportFragmentManager();
            RozgrywkaAddDialog dialog = new RozgrywkaAddDialog();
            dialog.setTargetFragment(mFragment, REQUEST_ROZGRYWKA_ADD_DIALOG);
            dialog.show(manager,DIALOG_ADD_ROZGRYWKA);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rozgrywki, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), TysiacSettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
