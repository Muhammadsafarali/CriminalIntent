package ru.soyer.tom.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by elenaozerova on 25/05/2018.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
