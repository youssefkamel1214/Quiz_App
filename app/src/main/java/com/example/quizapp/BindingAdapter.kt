package com.example.quizapp

import android.graphics.Bitmap
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("Name_error")
fun Name_error(inputfield:EditText,error:Int){
    if (error>0){
        inputfield.error=inputfield.context.getString(R.string.nameerror)
    }
    else{
        inputfield.error=null
    }
}
@BindingAdapter("password_error")
fun password_error(inputfield:EditText,error:Int){
    if (error==1){
        inputfield.error=inputfield.context.getString(R.string.passwordwrongfromat)
    }
    else if(error==2){
        inputfield.error=inputfield.context.getString(R.string.firebase_wrong_password)
    }
    else{
        inputfield.error=null
    }
}
@BindingAdapter("email_error")
fun email_error(inputfield:EditText,error:Int){
    when (error) {
        1 -> {
            inputfield.error=inputfield.context.getString(R.string.email_wrong_format)
        }
        2 -> {
            inputfield.error=inputfield.context.getString(R.string.firebase_no_email)
        }
        3->{
            inputfield.error=inputfield.context.getString(R.string.firebase_email_exists)
        }
        else -> {
            inputfield.error=null
        }
    }
}

@BindingAdapter("setvisibility")
fun setvisibility(view:View,boolean: Boolean){
    if(boolean){
        view.visibility=View.VISIBLE
    }
    else{
        view.visibility=View.GONE
    }
}
@BindingAdapter("set_form_authentication_button_text")
fun set_form_authentication_button_text(button:AppCompatButton,boolean: Boolean){
    if(boolean){
        button.text=button.context.getString(R.string.log_in)
    }
    else{
        button.text=button.context.getString(R.string.sign_up)
    }
}
@BindingAdapter("set_form_authentication_state_changer")
fun set_form_authentication_state_changer(text:TextView,boolean: Boolean){
    if(boolean){
        text.text=text.context.getString(R.string.change_to_sign_up)
    }
    else{
        text.text=text.context.getString(R.string.change_to_log_in)
    }
}
@BindingAdapter("set_visibility_using_state")
fun set_visibility_using_state(view: View,state:Int){
    if(state!=-1)
        view.visibility=View.VISIBLE
    else
        view.visibility=View.GONE
}

