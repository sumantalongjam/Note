package com.treebo.note.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.squareup.otto.Subscribe;
import com.treebo.note.Adapters.TaskRecyclerAdapter;
import com.treebo.note.NoteApp;
import com.treebo.note.R;
import com.treebo.note.databases.TaskDataHandler;
import com.treebo.note.entities.TaskEntity;
import com.treebo.note.events.TaskEvent;
import java.util.ArrayList;

/**
 * Created by sumanta on 21/7/16.
 */
public class MainActivity extends TreeboActivity implements View.OnClickListener {

    private ArrayList<TaskEntity> entityList;
    private TaskRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteApp.getInstance().getEventBus().register(this);
        setContentView(R.layout.activity_main);
        setToolbar("Task List");
        findViewById(R.id.createBtn).setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        entityList = TaskDataHandler.getAllTask(this);
        adapter = new TaskRecyclerAdapter(this, entityList);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void TaskEvent(TaskEvent event) {
        TaskEvent.Action action = event.getAction();
        TaskEntity entity = event.getEntity();
        if(action == TaskEvent.Action.ADD_TASK) {
            entityList.add(entity);
            adapter.notifyDataSetChanged();
        } else if (action == TaskEvent.Action.EDIT_TASK) {
            for (TaskEntity entityInner : entityList) {
                if (entity.getId() == entityInner.getId()) {
                    entityList.add(entityList.indexOf(entityInner), entity);
                    entityList.remove(entityInner);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } else if (action == TaskEvent.Action.DELETE_TASK) {
            for (TaskEntity entityInner : entityList) {
                if (entity.getId() == entityInner.getId()) {
                    entityList.remove(entityInner);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    protected void setToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.createBtn) {
            Intent intent = new Intent(MainActivity.this, FragmentWrapperActivity.class);
            intent.putExtra("className", "com.treebo.note.fragments.AddTaskFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.flipin, R.anim.flipout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoteApp.getInstance().getEventBus().unregister(this);
    }
}
