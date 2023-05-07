package com.example.quizapp.makequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
    val questiontitlename=Transformations.map(index){
        if(it==-1)
            return@map "quiz Name"
        else
            return@map "Question"
    }
    private val _navigateToTeacherFragment=MutableLiveData(false)
    val navigateToTeacherFragment: LiveData<Boolean>
        get() = _navigateToTeacherFragment
    private fun validate(): Boolean {
        var hasError=true
        if(questionName.value.isNullOrEmpty()||option1.value.isNullOrEmpty()||
            option2.value.isNullOrEmpty()||option3.value.isNullOrEmpty()||option4.value.isNullOrEmpty()||answer.value.isNullOrEmpty())
            hasError=false
        return hasError
    }

    fun nextButton(){
        if(index.value==-1&&!questionName.value.isNullOrEmpty()){
            quizname=questionName.value!!
            index.postValue(index.value!!+1)
            clearEveryThing()
        }
        if(index.value!!>=0&&validate()){
            val answerString=when(answer.value!!.toInt()-1){
                0->option1.value
                1->option2.value
                2->option3.value
                else->option4.value
            }
            val q=Question(questionName.value,answerString, listOf(option1.value!!,option2.value!!,option3.value!!,option4.value!!))
            questions.add(q)
            index.postValue(index.value!!+1)
            clearEveryThing()
        }
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
        val answerString=when(answer.value!!.toInt()-1){
            0->option1.value
            1->option2.value
            2->option3.value
            else->option4.value
        }
        val q=Question(questionName.value,answerString, listOf(option1.value!!,option2.value!!,option3.value!!,option4.value!!))
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