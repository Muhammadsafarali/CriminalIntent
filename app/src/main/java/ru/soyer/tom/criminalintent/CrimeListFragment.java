package ru.soyer.tom.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by elenaozerova on 25/05/2018.
 */

public class CrimeListFragment extends Fragment {

    private static final String LOG_TAG = "CrimeListFragment";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private static int updateRowIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(updateRowIndex);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime, int position) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mTitleTextView.setTag(position);
//            mTitleTextView.setTag(position);
//            getView().setTag(position);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Date date = sdf.parse(mCrime.getDate().toString());
                SimpleDateFormat print = new SimpleDateFormat("E, MMM dd, yyyy");
//                Log.e(LOG_TAG, "Date: " + print.format(date));
                mDateTextView.setText(print.format(date).toString());
//                String date = df.parse(mCrime.getDate().toString()).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            updateRowIndex = (int) mTitleTextView.getTag();
//            Log.e(LOG_TAG, "index: " + (int)mTitleTextView.getTag());
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeHolder2 extends RecyclerView.ViewHolder {

        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mButton;

        public CrimeHolder2(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime2, parent, false));

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mButton = itemView.findViewById(R.id.crime_btn);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2 * 2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            switch (viewType) {
                case 0: return new CrimeHolder(layoutInflater, parent);
                case 2: return new CrimeHolder2(layoutInflater, parent);
                default: return new CrimeHolder(layoutInflater, parent);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0: CrimeHolder viewHolder1 = (CrimeHolder) holder;
                    Crime crime = mCrimes.get(position);
                    viewHolder1.bind(crime, position);
                    break;
                case 2: CrimeHolder2 viewHolder2 = (CrimeHolder2) holder;
                    Crime crime1 = mCrimes.get(position);
                    viewHolder2.bind(crime1);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
