package team.fightcats.englishtrainervector.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.fightcats.englishtrainervector.model.Result

@Dao
interface ResultDao {
    @Query("SELECT * FROM results ORDER BY id DESC")
    fun getResults(): LiveData<List<Result>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(result: Result)

    @Query("DELETE FROM results")
    suspend fun deleteAll()
}