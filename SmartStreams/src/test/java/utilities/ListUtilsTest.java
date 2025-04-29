package test.java.utilities;

import main.java.utilities.ListUtil;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtilsTest {

    @Test
    public void filterStartsWith(){
        List<String> fruits = List.of("Apple","Avocado","Banana");

        List<String> result = ListUtil.filterStartsWith(fruits,'A');

        //assertions
        assertNotNull(result);
        assertEquals(2,result.size());
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("Avocado"));

    }

    @Test
    public void findMax(){

        List<Integer> num = List.of(5,3,6,2,12);

       int result1 = ListUtil.findMax(num, Comparator.naturalOrder());

       assertNotNull(result1);
       assertEquals(12,result1);

       //empty List check
       List<Integer> empty = List.of();
       assertThrows(NoSuchElementException.class, ()->{
           ListUtil.findMax(empty,Comparator.naturalOrder());
       });

       //null List check
        List<Integer> nul = null;
        assertThrows(NoSuchElementException.class, ()->{
           ListUtil.findMax(nul,Comparator.naturalOrder());
        });

    }

    @Test
    public void findMin(){

        List<Integer> num = List.of(5,3,6,2,12);

        int result = ListUtil.findMin(num,Comparator.naturalOrder());

        //assertions to check result output
        assertEquals(2,result);

        //empty List
        List<Integer> empty = List.of();
        assertThrows(NoSuchElementException.class, ()->{
            ListUtil.findMin(empty,Comparator.naturalOrder());
        });

        //null list
        List<Integer> nul = null;
        assertThrows(NoSuchElementException.class, ()->{
            ListUtil.findMin(nul,Comparator.naturalOrder());
        });
    }

    @Test
    public void flattenList(){

        List<List<String>> lists = List.of(
                List.of("Apple", "Strawberry", "Cherry"),
                List.of("Banana", "Lemon", "Pineapple"),
                List.of("Blueberry", "Blackberry", "Plum")
        );

        List<String> flatList = ListUtil.flattenList(lists);

        assertNotNull(flatList);
        assertEquals(9,flatList.size());
        assertEquals(List.of(
                "Apple", "Strawberry", "Cherry",
                "Banana", "Lemon", "Pineapple",
                "Blueberry", "Blackberry", "Plum"
        ), flatList);

        //empty list
        List<List<Integer>> empty = List.of();
        List<Integer> result = ListUtil.flattenList(empty);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        //null list
        List<String> nul = ListUtil.flattenList(null);
        assertNotNull(nul);
        assertTrue(nul.isEmpty());

    }

    @Test
    public void partitionByCondition(){

        List<String> fruits = List.of("Apple", "Banana", "Avocado", "Peach", "Pineapple", "Mango");

        Map<Boolean,List<String>> partition = ListUtil.partitionByCondition(fruits,n-> n.length() > 5);

        //assertion to check for null, map size, and outputs
        assertNotNull(partition);
        assertEquals(2,partition.size());
        assertEquals(List.of("Banana","Avocado","Pineapple"), partition.get(true));
        assertEquals(List.of("Apple", "Peach", "Mango"),partition.get(false));
    }

    @Test
    public void filterAndCollect(){

        List<String> fruits = List.of("Apple", "Banana", "Avocado", "Peach", "Pineapple", "Mango");

        List<String> result = ListUtil.filterAndCollect(fruits, f-> f.length()>5);

        //assertions to check for null, list size and list output
        assertNotNull(result);
        assertEquals(3,result.size());
        assertEquals(List.of(
                "Banana","Avocado","Pineapple"
        ),result);

        //empty List
        List<String> empty = List.of();
        List<String> result2 = ListUtil.filterAndCollect(empty, f-> f.length()>5);

        assertNotNull(result2);
        assertTrue(result2.isEmpty());

        //null condition
        List<String> nul = ListUtil.filterAndCollect(fruits,null);
        assertNotNull(nul);
        assertTrue(nul.isEmpty());
    }

    @Test
    public void transformAndCollect(){

        List<String> fruits = List.of("Apple", "Banana", "Avocado", "Peach", "Pineapple", "Mango");

        //initializing the Predicate,Comparator,Function
        Predicate<String> con = s-> s.length()>6;
        Comparator<String> com = Comparator.naturalOrder();
        Function<String,String> func = String::toUpperCase;

        List<String> result = ListUtil.transformAndCollect(fruits,con,func,com);
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(List.of("AVOCADO","PINEAPPLE"),result);


        //empty list
        List<String> empty = List.of();
        List<String> result2 = ListUtil.transformAndCollect(empty,con,func,com);
        assertNotNull(result2);
        assertTrue(result2.isEmpty());

        //null condition
        List<String> nul = ListUtil.transformAndCollect(fruits,null,func,com);
        assertNotNull(nul);
        assertTrue(nul.isEmpty());
    }


    @Test
    public void distinctSortedFilter(){

        //initializing the Predicate and Comparator
        Predicate<String> con = s-> s.length()<6;
        Comparator<String> com = Comparator.naturalOrder();


        List<String> fruits = List.of("Apple","Apple","Banana", "Banana", "Mango","Avocado", "Peach", "Pineapple", "Mango");

        List<String> result = ListUtil.distinctSortedFilter(fruits,con,com);

        assertNotNull(result);
        assertEquals(List.of("Apple","Mango","Peach"),result);

        //empty list
        List<String> empty = List.of();
        List<String> result1 = ListUtil.distinctSortedFilter(empty,con,com);

        assertNotNull(result1);
        assertTrue(result1.isEmpty());

        //null comparator
        List<String> nul = ListUtil.distinctSortedFilter(fruits,con,null);
        assertNotNull(nul);
        assertTrue(nul.isEmpty());
    }

}
