package org.example;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Problem problem = new HealthyDietProblem();
        Algorithm algorithm = new NSGAII(
                problem);
        algorithm.step();

        NondominatedPopulation population =  algorithm.getResult();
        for (Solution solution : population) {
            String[] names = (String[]) solution.getAttribute("names");
            System.out.println(Arrays.toString(names));
            System.out.println("Calories: " + solution.getObjective(0));
            System.out.println("Cost: " + solution.getObjective(1));
            System.out.println("Protein: " + -solution.getObjective(2)); //negate để lấy giá trị expect
            System.out.println("-----------");
        }
    }
}