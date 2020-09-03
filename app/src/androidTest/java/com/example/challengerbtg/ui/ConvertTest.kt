package com.example.challengerbtg.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.challengerbtg.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class ConvertTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun convertTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.buttonHistory), withText("History"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatImageButton = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.editTextAmount),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("1"), closeSoftKeyboard())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.textFrom),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.textView), withText("AED: United Arab Emirates Dirham"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        val appCompatTextView3 = onView(
            allOf(
                withId(R.id.textTo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatTextView3.perform(click())

        val appCompatTextView4 = onView(
            allOf(
                withId(R.id.textView), withText("AED: United Arab Emirates Dirham"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView4.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.buttonConvert), withText("Convert"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.buttonHistory), withText("History"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
