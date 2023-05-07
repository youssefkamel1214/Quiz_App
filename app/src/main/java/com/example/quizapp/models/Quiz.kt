package com.example.quizapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quiz(val name:String?=null,val id:String?=null,val teacherId: String?=null,val teacher:String?=null,val questions:List<Question>?=null):
    Parcelable
