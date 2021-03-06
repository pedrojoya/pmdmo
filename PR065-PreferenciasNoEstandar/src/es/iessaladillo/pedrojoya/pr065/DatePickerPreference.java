package es.iessaladillo.pedrojoya.pr065;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerPreference extends DialogPreference implements
        DatePicker.OnDateChangedListener {
    private DatePicker datePicker;
    private Date pickedDate;
    private Date today;

    public DatePickerPreference(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public DatePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle(getTitle());
        builder.setMessage(getDialogMessage());
        super.onPrepareDialogBuilder(builder);
    }

    @SuppressLint("NewApi")
    @Override
    protected View onCreateDialogView() {
        this.datePicker = new DatePicker(getContext());
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
            datePicker.setCalendarViewShown(false);
        }
        setToday();
        return datePicker;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month,
            int day) {
        Calendar selected = new GregorianCalendar(year, month, day);
        pickedDate = new Date(selected.getTimeInMillis());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which);
        datePicker.clearFocus();
        onDateChanged(datePicker, datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        onDialogClosed(which == DialogInterface.BUTTON1);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (!positiveResult)
            return;
        // sharedPreferences();
        String fecha = formatter().format(pickedDate);
        persistString(fecha);
        super.onDialogClosed(positiveResult);
    }

    private void setToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        today = new Date(c.getTimeInMillis());
    }

    private SimpleDateFormat formatter() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }

    // private void sharedPreferences() {
    // SharedPreferences preferences = PreferenceManager
    // .getDefaultSharedPreferences(getContext());
    // SharedPreferences.Editor editor = preferences.edit();
    // editor.putString(PROFILE_INFO_BIRTHDAY, getDate());
    // editor.commit();
    // }

    // private String getDate() {
    // // return dash "-" as this is default value of the YEAR, MONTH, DAY drop
    // // downs on the website
    // return dateInPast() ? formatter().format(pickedDate) : "-";
    // }
    //
    // private boolean dateInPast() {
    // return pickedDate.getTime() < today.getTime();
    // }
}