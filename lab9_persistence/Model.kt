package com.example.courses // app package

import android.content.Context // for database context
import androidx.room.* // Room annotations
import androidx.room.Room.databaseBuilder // build database

@Entity(tableName = "courses") // database table
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // unique ID
    val code: String, // course name/code
    val credits: Int // credit value
)

// TODO: Type the CourseDao interface here!!


// this should be *mostly* boilerplate for you to use later
@Database(entities = [Course::class], version = 1, exportSchema = false) // database setup
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao // access DAO

    companion object { // singleton instance

        @Volatile private var INSTANCE: CourseDatabase? = null // shared database

        fun getDatabase(context: Context): CourseDatabase =
            INSTANCE ?: synchronized(this) { // thread-safe init
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, // app context
                    CourseDatabase::class.java, // database class
                    "course_db" // database name
                ).build().also { INSTANCE = it } // build once
            }
    }
}