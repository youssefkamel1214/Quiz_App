package com.example.quizapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Question(val question:String?=null,val answer:String?=null,val options:List<String>?=null):
    Parcelable
