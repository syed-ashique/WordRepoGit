package syed.com.wordrepo.repo

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import syed.com.wordrepo.dao.WordDao
import syed.com.wordrepo.entitiy.Word

/**
 * Created by syed on 2/13/18.
 */
@Database(entities = arrayOf(Word::class), version = 1)
abstract class AppDatabaseKotlin : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "word_database").build()
                    }
                }
            }
            return INSTANCE
        }
    }

}
