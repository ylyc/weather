package com.twitter.challenge.di

import com.twitter.challenge.ui.MainViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
}