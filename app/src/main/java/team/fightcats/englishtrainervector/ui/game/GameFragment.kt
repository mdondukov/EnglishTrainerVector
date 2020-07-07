package team.fightcats.englishtrainervector.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.fragment_game.*
import team.fightcats.englishtrainervector.R
import team.fightcats.englishtrainervector.data.State
import team.fightcats.englishtrainervector.model.Answer
import team.fightcats.englishtrainervector.model.Question

class GameFragment : Fragment(),
    OnItemClickListener {
    private lateinit var navController: NavController
    private lateinit var gameViewModel: GameViewModel
    private lateinit var adapter: AnswerListAdapter
    private lateinit var question: Question

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        if (savedInstanceState == null) {
            gameViewModel.getScore()
            gameViewModel.getDataState().observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    State.LOADING -> loader.visibility = View.VISIBLE
                    State.DONE -> {
                        gameViewModel.getQuestion()
                        loader.visibility = View.GONE
                    }
                    else -> loader.visibility = View.VISIBLE
                }
            })
        }
        gameViewModel.question.observe(viewLifecycleOwner, Observer { showQuestion(it) })
        gameViewModel.gameScore.observe(viewLifecycleOwner, Observer { showScore(it) })
        gameViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { showProgress(it) })

        val recycler = view.findViewById<RecyclerView>(R.id.answer_list)
        initRecycler(recycler)
    }

    private fun showQuestion(item: Question) {
        question = item
        questionTv.text = question.word
        question.variants?.let { adapter.submitList(it) }
    }

    private fun showScore(gameScore: Int) {
        score.text = requireActivity().resources.getString(R.string.current_score, gameScore)
    }

    private fun showProgress(current: Int) {
        val total = gameViewModel.getTotalQuestions()
        progress.progress = current
        progress.max = total
        level.text = requireActivity().resources.getString(R.string.current_level, current, total)
    }

    private fun initRecycler(recycler: RecyclerView) {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        recycler.layoutManager = layoutManager

        adapter = AnswerListAdapter(
            LayoutInflater.from(context),
            this
        )
        recycler.adapter = adapter
    }

    private fun showSnackBar(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(word: String) {
        if (word.equals(question.translation, true)) {
            showSnackBar(requireActivity().resources.getString(R.string.correct))
            gameViewModel.updateScore(Answer.CORRECT)
            if (gameViewModel.isNoMoreQuestions()) {
                gameViewModel.saveResult()
                navController.navigate(R.id.action_gameFragment_to_resultListFragment)
            } else gameViewModel.getQuestion()
        } else {
            showSnackBar(requireActivity().resources.getString(R.string.incorrect))
            gameViewModel.updateScore(Answer.INCORRECT)
            if (gameViewModel.isNoMoreQuestions()) {
                gameViewModel.saveResult()
                navController.navigate(R.id.action_gameFragment_to_resultListFragment)
            } else gameViewModel.getQuestion()
        }
        gameViewModel.getScore()
    }
}