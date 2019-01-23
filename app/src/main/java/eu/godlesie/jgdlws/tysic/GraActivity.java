package eu.godlesie.jgdlws.tysic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.UUID;

public class GraActivity extends AppCompatActivity {

    private static final String DIALOG_ADD_GRA = "dialog_add_gra";
    public static final int ADD_GRA_DIALOG = 0;
    public static final String ARGS_NUM_ROZGRYWKA = "args_num_rozgrywka";

    private Fragment mFragment;

    private UUID mUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra);

        mUUID = (UUID) getIntent().getSerializableExtra(RozgrywkiFragment.EXTRA_ROZGRYWKA_UUID);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(R.id.container_gry);
        if (mFragment == null) {
            mFragment = new GraFragment();
            fm.beginTransaction()
                    .add(R.id.container_gry,mFragment)
                    .commit();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> {
            GraAddDialog dialog = new GraAddDialog();
            Bundle args = new Bundle();
            args.putSerializable(ARGS_NUM_ROZGRYWKA,mUUID);
            dialog.setArguments(args);
            dialog.setTargetFragment(mFragment, ADD_GRA_DIALOG);
            dialog.show(fm,DIALOG_ADD_GRA);
        });

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
