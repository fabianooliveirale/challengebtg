package com.example.challengebtg.ui


import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.challengebtg.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UiConvertTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun uiConvertTest() {
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
        SystemClock.sleep(900)
        textInputEditText.perform(replaceText("1"), closeSoftKeyboard())

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
        SystemClock.sleep(900)
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
        SystemClock.sleep(900)
        appCompatImageButton.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.buttonConvert), withText("Convert"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(900)
        appCompatButton2.perform(click())

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
        SystemClock.sleep(900)
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
        SystemClock.sleep(900)
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
        SystemClock.sleep(900)
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
        SystemClock.sleep(900)
        appCompatTextView4.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.buttonConvert), withText("Convert"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        SystemClock.sleep(900)
        appCompatButton3.perform(click())

        val appCompatButton4 = onView(
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
        SystemClock.sleep(900)
        appCompatButton4.perform(click())

        val appCompatImageButton2 = onView(
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
        SystemClock.sleep(900)
        appCompatImageButton2.perform(click())
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
