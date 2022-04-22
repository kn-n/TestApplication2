package ru.kn_n.testapplication2.presentation.view.main.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kn_n.testapplication2.domain.containers.Repository
import ru.kn_n.testapplication2.R

class RepositoriesAdapter(private val repositories: ArrayList<Repository>,
                          private val onItemClick: (repo: Repository) -> Unit,
                          private val checkRepo: (repo: Repository) -> Boolean,
                          private val deleteFromDB: (repo: Repository) -> Unit) :
    RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        val savedRepo = checkRepo(repository)
        if (savedRepo) {
            holder.inSaved.visibility = View.VISIBLE
            holder.delete.visibility = View.VISIBLE
            holder.delete.setOnClickListener {
                deleteFromDB(repository)
            }
        } else {
            holder.inSaved.visibility = View.GONE
            holder.delete.visibility = View.GONE
        }

        holder.nameOfRepository.text = repository.full_name
        holder.descriptionOfRepository.text = repository.description

        holder.repository.setOnClickListener {
            onItemClick(repository)
        }
    }

    override fun getItemCount() = repositories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameOfRepository: TextView = itemView.findViewById(R.id.name)
        val descriptionOfRepository: TextView = itemView.findViewById(R.id.description)
        val inSaved: ImageView = itemView.findViewById(R.id.in_saved)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val repository: LinearLayout = itemView.findViewById(R.id.item)
    }
}