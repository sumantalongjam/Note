package com.treebo.note;

import android.app.Application;
import android.content.Context;
import com.squareup.otto.ThreadEnforcer;
import com.treebo.note.events.TreeboEventBus;

/**
 * Created by sumanta on 21/7/16.
 */
public class NoteApp extends Application {

    private static Context context;
    private static TreeboEventBus eventBus;
    private static final NoteApp noteApp = new NoteApp();

    public NoteApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        eventBus = new TreeboEventBus(ThreadEnforcer.MAIN);
    }
    public static NoteApp getInstance() {
        return noteApp;
    }
    public TreeboEventBus getEventBus() { return eventBus; }
    public Context getContext() {
        return context;
    }

}