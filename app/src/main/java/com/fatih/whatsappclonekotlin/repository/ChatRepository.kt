package com.fatih.whatsappclonekotlin.repository


import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ChatRepository @Inject constructor(private val firestore: FirebaseFirestore ):ChatRepositoryInterface {

    override suspend fun getUsersFromFirebase(lambda:(List<User>)->Unit) {
        currentUser.value?.let { myCurrentUser->
            firestore.collection("User").whereNotEqualTo("uid",
                myCurrentUser.uid).addSnapshotListener { value, error ->
                val users = mutableListOf<User>()
                value?.let {
                    for(document in it.documents){
                        val user = document.toObject(User::class.java)
                        user?.let { user1->
                            users.add(user1)
                        }
                    }
                    users.sortByDescending {
                        it.infoHashMap?.let {
                            it[myCurrentUser.uid]?.let {
                                it.date
                            }?:0
                        }?:0
                    }
                    lambda(users)
                }

            }
        }

        }

}