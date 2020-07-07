package team.fightcats.englishtrainervector.data

import androidx.lifecycle.LiveData
import team.fightcats.englishtrainervector.db.ResultDao
import team.fightcats.englishtrainervector.model.Result

class ResultRepository(
    private val resultDao: ResultDao
) {
    val allResults: LiveData<List<Result>> = resultDao.getResults()

    suspend fun insert(result: Result) = resultDao.insert(result)

    suspend fun deleteAll() = resultDao.deleteAll()
}