package test.TestSuite;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;


public class ProgramTests {

    @Test
    public void test() {
        File programsDirectory = new File("./programs");
        System.out.println(programsDirectory);
    }


}