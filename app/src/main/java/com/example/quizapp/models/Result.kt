package com.example.quizapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(val student:String?=null,val studentId:String?=null,val examName:String?=null,val score:Int?=null,
                  val examId:String?=null,val teacher:String?=null,val teacherId:String?=null ,val questions:Int?=null):Parcelable
