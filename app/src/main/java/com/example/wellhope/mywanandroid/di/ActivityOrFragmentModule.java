package com.example.wellhope.mywanandroid.di;

import com.example.wellhope.mywanandroid.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityOrFragmentModule {

    @ContributesAndroidInjector()
    abstract MainActivity mainActivity();

}
