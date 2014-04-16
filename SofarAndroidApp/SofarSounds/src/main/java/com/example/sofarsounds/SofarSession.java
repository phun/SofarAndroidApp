package com.example.sofarsounds;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lucid on 4/7/14.
 */
public class SofarSession {
    private final String sessionID;
    private final Context context;
    private SofarSession(Context context, String sessionID) {
        this.context = context;
        this.sessionID = sessionID;
    }

    private static SharedPreferences getStorage(Context ctx) {
        return ctx.getSharedPreferences(ctx.getString(R.string.session_preference_id), Context.MODE_PRIVATE);
    }

    public static SofarSession getCurrentSession(Context ctx) {
        SharedPreferences storage = getStorage(ctx);
        if (storage.contains("session_id"))
            return new SofarSession(ctx, storage.getString("session_id", null));
        else
            return null;
    }

    public static boolean hasValidSession(Context ctx) {
        // TODO: Issue a server call to ensure current session is valid.
        return getCurrentSession(ctx) != null;
    }

    public static SofarSession openNewSession(Context ctx, String username, String password) {
        if (getCurrentSession(ctx) != null)
            getCurrentSession(ctx).close();
        SofarSession sess = new SofarSession(ctx, "UPDATE WITH SERVER RANDOM STRING HERE");
        SharedPreferences.Editor e = getStorage(ctx).edit();
        e.putString("session_id", sess.sessionID);
        e.commit();
        return sess;
    }

    public void close() {
        // TODO: Issue a logout / invalidate session on server.
        // if (this.equals(getCurrentSession())) { OOH WEIRD RACE CONDITION
        SharedPreferences.Editor e = getStorage(context).edit();
        e.remove("session_id");
        e.commit();
    }

    public boolean equals(Object other) {
        if (!(other instanceof SofarSession))
            return false;
        return ((SofarSession)other).sessionID.equals(sessionID);
    }
}
