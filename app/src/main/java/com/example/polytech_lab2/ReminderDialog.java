package com.example.polytech_lab2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.polytech_lab2.model.TodoItem;

import java.util.Calendar;
import java.util.Date;

public class ReminderDialog extends DialogFragment {

    public interface ReminderListener {
        void onReminderCreated(TodoItem item);
    }

    private EditText editTextReminder;
    private final Calendar calendar = Calendar.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_reminder, null);

        editTextReminder = view.findViewById(R.id.editTextReminder);
        Button buttonDate = view.findViewById(R.id.buttonPickDate);
        Button buttonTime = view.findViewById(R.id.buttonPickTime);

        updateDateButton(buttonDate);
        updateTimeButton(buttonTime);

        buttonDate.setOnClickListener(v -> {
            @SuppressLint("DefaultLocale") DatePickerDialog datePicker = new DatePickerDialog(
                    requireContext(),
                    (dp, year, month, day) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        updateDateButton(buttonDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.show();
        });

        buttonTime.setOnClickListener(v -> {
            @SuppressLint("DefaultLocale") TimePickerDialog timePicker = new TimePickerDialog(
                    requireContext(),
                    (tp, hour, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        updateTimeButton(buttonTime);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            timePicker.show();
        });

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Новое напоминание")
                .setView(view)
                .setPositiveButton("Сохранить", null)
                .setNegativeButton("Отмена", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            saveButton.setOnClickListener(v -> {
                String text = editTextReminder.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    editTextReminder.setError("Введите текст напоминания");
                    return;
                }
                TodoItem reminder = new TodoItem(text);
                reminder.setReminder(new Date(calendar.getTimeInMillis()));
                if (getActivity() instanceof ReminderListener) {
                    ((ReminderListener) getActivity()).onReminderCreated(reminder);
                }
                dialog.dismiss();
            });
        });

        return dialog;
    }

    @SuppressLint("DefaultLocale")
    private void updateDateButton(Button button) {
        button.setText(String.format("%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)));
    }

    @SuppressLint("DefaultLocale")
    private void updateTimeButton(Button button) {
        button.setText(String.format("%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)));
    }
}
