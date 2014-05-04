package com.example.sofarsounds;

import android.content.Context;
import android.content.SharedPreferences;
import com.parse.ParseException;
import com.parse.ParseUser;

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

    public static SofarSession getCurrentSession(Context ctx) {
        ParseUser current = ParseUser.getCurrentUser();
        if (current != null)
            return new SofarSession(ctx, current.getSessionToken());
        else
            return null;
    }

    public static boolean hasValidSession(Context ctx) {
        // TODO: Issue a server call to ensure current session is valid.
        return getCurrentSession(ctx) != null;
    }

    public static SofarSession openNewSession(Context ctx, String username, String password) throws ParseException {
        if (getCurrentSession(ctx) != null)
            getCurrentSession(ctx).close();
        ParseUser pu = ParseUser.logIn(username, password);
        return new SofarSession(ctx, pu.getSessionToken());
    }

    public void close() {
        ParseUser.logOut();
    }

    public boolean equals(Object other) {
        if (!(other instanceof SofarSession))
            return false;
        return ((SofarSession)other).sessionID.equals(sessionID);
    }
}
