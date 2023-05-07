package com.example.quizapp.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.controller.QuizAdapter
import com.example.quizapp.databinding.FragmentStudentBinding
import com.example.quizapp.models.Quiz
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [StudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        quizzes.clear()
        results.clear()
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }
    }

    private lateinit var binding: FragmentStudentBinding
    private val db= Firebase.firestore
    private val quizzes=ArrayList<Quiz>()
    private val results=ArrayList<com.example.quizapp.models.Result>()
    private val auth= Firebase.auth
    suspend fun getData(){
      val documents=  db.collection("quizzes").get().await()
      val resdoc=db.collection("result").whereEqualTo("studentId",auth.currentUser!!.uid).get().await()
      val tmplist=ArrayList<String>()
        for (doc in resdoc){
            val result=doc.toObject<com.example.quizapp.models.Result>()
            results.add(result)

            if(result.studentId==auth.currentUser!!.uid)
                tmplist.add(result.examId!!)
        }

      for (doc in documents){
          val tmp=doc.toObject<Quiz>()
          if(!tmplist.contains(tmp.id))
                quizzes.add(tmp)
      }
        withContext(Dispatchers.Main) {
            binding.progressbar.visibility = View.GONE
            if (!quizzes.isEmpty()) {
                binding.textView2.visibility = View.VISIBLE
                binding.reclerview.visibility = View.VISIBLE
                binding.reclerview.adapter = QuizAdapter(quizzes, QuizAdapter.ClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable("quiz", it)
                    findNavController().navigate(R.id.quizFragment, bundle)
                })
            } else {
                binding.textView3.visibility = View.VISIBLE
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       CoroutineScope(Dispatchers.IO).launch {
           getData()
       }
        binding= FragmentStudentBinding.inflate(layoutInflater, container, false)
        binding.reclerview.layoutManager= LinearLayoutManager(requireContext())
        binding.appCompatButton2.setOnClickListener { navigateTOResult() }
        return binding.root
    }

    private fun navigateTOResult() {
        val bundle = Bundle()
        bundle.putParcelableArrayList("results",results)
        findNavController().navigate(R.id.resultFragment,bundle)
    }


}