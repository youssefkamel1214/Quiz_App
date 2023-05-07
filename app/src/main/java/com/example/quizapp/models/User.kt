package com.example.quizapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val email:String?=null,val type:String?=null,val name:String?=null):Parcelable
