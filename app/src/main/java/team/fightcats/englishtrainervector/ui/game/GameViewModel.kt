package team.fightcats.englishtrainervector.ui.game

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.fightcats.englishtrainervector.data.QuestionRepository
import team.fightcats.englishtrainervector.data.ResultRepository
import team.fightcats.englishtrainervector.data.State
import team.fightcats.englishtrainervector.db.ResultDatabase
import team.fightcats.englishtrainervector.model.Answer
import team.fightcats.englishtrainervector.model.Question
import team.fightcats.englishtrainervector.model.Result
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val resultRepository: ResultRepository
    private val questionRepository: QuestionRepository
    private val questionLiveData = MutableLiveData<Question>()
    private val gameScoreLiveData = MutableLiveData<Int>()
    private val currentQuestionLiveData = MutableLiveData<Int>()

    private val questions = ArrayList<Question>()
    private var index = 0
    private var score = 0
    private var correct = 0

    val question: LiveData<Question>
        get() = questionLiveData

    val gameScore: LiveData<Int>
        get() = gameScoreLiveData

    val currentQuestion: LiveData<Int>
        get() = currentQuestionLiveData

    fun getQuestion() {
        questionLiveData.postValue(questions[index])
        currentQuestionLiveData.postValue(index + 1)

        if (index < questions.lastIndex) index++
        else index = 0
    }

    fun getScore() = gameScoreLiveData.postValue(score)

    fun getTotalQuestions() = questions.size

    fun isNoMoreQuestions(): Boolean {
        return index == 0
    }

    fun updateScore(state: Answer) {
        when (state) {
            Answer.CORRECT -> {
                score += 2
                correct += 1
            }
            Answer.INCORRECT -> if (score > 0) score -= 1
        }
    }

    init {
        val resultDao = ResultDatabase.getInstance(application).resultDao()
        resultRepository = ResultRepository(resultDao)
        questionRepository = QuestionRepository(application, viewModelScope)
        questionRepository.getQuestions(object : QuestionRepository.OnDataReadyCallback {
            override fun onSuccess(data: List<Question>) {
                questions.clear()
                questions.addAll(data)
            }
        })
    }

    fun getDataState(): LiveData<State> {
        return questionRepository.state
    }

    fun saveResult() {
        val date = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
        val item = Result(0, date, score, correct, getTotalQuestions())
        viewModelScope.launch(Dispatchers.IO) { resultRepository.insert(item) }

        score = 0
        correct = 0
    }
}