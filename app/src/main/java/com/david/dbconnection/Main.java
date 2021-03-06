package com.david.dbconnection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;

    final int MAX_DIAS_VACACIONES = 30;

    public Context context;
    public DBHolydays dbHolydays;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        // Inicializa la BDD
        dbHolydays = new DBHolydays(this);
        user = "David";

        // Inicializa el DatePicker (se podría hacer con un DatePickerDialog)
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR); // Año actual
        int month = c.get(java.util.Calendar.MONTH); // Mes actual
        int day = c.get(java.util.Calendar.DAY_OF_MONTH); // Día actual

        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            // El callback devuelve la fecha seleccionada en el DatePicker
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                dbHolydays.insertIntoHolydays(selectedDate, user);
                Vector<String> holydays = dbHolydays.getFromHolydays(user);
                Toast t = Toast.makeText(context, holydays.toString(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    @OnClick (R.id.button)
    public void deleteFromDatabase (View view) {
        DBHolydays dbHolydays = new DBHolydays(context);
        dbHolydays.deleteFromHolydays(user);
    }
}
