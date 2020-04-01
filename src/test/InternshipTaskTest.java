package test;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import test.simpleTests.SimpleTest;
import test.middleTests.MiddleTest;

public class InternshipTaskTest {
    @Test
    public void runTests(){
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));


        resultReport(junit.run(SimpleTest.class), "Simple tests");
        resultReport(junit.run(MiddleTest.class), "Middle tests");


    }

    private static void resultReport(Result result, String testName) {
        System.out.println(testName + " finished. Result: Failures: " +
                result.getFailureCount() + ". Ignored: " +
                result.getIgnoreCount() + ". Tests run: " +
                result.getRunCount() + ". Time: " +
                result.getRunTime() + "ms.");
    }


}