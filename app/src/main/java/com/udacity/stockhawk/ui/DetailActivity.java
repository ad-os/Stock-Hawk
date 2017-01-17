package com.udacity.stockhawk.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.chart.XAxisValueFormatter;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Cursor mCursor;
    private Uri mUri;
    private List<Entry> mEntries;
    private static final int CHART_LOADER = 1;

    @BindView(R.id.chart)
    LineChart mLineChart;

    private final static String[] PROJECTION_COLUMNS = {
            Contract.Quote._ID,
            Contract.Quote.COLUMN_SYMBOL,
            Contract.Quote.COLUMN_PRICE,
            Contract.Quote.COLUMN_ABSOLUTE_CHANGE,
            Contract.Quote.COLUMN_PERCENTAGE_CHANGE,
            Contract.Quote.COLUMN_HISTORY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mEntries = new ArrayList<>();

        Intent intent = getIntent();
        mUri = intent.getData();
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> loader = loaderManager.getLoader(CHART_LOADER);

        if (loader == null) {
            loaderManager.initLoader(CHART_LOADER, null, this);
        } else {
            loaderManager.restartLoader(CHART_LOADER, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                mUri,
                PROJECTION_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor != null && mCursor.moveToNext()) {
            String history = mCursor.getString(Contract.Quote.POSITION_HISTORY);

            String historyData[] = (history.split("\\n"));

            XAxis xAxis = mLineChart.getXAxis();
            xAxis.setValueFormatter(new XAxisValueFormatter());
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);

            YAxis left = mLineChart.getAxisLeft();
            left.setTextColor(Color.WHITE);
            left.setDrawGridLines(false);
            mLineChart.getAxisRight().setEnabled(false);

            mLineChart.setScaleEnabled(false);
            mLineChart.setPinchZoom(false);
            mLineChart.setBorderColor(Color.WHITE);

            for (String aHistoryData : historyData) {
                String[] temp = aHistoryData.split(",");
                float xCoordinate = Float.parseFloat(temp[0]);
                float yCoordinate = Float.parseFloat(temp[1]);
                mEntries.add(new Entry(xCoordinate, yCoordinate));
            }

            Log.d(TAG, "onCreate: " + mEntries.toString());

            LineDataSet dataSet = new LineDataSet(mEntries, "Stock");
            LineData lineData = new LineData(dataSet);
            mLineChart.setData(lineData);
            mLineChart.setContentDescription(getString(R.string.stock_Description));
            mLineChart.invalidate();
            mLineChart.animateY(1000);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLineChart.invalidate();
    }
}
