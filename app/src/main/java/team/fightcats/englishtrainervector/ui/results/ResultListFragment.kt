package team.fightcats.englishtrainervector.ui.results

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import team.fightcats.englishtrainervector.R

class ResultListFragment : Fragment() {
    private lateinit var resultListViewModel: ResultListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.result_list)
        val layoutManager = LinearLayoutManager(context)
        val adapter = ResultListAdapter(LayoutInflater.from(context))

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        recycler.addItemDecoration(
            ResultItemDecoration(
                requireContext().resources.getDimension(R.dimen.space_8).toInt(),
                requireContext().resources.getDimension(R.dimen.space_4).toInt()
            )
        )

        resultListViewModel = ViewModelProvider(this).get(ResultListViewModel::class.java)
        resultListViewModel.allResults.observe(viewLifecycleOwner, Observer { results ->
            results?.let { adapter.submitList(it) }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.results_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_results -> {
                resultListViewModel.clearResults()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}