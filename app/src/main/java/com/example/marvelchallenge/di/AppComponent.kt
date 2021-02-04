package com.example.marvelchallenge.di

import com.example.marvelchallenge.di.viewmodel.ViewModelModule
import com.example.marvelchallenge.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, ViewModelModule::class]
)
@Singleton
interface AppComponent {
    fun inject(into: MainFragment)
//    fun inject(into: SearchFragment)
}