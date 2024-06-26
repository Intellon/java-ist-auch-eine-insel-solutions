package com.tutego.exercises.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeroCombinedComparators {

  public static void main( String[] args ) {
    //tag::solution[]
    Comparator<Heroes.Hero> nameComparator =
        (h1, h2) -> h1.name.compareTo( h2.name );

    Comparator<Heroes.Hero> yearComparator =
        (h1, h2) -> Integer.compare( h1.yearFirstAppearance, h2.yearFirstAppearance );

    Comparator<Heroes.Hero> combinedComparator = yearComparator.thenComparing( nameComparator );

    List<Heroes.Hero> allHeros = new ArrayList<>( Heroes.ALL );
    allHeros.sort( combinedComparator );
    System.out.println( allHeros );
    //end::solution[]
  }
}
