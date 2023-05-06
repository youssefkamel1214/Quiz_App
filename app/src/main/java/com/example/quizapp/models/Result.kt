package com.example.quizapp.models

data class Result(val student:String?=null,val studentId:String?,val examName:String?=null,val score:Int?=null,
                  val examId:String?=null,val teacher:String?=null,val teacherId:String?=null ,val questions:Int?=null)
