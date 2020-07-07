package team.fightcats.englishtrainervector.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_answer.view.*
import team.fightcats.englishtrainervector.R

class AnswerListAdapter(
    private val inflater: LayoutInflater,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AnswerListViewHolder>() {
    private val answerList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerListViewHolder {
        return AnswerListViewHolder(
            inflater.inflate(R.layout.item_answer, parent, false)
        )
    }

    override fun getItemCount() = answerList.size

    override fun onBindViewHolder(holder: AnswerListViewHolder, position: Int) {
        val word = answerList[position]
        holder.bind(word)
        holder.itemView.answerLl.setOnClickListener { listener.onClick(word) }
    }

    fun submitList(answers: List<String>) {
        answerList.clear()
        answerList.addAll(answers)
        notifyDataSetChanged()
    }
}

class AnswerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(word: String) {
        itemView.answerTv.text = word
    }
}

interface OnItemClickListener {
    fun onClick(word: String)
}