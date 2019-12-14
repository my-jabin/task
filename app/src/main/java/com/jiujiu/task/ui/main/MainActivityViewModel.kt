package com.jiujiu.task.ui.main

import com.jiujiu.task.data.DataManager
import com.jiujiu.task.ui.base.BaseViewModel

import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager)
