package com.simona.easytravel1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AfisareCalendar extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();
        int anul = cal.get(Calendar.YEAR);
        int luna = cal.get(Calendar.MONTH);
        int ziua = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener) getActivity(), anul, luna, ziua);

        dpd
                .getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dpd;

    }


}
