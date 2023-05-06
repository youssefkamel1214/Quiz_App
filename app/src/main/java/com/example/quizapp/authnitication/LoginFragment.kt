package com.example.quizapp.authnitication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {



    private  val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(layoutInflater,container,false)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this
        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, _ ->viewModel.onCheckListener(group)  })
        viewModel.radioError.observe(viewLifecycleOwner){
            if(it>0)
                Toast.makeText(requireContext(),"must say if you are teacher or student",Toast.LENGTH_SHORT).show()
        }
        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner){
            if(it) {
                if (viewModel.user!!.type == "teacher")
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTeacherFragment())
                else {

                }
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}