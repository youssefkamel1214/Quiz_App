package com.example.quizapp.quiz

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.models.Quiz

class QuizFragment : Fragment() {



    private val viewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }
    private lateinit var binding: FragmentQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val quiz=arguments?.get("quiz") as Quiz
        viewModel.quiz=quiz
        binding= FragmentQuizBinding.inflate(inflater,container,false)
        binding.veiwModel=viewModel
        binding.lifecycleOwner=this
        viewModel.index.observe(viewLifecycleOwner){
            if(it<quiz.questions!!.size)
                  binding.Questionnumber.text="Question ${it+1}"
        }
        viewModel.finished.observe(viewLifecycleOwner){
            if(it)
                binding.score.text="Score :${viewModel.answers}/${quiz.questions!!.size}"
        }
        binding.option1.setOnClickListener { viewModel.onOptionClicked(binding.option1) }
        binding.option2.setOnClickListener { viewModel.onOptionClicked(binding.option2) }
        binding.option3.setOnClickListener { viewModel.onOptionClicked(binding.option3) }
        binding.option4.setOnClickListener { viewModel.onOptionClicked(binding.option4) }
        viewModel.answered.observe(viewLifecycleOwner){
            if (!it){
                binding.option1.setBackgroundColor(Color.TRANSPARENT)
                binding.option2.setBackgroundColor(Color.TRANSPARENT)
                binding.option3.setBackgroundColor(Color.TRANSPARENT)
                binding.option4.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        viewModel.index.observe(viewLifecycleOwner){
            if(it==quiz.questions!!.size)
                binding.appCompatButton6.text="Finish"
        }
        viewModel.navigateBack.observe(viewLifecycleOwner){
            if(it)
             findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}