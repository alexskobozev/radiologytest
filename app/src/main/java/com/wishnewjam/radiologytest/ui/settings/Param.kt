package com.wishnewjam.radiologytest.ui.settings

class Param(val type: Int, val value: String?) {
    var checked = true

    companion object {
        const val TYPE_COMPLEXITY = 0
        const val TYPE_THEME = 1
        const val TYPE_DIVIDER = 3
    }
}