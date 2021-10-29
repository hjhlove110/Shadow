package com.dresses.library.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.*
import android.widget.ImageView

object ViewAnimationUtils {
    fun runViewRotateOpenAnimation(view: View, isOpen: Boolean) {
        if (view != null) {
            val animation = ObjectAnimator.ofFloat(
                view, "rotation"
                , if (isOpen) 0f else 180f
                , if (isOpen) 180f else 0f
            )
            animation.duration = 200
            animation.start()
        }
    }

    fun runViewAlphaOpenAnimation(view: View, isOpen: Boolean) {
        val animation = ObjectAnimator.ofFloat(
            view, "alpha"
            , if (isOpen) 0f else 0.5f
            , if (isOpen) 0.5f else 0f
        )
        animation.duration = 200
        animation.start()
    }

    fun runViewUpToHideAnimator(
        view: View,
        isShow: Boolean,
        hideAction: Int = View.GONE,
        duration: Long = 200
    ) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val height = view.measuredHeight

        val animTrans = ObjectAnimator.ofFloat(
            view, "translationY", if (isShow) -height.toFloat() else 0f
            , if (isShow) 0f else -height.toFloat()
        )

        val animAlpha = ObjectAnimator.ofFloat(
            view, "alpha", if (isShow) 0f else 1f
            , if (isShow) 1f else 0f
        )
        view.visibility = View.VISIBLE

        val set = AnimatorSet()

        set.duration = duration
        set.playTogether(animTrans, animAlpha)

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    view.visibility = hideAction
                } else {
                    view.visibility = View.VISIBLE
                }
            }

        })
        set.start()
    }

    fun runViewDownToHideAnimator(view: View, isShow: Boolean, hideAction: Int = View.GONE) {
        if (view == null) {
            return
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val height = view.measuredHeight

        val animTrans = ObjectAnimator.ofFloat(
            view, "translationY", if (isShow) height.toFloat() else 0f
            , if (isShow) 0f else height.toFloat()
        )

        val animAlpha = ObjectAnimator.ofFloat(
            view, "alpha", if (isShow) 0f else 1f
            , if (isShow) 1f else 0f
        )
        view.visibility = View.VISIBLE

        val set = AnimatorSet()

        set.duration = 200
        set.playTogether(animTrans, animAlpha)

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    view.visibility = hideAction
                } else {
                    view.visibility = View.VISIBLE
                }
            }

        })
        set.start()
    }


    fun runViewRightToHideAnimator(view: View, isShow: Boolean, hideAction: Int = View.GONE) {
        if (view == null) {
            return
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val width = view.measuredWidth

        val animTrans = ObjectAnimator.ofFloat(
            view, "translationX", if (isShow) width.toFloat() else 0f
            , if (isShow) 0f else width.toFloat()
        )

        val animAlpha = ObjectAnimator.ofFloat(
            view, "alpha", if (isShow) 0f else 1f
            , if (isShow) 1f else 0f
        )
        view.visibility = View.VISIBLE

        val set = AnimatorSet()

        set.duration = 200
        set.playTogether(animTrans, animAlpha)

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    view.visibility = hideAction
                } else {
                    view.visibility = View.VISIBLE
                }
            }

        })
        set.start()
    }

    fun runViewLeftToHideAnimator(view: View, isShow: Boolean, hideAction: Int = View.GONE) {
        if (view == null) {
            return
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val width = view.measuredWidth

        val animTrans = ObjectAnimator.ofFloat(
            view, "translationX", if (isShow) -width.toFloat() else 0f
            , if (isShow) 0f else -width.toFloat()
        )

        val animAlpha = ObjectAnimator.ofFloat(
            view, "alpha", if (isShow) 0f else 1f
            , if (isShow) 1f else 0f
        )
        view.visibility = View.VISIBLE

        val set = AnimatorSet()

        set.duration = 200
        set.playTogether(animTrans, animAlpha)

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    view.visibility = hideAction
                } else {
                    view.visibility = View.VISIBLE
                }
            }

        })
        set.start()
    }

    fun rotateViewU(view: View, isOpen: Boolean) {
//        val animRotate = ObjectAnimator.ofFloat(view,"rotation",0f,180f)
        val animRotate = RotateAnimation(
            if (isOpen) 0f else 180f, if (isOpen) 180f else 0f
            , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        animRotate.duration = 200
        animRotate.isFillEnabled = true
        animRotate.fillAfter = true
        view.startAnimation(animRotate)
    }

    fun showScaleAnim(view: View, from: Float = 0.9f, to: Float = 1f, isLoop: Boolean = true) {
        val anim = ScaleAnimation(
            from,
            to,
            from,
            to,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = 3000
        anim.interpolator = CycleInterpolator(2f)
        anim.repeatCount = -1
        view.startAnimation(anim)
    }

    fun stopScale(view: ImageView) {
        view.animation?.cancel()
        view.visibility = View.INVISIBLE
    }


    fun flipAnimatorXViewShow(
        oldView: View,
        newView: View,
        time: Long
    ) {
        val animator1 =
            ObjectAnimator.ofFloat(oldView, "rotationY", 0f, 90f)
        val animator2 =
            ObjectAnimator.ofFloat(newView, "rotationY", -90f, 0f)
        animator2.interpolator = OvershootInterpolator(2.0f)
        animator1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                oldView.visibility = View.GONE
                animator2.setDuration(time).start()
                newView.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator1.setDuration(time).start()
    }
}