package com.ingridsantos.ceibatechnicaltest.util

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf

fun checkViewIsDisplayedById(@IdRes viewId: Int) {
    onView(withId(viewId))
        .check(matches(isDisplayed()))
}

fun checkViewIsNotDisplayedById(@IdRes viewId: Int) {
    onView(withId(viewId)).check(matches(not(isDisplayed())))
}

fun checkViewIsDisplayedByText(viewText: String) {
    onView(withText(viewText))
        .check(matches(isDisplayed()))
}

fun performClickByText(viewText: String) {
    onView(withText(viewText)).perform(ViewActions.click())
}

fun performClickById(viewId: Int) {
    onView(withId(viewId)).perform((ViewActions.click()))
}

fun checkViewIsDisplayedByText(@StringRes viewText: Int) {
    onView(withText(viewText)).check(matches(isDisplayed()))
}

fun checkViewWithIdAndTextIsDisplayed(@IdRes viewId: Int, @StringRes viewText: Int) {
    onView(withId(viewId))
        .check(matches(withText(viewText)))
        .check(matches(isDisplayed()))
}

fun checkViewWithIdAndTextIsDisplayed(@IdRes viewId: Int, viewText: String) {
    onView(withId(viewId))
        .check(matches(withText(viewText)))
        .check(matches(isDisplayed()))
}

fun typeTextById(@IdRes viewId: Int, newString: String) {
    onView(withId(viewId)).perform(ViewActions.typeText(newString))
}

fun checkViewHint(@IdRes viewId: Int, viewText: String) {
    onView(withId(viewId))
        .check(matches(withHint(viewText)))
}

fun checkViewHint(@IdRes viewId: Int, @StringRes viewText: Int) {
    onView(withId(viewId))
        .check(matches(withHint(viewText)))
}

fun scrollToRecyclerPosition(@IdRes viewId: Int, position: Int) {
    onView(withId(viewId))
        .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, scrollTo()))
}

fun checkViewIsEnableById(@IdRes viewId: Int) {
    onView(withId(viewId))
        .check(matches(isEnabled()))
}

fun checkViewIsDisableById(@IdRes viewId: Int) {
    onView(withId(viewId))
        .check(matches(not(isEnabled())))
}

fun checkDisableRecyclerAtPositionById(
    @IdRes recyclerViewId: Int,
    position: Int,
    @IdRes res: Int
) {
    onView(withId(recyclerViewId))
        .check(
            matches(
                recyclerItemAtPosition(
                    position,
                    hasDescendant(allOf(withId(res), isDisplayed()))
                )
            )
        )
}

fun checkViewWithTextIsDisplayedAtPosition(@IdRes recyclerId: Int, position: Int, text: String) {
    onView(withId(recyclerId))
        .check(
            matches(recyclerItemAtPosition(position, hasDescendant(withText(text))))
        )
}

fun performClickAndDisplayedRecyclerAtPositionById(
    @IdRes recyclerId: Int,
    position: Int,
    @IdRes clickedViewId: Int
) {
    onView(withId(recyclerId))
        .check(
            matches(
                recyclerItemAtPosition(
                    position,
                    hasDescendant(AllOf.allOf(withId(clickedViewId), isDisplayed()))
                )
            )
        ).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickOnViewChild(clickedViewId)
            )
        )
}

fun closedKeyBoard() {
    onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
}
