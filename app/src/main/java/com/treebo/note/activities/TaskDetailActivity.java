package com.treebo.note.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.treebo.note.NoteApp;
import com.treebo.note.R;
import com.treebo.note.databases.TaskDataHandler;
import com.treebo.note.entities.TaskEntity;
import com.treebo.note.events.TaskEvent;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskDetailActivity extends TreeboActivity implements View.OnClickListener{

    private TaskEntity entity;
    private TextView titleTV, contentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteApp.getInstance().getEventBus().register(this);
        setContentView(R.layout.activity_task_detail);
        setToolbar("Task Detail");
        entity = getIntent().getParcelableExtra("entity");
        findViewById(R.id.editBtn).setOnClickListener(this);
        titleTV = (TextView) findViewById(R.id.titleTV);
        contentTV = (TextView) findViewById(R.id.contentTV);
        if(entity!=null) {
            titleTV.setText(TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
            contentTV.setText(TextUtils.isEmpty(entity.getContent()) ? "" : entity.getContent());
        }
    }

    @Subscribe
    public void TaskEvent(TaskEvent event) {
        TaskEvent.Action action = event.getAction();
        TaskEntity entity = event.getEntity();
        if(action == TaskEvent.Action.EDIT_TASK) {
            this.entity = entity;
            titleTV.setText(TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
            contentTV.setText(TextUtils.isEmpty(entity.getContent()) ? "" : entity.getContent());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }  else if (id == R.id.deleteMenu) {
            TaskDataHandler.deleteTask(TaskDetailActivity.this, entity.getId());
            NoteApp.getInstance().getEventBus().post(new TaskEvent(TaskEvent.Action.DELETE_TASK, entity));
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.editBtn) {
            Intent intent = new Intent(TaskDetailActivity.this, FragmentWrapperActivity.class);
            intent.putExtra("className", "com.treebo.note.fragments.EditTaskFragment");
            intent.putExtra("entity", entity);
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
