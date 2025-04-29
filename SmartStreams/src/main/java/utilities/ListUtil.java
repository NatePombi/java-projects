package main.java.utilities;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtil {



    public static List<String> filterStartsWith(List<String> list, char letter){

        if(list == null || list.isEmpty()){
            return List.of();
        }

        char charact = Character.toLowerCase(letter);


        return list.stream()
                .filter(s -> !s.isEmpty() && Character.toLowerCase(s.charAt(0)) == charact)
                .collect(Collectors.toList());
    }

    public static <T> List<T> flattenList(List<List<T>> list){
        if(list == null || list.isEmpty()){
            return List.of();
        }

        return list.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static <T> Map<Boolean,List<T>> partitionByCondition(List<T> list, Predicate<T> condition){

        if(list== null || list.isEmpty() || condition==null){
            return Map.of();
        }

        return list.stream()
                .collect(Collectors.partitioningBy(condition));
    }

    public static <T> T findMax (List<T> list, Comparator<T> comparator){

        if(list == null || list.isEmpty() || comparator==null){
            throw new NoSuchElementException();
        }

        return list.stream()
                .max(comparator)
                .orElse(null);
    }

    public static <T> T findMin(List<T> list, Comparator<T> comparator){

        if(list == null || list.isEmpty() || comparator==null){
            throw new NoSuchElementException();
        }

        return list.stream()
                .min(comparator)
                .orElse(null);
    }

    public static <T> List<T> filterAndCollect(List<T> list, Predicate<T> condition){

        if(list == null || list.isEmpty() || condition ==null){
            return List.of();
        }

        return list.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public static <T> List<T> distinctSortedFilter(List<T> list, Predicate<T> condition, Comparator<T> comparator){

        if(list == null || list.isEmpty() || condition ==null || comparator == null){
            return List.of();
        }

        return list.stream()
                .filter(condition)
                .distinct()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public static <T,R> List<R> transformAndCollect(List<T> list, Predicate<T> filterCondition, Function<T,R> mapper, Comparator<R> comparator){
        if(list == null || list.isEmpty() || filterCondition == null || mapper == null || comparator ==null){
            return List.of();
        }

        return list.stream()
                .filter(filterCondition)
                .distinct()
                .map(mapper)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
