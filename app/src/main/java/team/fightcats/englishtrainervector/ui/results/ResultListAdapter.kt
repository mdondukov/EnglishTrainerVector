package team.fightcats.englishtrainervector.ui.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_result.view.*
import team.fightcats.englishtrainervector.R
import team.fightcats.englishtrainervector.model.Result

class ResultListAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ResultListViewHolder>() {
    private val resultList = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListViewHolder {
        return ResultListViewHolder(
            inflater.inflate(R.layout.item_result, parent, false)
        )
    }

    override fun getItemCount() = resultList.size

    override fun onBindViewHolder(holder: ResultListViewHolder, position: Int) {
        val result = resultList[position]
        holder.bind(result)
    }

    fun submitList(items: List<Result>) {
        resultList.clear()
        resultList.addAll(items)
        notifyDataSetChanged()
    }
}

class ResultListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Result) {
        itemView.dateTv.text = item.startDate
        itemView.scoreTv.text = item.gameScore.toString()
        itemView.correctTv.text =
            itemView.resources.getString(R.string.game_result, item.correctAnswers, item.totalQuestions)
    }
}