package com.example.ehgezli;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CustomerServiceReservation extends AppCompatActivity {
private Button btn;
    private FirebaseDatabase fire;
    private CalendarView date;
    private DatabaseReference ref , ref2;
    private static   BankDate dateRet = new BankDate();
    private static BankDate dateSet = new BankDate();
    private Calendar calenderTommorow = Calendar.getInstance();
    private Calendar calenderWeekAfter = Calendar.getInstance();
    private FirebaseUser user;
    private  String userId,branchId,operationId;
    private int time;
    private Boolean IsReserved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerservice_reservation);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        branchId =  getIntent().getStringExtra("BranchNumber");
        operationId = getIntent().getStringExtra("OperationNumber");
        btn = findViewById(R.id.button);
        fire = FirebaseDatabase.getInstance();
        date = findViewById(R.id.calendarView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        ref = fire.getReference(branchId).child("CustomerService");
        ref2 = fire.getReference("Reservations").child(userId);
        IsReserved = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("تحذير");
        builder2.setIcon(android.R.drawable.ic_dialog_alert);
        builder2.setMessage("يرجى العلم بأنه سوف يتم خصم 50 جنية فى حالة عدم الحضور");
        if(operationId.equals("60") || operationId.equals("66") || operationId.equals("70") ){
            time = 10;
        }
        else if(operationId.equals("61") || operationId.equals("62") || operationId.equals("67") || operationId.equals("68") || operationId.equals("69")  ){
            time = 15;
        }
        else if(operationId.equals("58") || operationId.equals("71")){
            time = 20;
        }
        else if(operationId.equals("57") || operationId.equals("59")  || operationId.equals("65") || operationId.equals("72") ){
            time = 30;
        }
        else if(operationId.equals("63") || operationId.equals("64") ){
            time = 45;
        }
        SetTommorowTime();
        String DbTitle = String.valueOf(dateSet.Day)+String.valueOf(dateSet.Month)+String.valueOf(dateSet.Year);
        GetDate(DbTitle);
        Calendar calender = Calendar.getInstance();
        Calendar lastSelectedCalendar = Calendar.getInstance();
        Context context = getApplicationContext();
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar checkCalendar = Calendar.getInstance();
                checkCalendar.set(year, month, dayOfMonth);
                if(checkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY || checkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){

                    date.setDate(calenderTommorow.getTimeInMillis());
                    dateSet.Day = calenderTommorow.get(Calendar.DAY_OF_MONTH);
                    dateSet.Month = calenderTommorow.get(Calendar.MONTH);
                    dateSet.Year = calenderTommorow.get(Calendar.YEAR);
                    Toast.makeText(context, "عفوا لا يمكنك الحجز فى العطلات الرسمية", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateSet.Day = dayOfMonth;
                    dateSet.Month = month;
                    dateSet.Year = year;
                }
                String DbTitle = String.valueOf(dateSet.Day)+String.valueOf(dateSet.Month)+String.valueOf(dateSet.Year);
                GetDate(DbTitle);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DbTitle = String.valueOf(dateSet.Day) + String.valueOf(dateSet.Month) + String.valueOf(dateSet.Year);
                if(IsReserved.equals(false)){
                if (dateRet.Day == 0 && dateRet.Month == 0 && dateRet.Year == 0) {
                    builder.setTitle("تأكيد");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("حجزك " + dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - 9:00" + " هل تريد التأكيد ؟");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            ref.child(DbTitle).child("Day").setValue(dateSet.Day);
                            ref.child(DbTitle).child("Month").setValue(dateSet.Month);
                            ref.child(DbTitle).child("Year").setValue(dateSet.Year);
                            ref.child(DbTitle).child("Hour").setValue(9);
                            ref.child(DbTitle).child("Minute").setValue(0);
                            ref2.child(DbTitle).child("Operation").setValue("Customer Service");
                            ref2.child(DbTitle).child("Date").setValue(dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - 9:00");
                            builder2.setNegativeButton(android.R.string.yes, null).show();
                            Toast.makeText(context, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                        }
                    })
                            .setNegativeButton(android.R.string.no, null).show();
                } else {
                    if (dateRet.Minute + time < 60) {
                        int minuteSet = dateRet.Minute + time;
                        builder.setTitle("تأكيد");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setMessage("حجزك " + dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - " +
                                dateRet.Hour + ":" + dateRet.Minute + " هل تريد التأكيد ؟");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ref.child(DbTitle).child("Day").setValue(dateSet.Day);
                                ref.child(DbTitle).child("Month").setValue(dateSet.Month);
                                ref.child(DbTitle).child("Year").setValue(dateSet.Year);
                                ref.child(DbTitle).child("Hour").setValue(dateRet.Hour);
                                ref.child(DbTitle).child("Minute").setValue(minuteSet);
                                ref2.child(DbTitle).child("Operation").setValue("Customer Service");
                                ref2.child(DbTitle).child("Date").setValue(dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - " +
                                        dateRet.Hour + ":" + dateRet.Minute);
                                builder2.setNegativeButton(android.R.string.yes, null).show();
                                Toast.makeText(context, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .setNegativeButton(android.R.string.no, null).show();
                    } else {
                        if (dateRet.Hour + 1 < 14) {
                            int hourSet = dateRet.Hour + 1;
                            int minuteSet = time - (60 - dateRet.Minute);
                            builder.setTitle("تأكيد");
                            builder.setIcon(android.R.drawable.ic_dialog_alert);
                            builder.setMessage("حجزك " + dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - " +
                                    dateRet.Hour + ":" + dateRet.Minute + " هل تريد التأكيد ؟");
                            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ref.child(DbTitle).child("Day").setValue(dateSet.Day);
                                    ref.child(DbTitle).child("Month").setValue(dateSet.Month);
                                    ref.child(DbTitle).child("Year").setValue(dateSet.Year);
                                    ref.child(DbTitle).child("Hour").setValue(hourSet);
                                    ref.child(DbTitle).child("Minute").setValue(minuteSet);
                                    ref2.child(DbTitle).child("Operation").setValue("Customer Service");
                                    ref2.child(DbTitle).child("Date").setValue(dateSet.Day + "/" + dateSet.Month + "/" + dateSet.Year + " - " +
                                            dateRet.Hour + ":" + dateRet.Minute);
                                    builder2.setNegativeButton(android.R.string.yes, null).show();
                                    Toast.makeText(context, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                                }
                            })
                                    .setNegativeButton(android.R.string.no, null).show();
                        } else {
                            Toast.makeText(context, "عفوا لا يوجد مواعيد متاحة هذا اليوم", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
                else {
                    Toast.makeText(context, "عفوا لقد قمت مسبقا بالحجز فى نفس اليوم", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public void SetTommorowTime(){

        calenderTommorow.add(Calendar.DATE,1);
        calenderWeekAfter.add(Calendar.DATE,7);
        date.setMinDate(calenderTommorow.getTimeInMillis());
        date.setMaxDate(calenderWeekAfter.getTimeInMillis());

        if(calenderTommorow.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY || calenderTommorow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
            calenderTommorow.add(Calendar.DATE,1);
            date.setMinDate(calenderTommorow.getTimeInMillis());
            date.setDate(calenderTommorow.getTimeInMillis());
            if(calenderTommorow.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY || calenderTommorow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) {
                calenderTommorow.add(Calendar.DATE,1);
                date.setMinDate(calenderTommorow.getTimeInMillis());
                date.setDate(calenderTommorow.getTimeInMillis());
            }
                dateSet.Day = calenderTommorow.get(Calendar.DAY_OF_MONTH);
                dateSet.Month = calenderTommorow.get(Calendar.MONTH);
                dateSet.Year = calenderTommorow.get(Calendar.YEAR);
        }
        else {
            dateSet.Day = calenderTommorow.get(Calendar.DAY_OF_MONTH);
            dateSet.Month = calenderTommorow.get(Calendar.MONTH);
            dateSet.Year = calenderTommorow.get(Calendar.YEAR);
            dateSet.Hour = calenderTommorow.get(Calendar.HOUR);
            dateSet.Minute = calenderTommorow.get(Calendar.MINUTE);
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void GetDate(String DbTitle) {
        IsReserved = false;
        ref.child(DbTitle).child("Day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String child = String.valueOf(snapshot.getValue());
                    dateRet.Day = Integer.parseInt(child);
                } catch (Exception e) {
                    dateRet.Day = 0;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(DbTitle).child("Month").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String child = String.valueOf(snapshot.getValue());
                    dateRet.Month = Integer.parseInt(child);
                } catch (Exception e) {
                    dateRet.Month = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(DbTitle).child("Year").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String child = String.valueOf(snapshot.getValue());
                    dateRet.Year = Integer.parseInt(child);
                } catch (Exception e) {
                    dateRet.Year = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(DbTitle).child("Hour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String child = String.valueOf(snapshot.getValue());
                    dateRet.Hour = Integer.parseInt(child);
                } catch (Exception e) {
                    dateRet.Hour = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(DbTitle).child("Minute").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String child = String.valueOf(snapshot.getValue());
                    dateRet.Minute = Integer.parseInt(child);
                } catch (Exception e) {
                    dateRet.Minute = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.hasChild(DbTitle)) {
                        IsReserved = true;
                    }
                    else {
                        IsReserved = false;
                    }
                } catch (Exception e) {
                    IsReserved = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
