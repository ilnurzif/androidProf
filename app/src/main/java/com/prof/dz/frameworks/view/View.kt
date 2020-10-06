package com.prof.dz.frameworks.view

import com.prof.dz.entities.DataModel

interface View {
    fun renderData(dataModel: DataModel)
}