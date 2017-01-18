package com.udacity.stockhawk.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by adhyan on 1/19/17.
 */

public class YAxisLabelFormatter implements IAxisValueFormatter{

    DecimalFormat mDecimalFormat;

    public YAxisLabelFormatter() {
        mDecimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mDecimalFormat.format(value) + "$";
    }
}
