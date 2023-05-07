package com.example.quizapp.quiz

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.*
import com.example.quizapp.models.Quiz
import com.example.quizapp.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class QuizViewModel : ViewModel() {
    private val _index=MutableLiveData(0)
    val index:LiveData<Int>get() = _index
    private val auth=Firebase.auth
    private val db=Firebase.firestore
    lateinit var  quiz:Quiz
    var  answers:Int=0
    private val _answered=MutableLiveData(false)
    val answered:LiveData<Boolean>get() = _answered
    val question = Transformations.map(_index){
        if(it==quiz.questions!!.size)
            return@map null
        quiz.questions!![it]
    }
    val answer=MutableLiveData("")
    val finished=Transformations.map(_index){
        return@map it==quiz.questions!!.size
    }
    private val _navigateBack=MutableLiveData(false)
    val navigateBack:LiveData<Boolean>get() = _navigateBack
    private val _loadingState=MutableLiveData(false)
    val loadingState:LiveData<Boolean>get() = _loadingState
    fun onOptionClicked(textView: TextView){
        answer.value=question.value!!.answer
        _answered.postValue(true)

        if (textView.text.toString()==answer.value!!) {
            answers++
            textView.setBackgroundColor(Color.GREEN)
        }
        else
            textView.setBackgroundColor(Color.RED)

    }
    fun submit(){
        if(!finished.value!!) {
            _index.value=_index.value!!+1
            answer.postValue("")
            if(_index.value!!!=quiz.questions!!.size)
            _answered.postValue(false)
        }else{
            sumbitResult()

        }

    }

    private fun sumbitResult() {
        viewModelScope.launch {
        try {
            _loadingState.postValue(true)
            val user=db.collection("users").document(auth.currentUser!!.uid).get().await().toObject<User>()
            val result=com.example.quizapp.models.Result(user!!.name,auth.currentUser!!.uid,quiz.name,answers,quiz.id,quiz.teacher,quiz.teacherId,quiz.questions!!.size)
            db.collection("result").add(result).await()
            _loadingState.postValue(false)
            _navigateBack.postValue(true)

        }   catch (e:java.lang.Exception){
            Log.e("QuizViewModel",e.message.toString())
        }
        }
    }


}