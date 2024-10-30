package com.example.testofcoordinator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

class ScrollAwareAppBarBehavior(
    context: Context,
    attrs: AttributeSet
) : AppBarLayout.Behavior(context, attrs) {

    private var isToolbarVisible = true

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        val recyclerView = target as? RecyclerView ?: return
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return

        val middlePosition = (layoutManager.itemCount / 2)
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (dy > 0 && isToolbarVisible) {
            child.setExpanded(false, true)
            isToolbarVisible = false
        } else if (dy < 0 && !isToolbarVisible && firstVisibleItemPosition <= middlePosition) {
            child.setExpanded(true, true)
            isToolbarVisible = true
        }
    }
}
