package com.fiqri.githubusersinfo.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fiqri.githubusersinfo.database.Fav
import com.fiqri.githubusersinfo.databinding.ItemUserBinding
import com.fiqri.githubusersinfo.helper.FavDiffCallback

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    private val listFav = ArrayList<Fav>()
    fun setListFav(listFav: List<Fav>) {
        val diffCallback = FavDiffCallback(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFav.clear()
        this.listFav.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFav[position])
    }
    override fun getItemCount(): Int {
        return listFav.size
    }
    inner class FavViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: Fav) {
            with(binding) {
                tvItem.text = fav.username
                Glide.with(binding.profPic.context)
                    .load(fav.avatar_url)
                    .into(profPic)
                oneUser.setOnClickListener {
                    val moveToDetail = Intent(it.context, UserDetailActivity::class.java)
                    moveToDetail.putExtra("username", fav.username)
                    it.context.startActivity(moveToDetail)
                }
            }
        }
    }

}