package com.wishnewjam.radiologytest.ui.settings

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Params(val complexities: List<String>?, val themes: List<String>?) : Parcelable