package com.example.quizapp.models

data class Quiz(val name:String?=null,val id:String?=null,val teacherId: String?,val teacher:String?=null,val questions:List<Question>?=null)
