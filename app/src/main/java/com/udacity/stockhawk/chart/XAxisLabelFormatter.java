package com.udacity.stockhawk.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by adhyan on 1/17/17.
 */

public class XAxisLabelFormatter implements IAxisValueFormatter {
    private SimpleDateFormat mSimpleDateFormat;
    private Long referenceTime;
    private DecimalFormat mDecimalFormat;

    public XAxisLabelFormatter(Long referenceTime) {
        mSimpleDateFormat = new SimpleDateFormat("dd/MM");
        this.referenceTime = referenceTime;
        mDecimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        value = Float.parseFloat(mDecimalFormat.format(value*1000*60*60));
        return mSimpleDateFormat.format(
                value + referenceTime
        );
    }
}
