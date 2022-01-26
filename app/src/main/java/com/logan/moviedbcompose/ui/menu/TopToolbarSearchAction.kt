package com.logan.moviedbcompose.ui.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.logan.moviedbcompose.R

sealed class TopToolbarSearchAction(

    @StringRes val label: Int,
    @DrawableRes val icon: Int

) {

    object  Search : TopToolbarSearchAction(R.string.Search, R.drawable.ic_baseline_search_24)

}