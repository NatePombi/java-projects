import main.java.utilities.ListUtil;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class App {
    public static void main(String[] args) {

        // Test data
        List<String> fruits = List.of("Apple", "Banana", "Avocado", "Peach", "Pineapple", "Mango");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Method 1: filterStartsWith
        System.out.println("Method 1 - filterStartsWith:");
        List<String> filteredFruits = ListUtil.filterStartsWith(fruits, 'A');
        System.out.println(filteredFruits);
        System.out.println();

        // Method 2: flattenList
        System.out.println("Method 2 - flattenList:");
        List<List<String>> fruitBaskets = List.of(
                List.of("Apple", "Strawberry", "Cherry"),
                List.of("Banana", "Lemon", "Pineapple"),
                List.of("Blueberry", "Blackberry", "Plum")
        );
        List<String> flattenedFruits = ListUtil.flattenList(fruitBaskets);
        System.out.println(flattenedFruits);
        System.out.println();

        // Method 3: partitionByCondition
        System.out.println("Method 3 - partitionByCondition:");
        Predicate<String> condition = s -> s.length() > 5;
        Map<Boolean, List<String>> partitionedFruits = ListUtil.partitionByCondition(fruits, condition);
        System.out.println(partitionedFruits);
        System.out.println();

        // Method 4: findMax
        System.out.println("Method 4 - findMax:");
        Integer maxNumber = ListUtil.findMax(numbers, Comparator.naturalOrder());
        System.out.println("Max Number: " + maxNumber);
        System.out.println();

        // Method 5: findMin
        System.out.println("Method 5 - findMin:");
        Integer minNumber = ListUtil.findMin(numbers, Comparator.naturalOrder());
        System.out.println("Min Number: " + minNumber);
        System.out.println();

        // Method 6: filterAndCollect
        System.out.println("Method 6 - filterAndCollect:");
        List<String> longFruits = ListUtil.filterAndCollect(fruits, f -> f.length() > 5);
        System.out.println(longFruits);
        System.out.println();

        // Method 7: distinctSortedFilter
        System.out.println("Method 7 - distinctSortedFilter:");
        List<String> distinctSortedFruits = ListUtil.distinctSortedFilter(fruits, f -> f.length() > 5, Comparator.naturalOrder());
        System.out.println(distinctSortedFruits);
        System.out.println();

        // Method 8: transformAndCollect
        System.out.println("Method 8 - transformAndCollect:");
        List<String> transformedAndSortedFruits = ListUtil.transformAndCollect(fruits,
                n-> n.length()>5,
                String::toUpperCase,
                Comparator.<String>naturalOrder());
        System.out.println(transformedAndSortedFruits);
    }
}

