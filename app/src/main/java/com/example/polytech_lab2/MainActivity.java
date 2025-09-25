package com.example.polytech_lab2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polytech_lab2.adapter.TodoAdapter;
import com.example.polytech_lab2.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ReminderDialog.ReminderListener {

    private List<TodoItem> todoList;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextTask = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonReminder = findViewById(R.id.buttonReminder);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        todoList = new ArrayList<>();
        adapter = new TodoAdapter(todoList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            String taskText = editTextTask.getText().toString().trim();
            if (!taskText.isEmpty()) {
                todoList.add(new TodoItem(taskText));
                adapter.notifyItemInserted(todoList.size() - 1);
                editTextTask.setText("");
            }
        });

        buttonReminder.setOnClickListener(v -> {
            ReminderDialog dialog = new ReminderDialog();
            dialog.show(getSupportFragmentManager(), "ReminderDialog");
        });
    }

    @Override
    public void onReminderCreated(TodoItem item) {
        todoList.add(item);
        adapter.notifyItemInserted(todoList.size() - 1);
    }
}

