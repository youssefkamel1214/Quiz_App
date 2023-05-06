package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.Question
import com.example.quizapp.models.Quiz
import com.example.quizapp.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MakeQuizViewModel : ViewModel() {
    val questions=ArrayList<Question>()
    private var quizname=""
    private  var user:User?=null
    private val db=Firebase.firestore
    private val auth=Firebase.auth
    val questionName=MutableLiveData("")
    val option1=MutableLiveData("")
    val option2=MutableLiveData("")
    val option3=MutableLiveData("")
    val option4=MutableLiveData("")
    val answer=MutableLiveData("")
    val index=MutableLiveData(-1)
    private val _navigateToTeacherFragment=MutableLiveData(false)
    val navigateToTeacherFragment: LiveData<Boolean>
        get() = _navigateToTeacherFragment
    private fun validate(): Boolean {
        var hasError=false
        if(questionName.value.isNullOrEmpty()||option1.value.isNullOrEmpty()||
            option2.value.isNullOrEmpty()||option3.value.isNullOrEmpty()||option4.value.isNullOrEmpty()||answer.value.isNullOrEmpty())
            hasError=true
        return hasError
    }

    fun nextButton(){
        if(index.value==-1){
            quizname=questionName.value!!
        }
        if(index.value!!>=0&&validate()){
            val q=Question(questionName.value,answer.value, listOf(option1.value!!,option2.value!!,option3.value!!,option4.value!!))
            questions.add(q)
        }
        index.postValue(index.value!!+1)
        clearEveryThing()
    }

    private fun clearEveryThing() {
        questionName.postValue("")
        option1.postValue("")
        option2.postValue("")
        option3.postValue("")
        option4.postValue("")
        answer.postValue("")
    }
    fun submit(){
        if(index.value==-1)
            return
        if(!validate()){
           return
        }
        val q=Question(questionName.value,answer.value, listOf(option1.value!!,option2.value!!,option3.value!!,option4.value!!))
        questions.add(q)
        viewModelScope.launch {
            val docRef = db.collection("quizzes").document()
            val id = docRef.id
            val quiz=Quiz(quizname,id,auth.currentUser!!.uid,user!!.name,questions)
            docRef.set(quiz).await()
            _navigateToTeacherFragment.postValue(true)
        }
    }
    init {
        viewModelScope.launch {
            val tmp=db.collection("users").document(auth.currentUser!!.uid).get().await()
            user=tmp.toObject<User>()
        }
    }
}