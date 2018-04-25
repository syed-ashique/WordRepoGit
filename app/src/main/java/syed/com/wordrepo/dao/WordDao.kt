package syed.com.wordrepo.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import syed.com.wordrepo.entitiy.Word

/**
 * Created by syed on 2/13/18.
 */

@Dao
interface WordDao {
    @Insert
    fun insertWord(word: Word)

    @Update
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT * FROM word_table WHERE id = :arg0")
    fun getWord(id: Int): Word

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    //Todo make it more generic by passing the table name to the method
    @Query("SELECT COUNT(*) FROM word_table")
    fun getWordTableSize(): Int
}
