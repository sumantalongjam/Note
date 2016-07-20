package com.treebo.note.events;


import com.treebo.note.entities.TaskEntity;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskEvent {
    private Action action;
    private TaskEntity entity;
    public TaskEvent (Action action, TaskEntity entity) {
        this.action = action;
        this.entity = entity;
    }

    public enum Action {
        ADD_TASK, EDIT_TASK, DELETE_TASK;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public TaskEntity getEntity() {
        return entity;
    }

    public void setEntity(TaskEntity entity) {
        this.entity = entity;
    }
}
