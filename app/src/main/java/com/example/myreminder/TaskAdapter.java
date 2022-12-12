package com.example.myreminder;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    ArrayList<Task> tasks;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public TaskAdapter(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
    }
    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskDate,taskTime,remindText;
        ImageView deleteTask;
        public TaskViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            taskDate = itemView.findViewById(R.id.taskDate);
            taskTime = itemView.findViewById(R.id.taskTime);
            remindText = itemView.findViewById(R.id.remindText);
            deleteTask = itemView.findViewById(R.id.delete_task);

            deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }


    }
    @NonNull
    @Override //создание объекта представления задачи
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new TaskViewHolder(itemView, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.remindText.setText(task.getText());
        holder.taskTime.setText(task.getTime());
        holder.taskDate.setText(task.getDate());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}
