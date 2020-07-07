package team.fightcats.englishtrainervector.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParserException
import team.fightcats.englishtrainervector.model.Question
import java.io.IOException

class QuestionRepository(
    private val context: Context,
    private val scope: CoroutineScope
) {
    val state = MutableLiveData<State>()

    fun getQuestions(callback: OnDataReadyCallback) {
        updateState(State.LOADING)
        val questions = ArrayList<Question>()
        scope.launch(Dispatchers.IO) {
            try {
                questions.clear()
                questions.addAll(QuestionXmlPullParser().parse(context.assets.open("data.xml")))
                callback.onSuccess(questions)
                updateState(State.DONE)
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    interface OnDataReadyCallback {
        fun onSuccess(data: List<Question>)
    }
}
