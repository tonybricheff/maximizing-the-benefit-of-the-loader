package main;

import main.models.InputReader;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        InternshipTask solution = new InternshipTask();

        InputStream inputStream = new FileInputStream("loader.in");
        InputReader input = new InputReader(inputStream);

        System.out.println("Max value = " + solution.getSolution(input));
    }
}
