package com.fatih.whatsappclonekotlin.module

import android.content.Context
import androidx.room.Room
import com.fatih.whatsappclonekotlin.repository.*
import com.fatih.whatsappclonekotlin.room.UserDao
import com.fatih.whatsappclonekotlin.room.UserDb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@dagger.Module
object Module {

    @Provides
    @Singleton
    fun provideFirebaseAuth()= FirebaseAuth.getInstance()
    
    @Provides
    @Singleton
    fun provideFirebaseFirestore()= FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthInterface(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore)=AuthenticationRepository(firebaseAuth,firestore) as AuthenticationRepositoryInterface

    @Provides
    @Singleton
    fun provideChatInterface(firebaseFirestore: FirebaseFirestore)=ChatRepository(firebaseFirestore) as ChatRepositoryInterface

    @Provides
    @Singleton
    fun provideProfileInterface(firestore: FirebaseFirestore,reference: StorageReference,userDao: UserDao)=ProfileRepository(firestore,reference,userDao) as ProfileRepositoryInterface

    @Provides
    @Singleton
    fun provideFirebaseStorage()=FirebaseStorage.getInstance().reference

    @Provides
    @Singleton
    fun provideUserDb(@ApplicationContext context: Context)= Room.databaseBuilder(context,UserDb::class.java,"UserDatabase").build().userDao()

    @Provides
    @Singleton
    fun provideMessageRepo(firestore: FirebaseFirestore)=MessageRepository(firestore) as MessageRepositoryInterface

}