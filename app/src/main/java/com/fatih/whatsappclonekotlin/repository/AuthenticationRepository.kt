package com.fatih.whatsappclonekotlin.repository

import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Instance.setCurrentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firestore: FirebaseFirestore):AuthenticationRepositoryInterface {


    override suspend fun signInWithEmailAndPassword(email: String, password: String,lambda:(Resource<String>)->Unit) {

     try {
         firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
             if(it.isSuccessful){
                 it.result.user?.let { user->
                     lambda(Resource.success("Success"))
                     setCurrentUser()
                 }
             }else{
                 lambda(Resource.error(it.exception?.message,it.exception?.message))
                 setCurrentUser()
             }
         }
        }catch (e:Exception){
            lambda(Resource.error(e.message,e.message))
            setCurrentUser()
     }
    }

    override suspend fun signUpWithEmailAndPassword(userName:String,email: String, password: String,lambda:(Resource<String>)->Unit){
        if(userName.isNotEmpty()&&email.isNotEmpty()&&password.isNotEmpty()){
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        it.result.user?.let { fUser->
                            val myUser=User(fUser.email!!,password,null,null,fUser.uid,userName)
                            firestore.collection("User").document(fUser.uid).set(myUser).addOnCompleteListener { task->
                                if(task.isSuccessful){
                                    setCurrentUser()
                                    lambda(Resource.success("Success"))
                                }
                            }
                        }

                    }else{
                        it.exception?.let { exception->
                            lambda(Resource.error(exception.message,exception.message))
                            setCurrentUser()
                        }
                    }
                }


            }catch (e:Exception){
                lambda(Resource.error(data = e.message,e.message))
                setCurrentUser()
            }
        }else{
            lambda(Resource.error("Fill all blank lines","Fill all blank lines"))
            setCurrentUser()
        }

    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
        setCurrentUser()
    }
}