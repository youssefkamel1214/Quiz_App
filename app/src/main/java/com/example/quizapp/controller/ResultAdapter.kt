package com.example.quizapp.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.QuizitemBinding
import com.example.quizapp.databinding.ResultitemBinding

class ResultAdapter(val teacher:Boolean,val list:List<com.example.quizapp.models.Result>):RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    class ViewHolder(binding: ResultitemBinding):RecyclerView.ViewHolder(binding.root){
        var binding:ResultitemBinding
        init {
            this.binding=binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ResultitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res=list[position]
        holder.binding.score.text="score: ${res.score}/${res.questions}"
        if(teacher){
            holder.binding.user.text=res.teacher
            holder.binding.title.text=res.examName
            holder.binding.title.visibility=View.VISIBLE
        }else{
            holder.binding.user.text=res.examName
            holder.binding.title.visibility=View.GONE
        }
    }
}