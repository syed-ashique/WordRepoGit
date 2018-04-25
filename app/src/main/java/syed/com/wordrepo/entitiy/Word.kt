package syed.com.wordrepo.entitiy

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by syed on 2/12/18.
 */
@Entity(tableName = "word_table")
data class Word(@PrimaryKey(autoGenerate = true) var id:Int?, var word: String, var meaning:String, var description:String, var note:String) {
    constructor():this(null, "", "", "", "")
}

