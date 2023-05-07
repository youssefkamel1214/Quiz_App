package com.example.quizapp.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.controller.ResultAdapter
import com.example.quizapp.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private  var results:ArrayList< com.example.quizapp.models.Result>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        results=arguments?.getParcelableArrayList("results")

        binding= FragmentResultBinding.inflate(layoutInflater,container,false)
        binding.reclerview.layoutManager=LinearLayoutManager(requireContext())
        results?.apply {
          binding.reclerview.adapter=ResultAdapter(false,this)
        }
        return binding.root
    }


}