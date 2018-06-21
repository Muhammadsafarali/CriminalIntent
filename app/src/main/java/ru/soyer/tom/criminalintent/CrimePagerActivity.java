package ru.soyer.tom.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by elenaozerova on 18/06/2018.
 */

public class CrimePagerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_CRIME_ID = "ru.soyer.tom.criminalintent.crime_id";


    private Button btnLeft;
    private Button btnRight;
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        btnLeft = findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(this);

        btnRight = findViewById(R.id.btn_right);
        btnRight.setOnClickListener(this);

        mViewPager = findViewById(R.id.crime_view_pager);
        mViewPager.setPadding(16, 16, 16,16);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if (position > 0 && position < mCrimes.size()-1) {
                    btnLeft.setEnabled(true);
                    btnRight.setEnabled(true);
                }

                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }


        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left: {
                mViewPager.setCurrentItem(0);
                btnLeft.setEnabled(false);
            } break;
            case R.id.btn_right: {
                mViewPager.setCurrentItem(mCrimes.size()-1);
                btnRight.setEnabled(false);
            } break;
        }
    }
}
