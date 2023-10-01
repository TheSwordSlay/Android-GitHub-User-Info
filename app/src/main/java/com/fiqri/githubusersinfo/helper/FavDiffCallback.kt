package com.fiqri.githubusersinfo.helper

import androidx.recyclerview.widget.DiffUtil
import com.fiqri.githubusersinfo.database.Fav

class FavDiffCallback(private val oldNoteList: List<Fav>, private val newNoteList: List<Fav>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatar_url == newNote.avatar_url
    }
}