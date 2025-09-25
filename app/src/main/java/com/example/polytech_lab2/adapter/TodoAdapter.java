package com.example.polytech_lab2.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polytech_lab2.R;
import com.example.polytech_lab2.model.TodoItem;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<TodoItem> todoList;

    public TodoAdapter(List<TodoItem> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem item = todoList.get(position);

        holder.checkBoxDone.setOnCheckedChangeListener(null);

        holder.textViewTask.setText(item.getTitle());
        holder.checkBoxDone.setChecked(item.isDone());

        if (item.isDone()) {
            holder.textViewTask.setPaintFlags(
                    holder.textViewTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.textViewTask.setPaintFlags(
                    holder.textViewTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }

        holder.checkBoxDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setDone(isChecked);
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.buttonDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                todoList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, todoList.size());
            }
        });

        if (item.getReminder() != null) {
            @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
            holder.textViewReminder.setText(sdf.format(item.getReminder()));
            holder.textViewReminder.setVisibility(View.VISIBLE);
        } else {
            holder.textViewReminder.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxDone;
        TextView textViewTask;
        Button buttonDelete;

        TextView textViewReminder;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxDone = itemView.findViewById(R.id.checkBoxDone);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewReminder = itemView.findViewById(R.id.textViewReminder);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

    }
}
