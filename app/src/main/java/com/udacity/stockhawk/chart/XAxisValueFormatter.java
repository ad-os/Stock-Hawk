package com.udacity.stockhawk.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;

/**
 * Created by adhyan on 1/17/17.
 */

public class XAxisValueFormatter implements IAxisValueFormatter {
    private SimpleDateFormat mSimpleDateFormat;

    public XAxisValueFormatter() {
        mSimpleDateFormat = new SimpleDateFormat("MM/yy");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mSimpleDateFormat.format(value);
    }
}
