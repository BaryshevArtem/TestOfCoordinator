package com.example.testofcoordinator

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout


class ScrollAwareAppBarBehavior(context: Context, attrs: AttributeSet) :
    AppBarLayout.Behavior(context, attrs) {

    private var isToolbarHidden = false
    private var offsetAnimator: ObjectAnimator? = null

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild, target, axes, type
        )
    }

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

        val itemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (firstVisibleItemPosition >= itemCount / 2 && dy > 0 && !isToolbarHidden) {
            animateOffset(child, -child.height)
            isToolbarHidden = true
        } else if (firstVisibleItemPosition < itemCount / 2 && dy < 0 && isToolbarHidden) {
            animateOffset(child, 0)
            isToolbarHidden = false
        }
    }

    private fun animateOffset(appBarLayout: AppBarLayout, targetOffset: Int) {
        offsetAnimator?.cancel()

        offsetAnimator =
            ObjectAnimator.ofInt(this, "topAndBottomOffset", topAndBottomOffset, targetOffset)
                .apply {
                    duration = 300
                    start()
                }
    }
}