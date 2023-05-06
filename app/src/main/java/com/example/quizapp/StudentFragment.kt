package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.controller.QuizAdapter
import com.example.quizapp.databinding.FragmentStudentBinding
import com.example.quizapp.models.Quiz
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentFragment : Fragment() {

    private lateinit var binding: FragmentStudentBinding
    private val db=Firebase.firestore
    private val quizzes=ArrayList<Quiz>()

    suspend fun getData(){
      val documents=  db.collection("quizzes").get().await()
      for (doc in documents){
          val tmp=doc.toObject<Quiz>()
          quizzes.add(tmp)
      }
        activity?.runOnUiThread {
           binding.reclerview.adapter=QuizAdapter(quizzes, QuizAdapter.ClickListener {

           })
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
        binding=FragmentStudentBinding.inflate(layoutInflater,container,false)
        binding.reclerview.layoutManager=LinearLayoutManager(requireContext())
        return binding.root
    }


}