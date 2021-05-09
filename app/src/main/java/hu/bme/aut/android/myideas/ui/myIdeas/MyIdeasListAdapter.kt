package hu.bme.aut.android.myideas.ui.myIdeas

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.ui.myIdeas.MyIdeasListAdapter.IdeaCardViewHolder
import kotlinx.android.synthetic.main.item_idea_card.view.*

class MyIdeasListAdapter : ListAdapter<Idea, IdeaCardViewHolder>(IdeaComparator) {

    var itemClickedListener: ItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IdeaCardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_idea_card, parent, false)
        )

    override fun onBindViewHolder(holder: IdeaCardViewHolder, position: Int) {
        val idea = getItem(position)
        holder.idea = idea
        holder.itemView.ideaTitle.text = idea.title
        holder.itemView.ideaShortDescription.text = idea.shortDescription
    }

    fun getItemAt(position: Int): Idea = getItem(position)

    inner class IdeaCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {

        var idea: Idea? = null

        init {
            itemView.setOnClickListener {
                idea?.let { itemClickedListener?.onListItemClicked(it) }
            }

            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle("Mit szeretnél tenni?");
            menu?.add(adapterPosition, v?.id!!, 0, "Szerkesztés")
            menu?.add(adapterPosition, v?.id!!, 0, "Törlés")
        }
    }

    interface ItemClickedListener {
        fun onListItemClicked(item: Idea)
    }

    companion object {
        object IdeaComparator : DiffUtil.ItemCallback<Idea>() {
            override fun areItemsTheSame(oldItem: Idea, newItem: Idea): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Idea, newItem: Idea): Boolean {
                return oldItem == newItem
            }
        }
    }
}