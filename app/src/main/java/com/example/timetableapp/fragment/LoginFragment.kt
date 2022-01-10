package com.example.timetableapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.timetableapp.R
import com.example.timetableapp.Validations
import com.example.timetableapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            loginFragment = this@LoginFragment
            btLogin.setOnClickListener{


                        if(username.text.toString()=="AdminAccount" && password.text.toString()=="MCET1234"){

                            findNavController().navigate(R.id.action_loginFragment_to_adminFragment)
                        }
                        else{

                            findNavController().navigate(R.id.action_loginFragment_to_startFragment)
                        }

            }
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}