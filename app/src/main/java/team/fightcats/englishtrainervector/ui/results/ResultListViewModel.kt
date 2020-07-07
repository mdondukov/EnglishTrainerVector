package team.fightcats.englishtrainervector.ui.results

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.fightcats.englishtrainervector.data.ResultRepository
import team.fightcats.englishtrainervector.db.ResultDatabase
import team.fightcats.englishtrainervector.model.Result

class ResultListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ResultRepository
    val allResults: LiveData<List<Result>>

    init {
        val resultDao = ResultDatabase.getInstance(application).resultDao()
        repository = ResultRepository(resultDao)
        allResults = repository.allResults
    }

    fun clearResults() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}