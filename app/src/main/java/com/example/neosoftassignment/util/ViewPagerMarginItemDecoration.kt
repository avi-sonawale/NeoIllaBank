package com.example.neosoftassignment.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ViewPagerMarginItemDecoration(context: Context, @DimenRes marginInDP: Int) :
    RecyclerView.ItemDecoration() {

    private val marginInPx: Int =
        context.resources.getDimension(marginInDP).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = marginInPx
        outRect.left = marginInPx
    }

}