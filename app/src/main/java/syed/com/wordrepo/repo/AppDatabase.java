package syed.com.wordrepo.repo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import syed.com.wordrepo.dao.WordDao;
import syed.com.wordrepo.entitiy.Word;

/**
 * Created by syed on 1/11/18.
 */
@Database(entities = {Word.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract WordDao wordDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "word_database").build();
                }
            }
        }
        return INSTANCE;
    }


}
