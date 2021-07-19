package com.blokkok.app.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blokkok.app.CompileActivity
import com.blokkok.app.R

class LibrariesRecyclerView(
    private var libraries: Array<LibraryItem>
) : RecyclerView.Adapter<LibrariesRecyclerView.ViewHolder>() {

    fun updateView(libraries: Array<LibraryItem>) {
        this.libraries = libraries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.library_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val library = libraries[position]
        holder.name.text = library.name
        holder.cacheStatus.text = if (library.isCached) "CACHED" else "NOT CACHED"

        holder.root.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Confirmation")
                .setMessage("Do you want to compile (or re-compile if you've already compiled it) the cache for this library?\n\nNote: Yes, this is just temporary. Later on, when clicking the library item, you would be directed into a library manager activity.")
                .setPositiveButton("Compile") { _, _ ->
                    it.context.startActivity(
                        Intent(it.context, CompileActivity::class.java).apply {
                            putExtra("library_name", library.name)
                        }
                    )
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                .create()
                .show()
        }
    }

    override fun getItemCount(): Int = libraries.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.findViewById(R.id.library_root_item)
        val name: TextView = itemView.findViewById(R.id.library_name)
        val cacheStatus: TextView = itemView.findViewById(R.id.cache_status)
    }
}

data class LibraryItem(
    val name: String,
    val isCached: Boolean,
)