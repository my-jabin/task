package com.jiujiu.task.ui.base

import androidx.lifecycle.ViewModel

import com.jiujiu.task.data.DataManager

import org.jetbrains.anko.AnkoLogger

open class BaseViewModel(protected val dataManager: DataManager) : ViewModel(), AnkoLogger