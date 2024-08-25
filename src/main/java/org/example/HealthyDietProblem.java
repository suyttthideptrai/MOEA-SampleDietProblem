package org.example;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.Permutation;

/**
 * Problem mẫu, tối ưu thực đơn của một ngày sao cho nhiều dinh dưỡng nhưng lại ít tiền và rẻ =))
 * Số tiêu chí: 3
 * Số biến: 1
 * Kiểu của biến: Permutation nhưng chỉ lấy 3(số bữa trong ngày) số đầu của chuỗi Permutation
 */
public class HealthyDietProblem implements Problem {

    //Data set
    private final String[] NAMES = {"Apple", "Bread", "Chicken", "Rice", "Milk", "Burger", "Fries", "Kebab", "Salad"};
    private final double[] CALORIES = {52, 265, 239, 130, 42, 150, 60, 80, 30};
    private final double[] COSTS = {0.30, 0.50, 1.50, 0.40, 0.20, 1.2, 1, 1.5, 0.55};
    private final double[] PROTEINS = {0.3, 9, 27, 2.4, 3.4, 15, 3, 20, 2};

    private final int MEAL_PER_DAY = 3;

    public HealthyDietProblem() {
        super();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getNumberOfVariables() {
        return 1;
    }

    @Override
    public int getNumberOfObjectives() {
        return 3;
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    /**
     * Hàm core của Problem
     * @param solution
     */
    @Override
    public void evaluate(Solution solution) {
        int[] trimmedSolution = trimSolution(solution, MEAL_PER_DAY);

        // Tính tổng tất cả các tiêu chí
        double totalCalories = 0.0;
        double totalCost = 0.0;
        double totalProtein = 0.0;

        String[] mealNames = new String[MEAL_PER_DAY];
        for (int i = 0; i < MEAL_PER_DAY; i++) {
            int mealIndex = trimmedSolution[i];
            mealNames[i] = NAMES[mealIndex];
            totalCalories += CALORIES[mealIndex];
            totalCost += COSTS[mealIndex];
            totalProtein += PROTEINS[mealIndex];
        }
        // Set tên để lấy ra
        solution.setAttribute("names", mealNames);
        // Set set giá trị cho từng tiêu chí
        /**
         Framework sẽ tối ưu theo kiểu giảm giá trị tiêu chí xuống nên -> nếu muốn minimize thì chuyền thẳng tham số
         nếu muốn maximize chỉ số thì negate chính nó.
         */
        solution.setObjective(0, totalCalories);  // Minimize calories
        solution.setObjective(1, totalCost);      // Minimize cost
        solution.setObjective(2, -totalProtein);  // Maximize protein
    }

    /**
     * Lấy kết quả đã được random
     * @param solution Solution
     * @param size int
     * @return int[]
     */
    private int[] trimSolution(Solution solution, int size) {
        Permutation generated = (Permutation) solution.getVariable(0);
        int[] decodedVar = generated.toArray();
        int[] result = new int[size];
        System.arraycopy(decodedVar, 0, result, 0, size);
        return result;
    }

    /**
     * Định nghĩa cho việc generate Solution mới
     * @return Solution
     */
    @Override
    public Solution newSolution() {
        Solution solution = new Solution(this.getNumberOfVariables(), this.getNumberOfObjectives());
        solution.setVariable(this.getNumberOfVariables() - 1, new Permutation(NAMES.length));
        return solution;
    }

    @Override
    public void close() {

    }

}
