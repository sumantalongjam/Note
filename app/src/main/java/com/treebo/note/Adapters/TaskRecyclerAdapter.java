package com.treebo.note.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.treebo.note.R;
import com.treebo.note.activities.TaskDetailActivity;
import com.treebo.note.entities.TaskEntity;
import java.util.ArrayList;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private ArrayList<TaskEntity> entityList;

    public TaskRecyclerAdapter(Context context, ArrayList<TaskEntity> entityList) {
        this.context = context;
        this.entityList = entityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_task, parent, false);
        RecyclerView.ViewHolder viewHolder = new TaskHolder(view);
        ((TaskHolder)viewHolder).container.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskEntity entity = entityList.get(position);
        if (entity != null) {
            ((TaskHolder) holder).titleTV.setText(entity.getTitle());
            ((TaskHolder) holder).contentTV.setText(entity.getContent());
            ((TaskHolder) holder).container.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return entityList == null ? 0 : entityList.size();
    }

    @Override
    public void onClick(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        TaskEntity entity = entityList.get(position);
        if(entity!=null) {
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("entity", entity);
            context.startActivity(intent);
            if(context instanceof Activity)
                ((Activity)context).overridePendingTransition(R.anim.flipin, R.anim.flipout);
        }
    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        TextView titleTV, contentTV;
        LinearLayout container;
        public TaskHolder(View v) {
            super(v);
            titleTV = (TextView) v.findViewById(R.id.titleTV);
            contentTV = (TextView) v.findViewById(R.id.contentTV);
            container = (LinearLayout) v.findViewById(R.id.container);
        }
    }
}
