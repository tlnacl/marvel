package com.example.marvelchallenge.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.math.BigInteger
import java.security.MessageDigest

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

inline fun <reified VM : ViewModel> Fragment.provideViewModel(provider: ViewModelProvider.Factory): VM {
    return ViewModelProvider(this, provider).get(VM::class.java)
}