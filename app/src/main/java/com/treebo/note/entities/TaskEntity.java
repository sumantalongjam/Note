package com.treebo.note.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskEntity implements Parcelable{
    private long id;
    private String title;
    private String content;

    public TaskEntity() {}
    public TaskEntity(long id, String title) {
        this.id = id;
        this.title = title;
    }
    public TaskEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public TaskEntity(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    protected TaskEntity(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(content);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
