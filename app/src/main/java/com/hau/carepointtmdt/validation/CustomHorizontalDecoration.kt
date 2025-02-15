package com.hau.carepointtmdt.validation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomHorizontalDecoration(private val space: Int, private val endSpace: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position < itemCount - 1) {
            outRect.right = space
        } else {
            outRect.right = endSpace
        }
    }

}