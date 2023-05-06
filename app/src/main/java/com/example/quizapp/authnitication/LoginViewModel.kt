package com.example.quizapp.authnitication

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {
    private val tag="LoginViewModel"

    // TODO: Implement the ViewModel
    private val _modeSignIn= MutableLiveData(true)
    private val auth = Firebase.auth
    private val db=Firebase.firestore

    private var selectedItem=-1
    val modeSignIn: LiveData<Boolean>
        get() = _modeSignIn
    val nameError= MutableLiveData(0)
    val emailError= MutableLiveData(0)
    val passwordError= MutableLiveData(0)
    val radioError=MutableLiveData(0)
    val name= MutableLiveData("")
    val email= MutableLiveData("")
    val password= MutableLiveData("")
    private val _navigateToHomeFragment=MutableLiveData(false)
    val navigateToHomeFragment:LiveData<Boolean>
        get() = _navigateToHomeFragment
    var user:User?=null
    fun clearPasswordError(){
        passwordError.value=0
    }
    fun clearNameError(){
        nameError.value=0
    }
    fun clearEmailError(){
        emailError.value=0
    }
    fun clearEverything() {
        name.value=""
        password.value=""
        email.value=""
        _navigateToHomeFragment.value=false
        _modeSignIn.value=true
        nameError.value=0
        passwordError.value=0
        emailError.value=0
    }
    fun stateChange(){
        _modeSignIn.value= _modeSignIn.value!!.not()
    }
    fun validateAndSumbit(){
           if(valdiate()){
               sumbit()
           }
    }

    private fun sumbit() {
        if(_modeSignIn.value!!)
            signIn()
        else
            signup()
    }

    private fun signIn() {
        viewModelScope.launch {
            try {
                val res=auth.signInWithEmailAndPassword(email.value!!,password.value!!).await()
                val docref=db.collection("users").document(res.user!!.uid).get().await()
                user= docref.toObject<User>()
                _navigateToHomeFragment.postValue(true)

            }catch (e:Exception){
                e.message?.apply {
                    if(this.contains("no user record corresponding")){
                        emailError.value=2
                    }
                    else if(this.contains("The password is invalid")){
                        passwordError.value=2
                    }
                    Log.e(tag,e.message.toString())
                }
            }
        }
    }

    private fun signup() {
        viewModelScope.launch {
            try {
                val res =
                    auth.createUserWithEmailAndPassword(email.value!!, password.value!!).await()
            user = when(selectedItem){
                    0->User(email = email.value!!, type = "teacher", name = name.value!!)
                    else->User(email = email.value!!, type = "student", name = name.value!!)
                }
                db.collection("users").document(res.user!!.uid).set(user!!).await()
                _navigateToHomeFragment.postValue(true)
            } catch (e: Exception) {
                if (auth.currentUser != null) {
                    auth.currentUser!!.delete().await()
                }
                e.message?.apply {
                    if (this.contains("no user record corresponding")) {
                        emailError.postValue(2)
                    } else if (this.contains("The password is invalid")) {
                        passwordError.postValue(2)
                    } else if (this.contains("The email address is already in use by another account")) {
                        emailError.postValue(3)
                    }
                    Log.e(tag, e.message.toString())
                }
            }
        }
    }

    fun onCheckListener(radioGroup: RadioGroup){
          when(radioGroup.checkedRadioButtonId){
              R.id.teacher->selectedItem=0
              R.id.Student->selectedItem=1
              else->selectedItem=-1
          }
    }
    private fun valdiate():Boolean {
        var haserror=false
        var regex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        val emailpattern = Pattern.compile(regex)
        regex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$"
        Log.d(tag,email.value!!)
        Log.d(tag,name.value!!)
        Log.d(tag,password.value!!)
        val passwordpattern = Pattern.compile(regex)
        if(!_modeSignIn.value!!){
            if( name.value.isNullOrEmpty()){
                nameError.value=1
                haserror=true
            }else{
                nameError.value=0
            }
            if (selectedItem==-1){
                haserror=true
                radioError.postValue(1)
            }
            //radiobutton
        }
        if(email.value.isNullOrEmpty()||!emailpattern.matcher(email.value!!).matches()){
            emailError.value=1
            haserror=true
        }
        else{
            emailError.value=0
        }
        if(password.value.isNullOrEmpty()||!passwordpattern.matcher(password.value!!).matches()){
            passwordError.value=1
            haserror=true
        }
        else{
            passwordError.value=0
        }
        return !haserror
    }
    init {
        if(auth.currentUser!=null)
            _navigateToHomeFragment.postValue(true)
    }
}