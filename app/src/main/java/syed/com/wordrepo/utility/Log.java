package syed.com.wordrepo.utility;

/**
 * Created by syed on 1/23/18.
 */

public class Log {
    private static final String TAG = "WordRepoDemo: ";

    //Todo create more log type and save it to a file
    public static void info(Class c, String text){
        android.util.Log.d(TAG+c.getName(), text);
    }
}
