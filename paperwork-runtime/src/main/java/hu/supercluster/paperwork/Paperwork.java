package hu.supercluster.paperwork;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Paperwork {
    public static final String DEFAULT_FILENAME = "paperwork.json";

    private final Context context;
    private final String filename;
    private JSONObject json;

    public Paperwork(Context context) {
        this(context, DEFAULT_FILENAME);
    }

    public Paperwork(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public String get(String key) {
        init();

        return json.optString(key);
    }

    private void init() {
        if (json == null) {
            try {
                json = new JSONObject(getFileContents());

            } catch (JSONException e) {
                throw new PaperworkException("The file '%s' contains invalid JSON data", filename, e);
            }
        }
    }

    @NonNull
    private String getFileContents() {
        StringBuilder builder;

        try {
            InputStream stream = context.getAssets().open(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            builder = new StringBuilder();
            String str;

            while ((str = in.readLine()) != null) {
                builder.append(str);
            }

            in.close();

        } catch (IOException e) {
            throw new PaperworkException("There was an error parsing the file '%s'", filename, e);
        }

        return builder.toString();
    }
}
