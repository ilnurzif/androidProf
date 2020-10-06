package com.prof.dz.frameworks.network.model

import com.google.gson.annotations.SerializedName

class SearchResult(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)
