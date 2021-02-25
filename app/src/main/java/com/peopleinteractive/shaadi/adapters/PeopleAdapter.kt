package com.peopleinteractive.shaadi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.databinding.LayoutPeopleItemBinding
import com.peopleinteractive.shaadi.ui.people.PeopleViewModel

/**
 * PeopleAdapter is responsible to covert people data into view by binding people model with the view.
 */
class PeopleAdapter(private val viewModel: PeopleViewModel) :
    ListAdapter<People, PeopleAdapter.ViewHolder>(PeopleDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    class ViewHolder(private val binding: LayoutPeopleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the people model with the view.
         */
        fun bind(item: People?, viewModel: PeopleViewModel) {
            item?.let {
                binding.people = item
                binding.viewmodel = viewModel
            }
        }

        /**
         * Methods and variables in companion object are static.
         */
        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): ViewHolder {
                val binding = LayoutPeopleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * PeopleDiffCallBack replace only those items in the list which is updated.
 */
class PeopleDiffCallBack : DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People) = oldItem.email == newItem.email
    override fun areContentsTheSame(oldItem: People, newItem: People) = oldItem == newItem
}

