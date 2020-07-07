package team.fightcats.englishtrainervector.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import team.fightcats.englishtrainervector.model.Result

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
abstract class ResultDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        fun getInstance(context: Context): ResultDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "game_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}