package com.example.quizapp.teacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentTeacherBinding

class TeacherFragment : Fragment() {


    private val viewModel by lazy {
        ViewModelProvider(this)[TeacherViewModel::class.java]
    }
    private lateinit var binding: FragmentTeacherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTeacherBinding.inflate(layoutInflater,container,false)
        binding.viewmodel=viewModel
        return inflater.inflate(R.layout.fragment_teacher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}