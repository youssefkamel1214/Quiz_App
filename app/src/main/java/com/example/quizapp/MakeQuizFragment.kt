package com.example.quizapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentMakeQuizBinding

class MakeQuizFragment : Fragment() {



    private val viewModel: MakeQuizViewModel by lazy {
        ViewModelProvider(this)[MakeQuizViewModel::class.java]
    }
    private lateinit var binding: FragmentMakeQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMakeQuizBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        viewModel.navigateToTeacherFragment.observe(viewLifecycleOwner){
            if(it)
                findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}