package team.fightcats.englishtrainervector.ui.results

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ResultItemDecoration(
    private val spaceX: Int,
    private val spaceY: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return

        val itemCount = state.itemCount

        if (itemPosition == 0)
            outRect.set(spaceX, spaceY + spaceX, spaceX, spaceY)
        else if (itemCount > 0 && itemPosition == itemCount - 1)
            outRect.set(spaceX, spaceY, spaceX, spaceY + spaceX)
        else
            outRect.set(spaceX, spaceY, spaceX, spaceY)
    }
}