package com.dresses.library.utils

import android.os.Build
import java.lang.reflect.Field
import java.lang.reflect.Method


class InvokeFragmentManagerUtils {
    private var noteStateNotSavedMethod: Method? = null
    private var fragmentMgr: Any? = null
    private val activityClassName =
        arrayOf("Activity", "FragmentActivity")

    private fun invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod?.invoke(fragmentMgr)
                return
            }
            var cls: Class<*>? = javaClass
            do {
                cls = cls!!.superclass
            } while (!(activityClassName.get(0).equals(cls!!.simpleName)
                        || activityClassName.get(1).equals(cls.simpleName))
            )
            val fragmentMgrField: Field? = prepareField(cls, "mFragments")
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this)
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr!!, "noteStateNotSaved")
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod?.invoke(fragmentMgr)
                }
            }
        } catch (ex: Exception) {
        }
    }

    @Throws(NoSuchFieldException::class)
    private fun prepareField(c: Class<*>?, fieldName: String): Field? {
        var c = c
        while (c != null) {
            return try {
                val f: Field = c.getDeclaredField(fieldName)
                f.setAccessible(true)
                f
            } finally {
                c = c.superclass
            }
        }
        throw NoSuchFieldException()
    }

    private fun getDeclaredMethod(
        `object`: Any,
        methodName: String,
        vararg parameterTypes: Class<*>
    ): Method? {
        var method: Method? = null
        var clazz: Class<*>? = `object`.javaClass
        while (clazz != Any::class.java) {
            try {
                method = clazz!!.getDeclaredMethod(methodName, *parameterTypes)
                return method
            } catch (e: Exception) {
            }
            clazz = clazz!!.superclass
        }
        return null
    }
}