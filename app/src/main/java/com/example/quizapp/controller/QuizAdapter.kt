package com.example.quizapp.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.FragmentMakeQuizBinding
import com.example.quizapp.databinding.QuizitemBinding
import com.example.quizapp.models.Quiz

class QuizAdapter(val list: List<Quiz>,val clickListener:ClickListener):RecyclerView. Adapter<QuizAdapter.ViewHolder>(){
    class ViewHolder(binding: QuizitemBinding):RecyclerView.ViewHolder(binding.root){
        var binding:QuizitemBinding
        init {
            this.binding=binding
        }
    }
    class ClickListener(val clickListener: (quiz: Quiz) -> Unit) {
        fun onClick(quiz: Quiz) = clickListener(quiz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=QuizitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz=list[position]
        holder.binding.title.text=quiz.name
        holder.binding.questions.text= quiz.questions!!.size.toString()+" questions"
        holder.binding.teacher.text="Teacher : "+quiz.teacher
        holder.binding.constraint.setOnClickListener { clickListener.onClick(quiz) }
    }
}