package com.treebo.note.fragments;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.treebo.note.NoteApp;
import com.treebo.note.R;
import com.treebo.note.databases.TaskDataHandler;
import com.treebo.note.entities.TaskEntity;
import com.treebo.note.events.TaskEvent;

/**
 * Created by sumanta on 21/7/16.
 */
@Keep
public class EditTaskFragment extends TreeboFragment implements View.OnClickListener{

    private EditText titleET, contentET;
    private TaskEntity entity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
        entity = getArguments().getParcelable("entity");
        titleET = (EditText) view.findViewById(R.id.titleET);
        contentET = (EditText) view.findViewById(R.id.contentET);
        titleET.setText(entity.getTitle());
        contentET.setText(entity.getContent());
        view.findViewById(R.id.updateBtn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.updateBtn) {
            String title = titleET.getText().toString().trim();
            String content = contentET.getText().toString().trim();
            if(TextUtils.isEmpty(title)) {
                Toast.makeText(getActivity(), "Title can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(content)) {
                Toast.makeText(getActivity(), "Content can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            entity.setTitle(title);
            entity.setContent(content);
            TaskDataHandler.updateTask(getActivity(), entity);
            NoteApp.getInstance().getEventBus().post(new TaskEvent(TaskEvent.Action.EDIT_TASK, entity));
            getActivity().onBackPressed();
        }
    }
}
