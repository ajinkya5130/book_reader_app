package com.ajinkya.bookreaderapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajinkya.bookreaderapp.model.UserModel
import com.ajinkya.bookreaderapp.utils.ToastEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val TAG = "LoginViewModel"
    private val _toastMessage = MutableLiveData<Pair<String, ToastEnum>>()
    val toastMessage: LiveData<Pair<String, ToastEnum>> = _toastMessage

    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> = _loading

    fun createUserWithEmail(email: String, pass: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val displayName = task.result.user?.let { email.split("@")[0] }
                    createUser(displayName)
                    home()
                } else {
                    task.exception?.let {
                        //_toastMessage.value = Pair(it.message!!,ToastEnum.LongDuration)


                    }
                    Log.e(TAG, "createUserWithEmail: ${task.exception!!.message} ")
                }
                _loading.value = false

            }
        }

    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val userInfo = UserModel(
            userId = userId.toString(), displayName = displayName.toString(), profileUrl = "",
            profession = "Developer", quote = "There is will, there is a way!", id = null
        ).toMap()
        FirebaseFirestore.getInstance().collection("users").add(userInfo)


    }

    fun signInWithEmailAndPass(email: String, pass: String, home: () -> Unit) =
        viewModelScope.launch {

            try {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            Log.e(
                                TAG,
                                "task Successful signInWithEmailAndPass: ${task.result.user.toString()}"
                            )
                            home()
                            // TODO("take to home screen")
                        } else {
                            Log.e(TAG, "task failed signInWithEmailAndPass: ${task.result}")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "task exception signInWithEmailAndPass: ${e.message}")
                    }

                }

            } catch (ex: Exception) {

                Log.e(TAG, "login exception signInWithEmailAndPass: ${ex.message}")
            }

        }


}