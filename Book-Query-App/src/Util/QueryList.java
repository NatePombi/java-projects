package Util;

import Model.Book;

import java.util.ArrayList;
import java.util.List;


public class QueryList<S extends Book & QueryItems> extends ArrayList<S> {

    public QueryList(List<S> item){
        super(item);
    }

    public static <S extends Book> List<S> getMatches(List<S> items ,String field, String value){

        List<S> mat = new ArrayList<>();
        for(var x : items){
            if(x.matchFieldValue(field,value)){
                mat.add(x);
            }
        }

        return mat;
    }

    public List<S> getMatches(String field, String value){

        List<S> mat = new ArrayList<>();
        for(var x : this){
            if(x.matchFieldValue(field,value)){
                mat.add(x);
            }
        }

        return mat;
    }
}
