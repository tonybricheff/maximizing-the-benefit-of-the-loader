package test.simpleTests;

import main.InternshipTask;
import main.models.InputReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class SimpleTest{

    @Test
    public void test1() throws FileNotFoundException {
        InternshipTask currentCase = new InternshipTask();
        InputStream inputStream = new FileInputStream("../maximizing-the-benefit-of-the-loader/src/test/simpleTests/testFiles/1.test");
        InputReader input = new InputReader(inputStream);
        Assert.assertEquals(7, currentCase.getSolution(input));
    }

    @Test
    public void test2() throws FileNotFoundException {
        InternshipTask currentCase = new InternshipTask();
        InputStream inputStream = new FileInputStream("../maximizing-the-benefit-of-the-loader/src/test/simpleTests/testFiles/2.test");
        InputReader input = new InputReader(inputStream);
        Assert.assertEquals(23, currentCase.getSolution(input));
    }

    @Test
    public void test3() throws FileNotFoundException {
        InternshipTask currentCase = new InternshipTask();
        InputStream inputStream = new FileInputStream("../maximizing-the-benefit-of-the-loader/src/test/simpleTests/testFiles/3.test");
        InputReader input = new InputReader(inputStream);
        Assert.assertEquals(1, currentCase.getSolution(input));
    }

    @Test
    public void test4() throws FileNotFoundException {
        InternshipTask currentCase = new InternshipTask();
        InputStream inputStream = new FileInputStream("../maximizing-the-benefit-of-the-loader/src/test/simpleTests/testFiles/4.test");
        InputReader input = new InputReader(inputStream);
        Assert.assertEquals(5, currentCase.getSolution(input));
    }

    @Test
    public void test5() throws FileNotFoundException {
        InternshipTask currentCase = new InternshipTask();
        InputStream inputStream = new FileInputStream("../maximizing-the-benefit-of-the-loader/src/test/simpleTests/testFiles/5.test");
        InputReader input = new InputReader(inputStream);
        Assert.assertEquals(1, currentCase.getSolution(input));
    }

}
