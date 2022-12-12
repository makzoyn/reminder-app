package com.example.myreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addRemindBtn;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> tasks = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // достаем данные из sp
        databaseHelper = new DatabaseHelper(this);
        tasks = databaseHelper.getTasks();

        addRemindBtn = findViewById(R.id.addRemindButton);
        addRemindBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycleView);
        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tasks.remove(position);
                adapter.notifyItemChanged(position);
                databaseHelper.saveRemind(tasks);

            }
        });

    }
    //Обработка клика по кнопке создать напоминание
    @Override
    public void onClick(View view) {
        if(view == addRemindBtn)
        {
            goToCreateRemindActivity();
        }
    }
    //функция для перехода в CreateRemind activity
    private void goToCreateRemindActivity()
    {
        Intent intent = new Intent(getApplicationContext(), CreateRemind.class);
        startActivity(intent);
    }
}