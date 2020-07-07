package team.fightcats.englishtrainervector.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_start.*
import team.fightcats.englishtrainervector.R

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        startBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_gameFragment)
        }

        resultsBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_resultListFragment)
        }
    }
}