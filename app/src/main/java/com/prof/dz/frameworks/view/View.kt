package com.prof.dz.frameworks.view

import com.less.model.DataModel

interface View {
    fun renderData(dataModel: com.less.model.DataModel)
}