package com.example.quizapp.teacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.controller.ResultAdapter
import com.example.quizapp.databinding.FragmentTeacherBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TeacherFragment : Fragment() {
    private val db= Firebase.firestore
    private val results=ArrayList<com.example.quizapp.models.Result>()
    private val auth=Firebase.auth

    suspend fun getData(){
        val resdoc=db.collection("result").whereEqualTo("teacherId",auth.currentUser!!.uid).get().await()
        val tmplist=ArrayList<String>()
        for (doc in resdoc){
            val result=doc.toObject<com.example.quizapp.models.Result>()
            results.add(result)

            if(result.studentId==auth.currentUser!!.uid)
                tmplist.add(result.examId!!)
        }
        withContext(Dispatchers.Main){
            binding.progressBar4.visibility=View.GONE
            if(results.isEmpty()){
                binding.textView6.visibility=View.VISIBLE
            }else{
                binding.textView4.visibility=View.VISIBLE
                binding.reclerview.visibility=View.VISIBLE
                binding.reclerview.layoutManager=LinearLayoutManager(requireContext())
                binding.reclerview.adapter=ResultAdapter(true,results)
            }
        }
    }


    private lateinit var binding: FragmentTeacherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTeacherBinding.inflate(layoutInflater,container,false)
        binding.appCompatButton3.setOnClickListener {
            findNavController().navigate(TeacherFragmentDirections.actionTeacherFragmentToMakeQuizFragment())
        }
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}