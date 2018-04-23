package com.example.a37046.foods;

import com.example.a37046.foods.activity.LoginActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void s2(){
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.landingJudgment("lnn","11");

    }
}