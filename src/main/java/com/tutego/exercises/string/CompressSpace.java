package com.tutego.exercises.string;

public class CompressSpace {

  //tag::solution[]
  public static final String TWO_SPACES = "  ";

  static String compressSpace( String string ) {
    return compressSpace( new StringBuilder( string ) ).toString();
  }

  static StringBuilder compressSpace( StringBuilder string ) {
    int index = string.lastIndexOf( TWO_SPACES );

    while ( index >= 0 ) {
      string.deleteCharAt( index );
      index = string.lastIndexOf( TWO_SPACES );
    }
    return string;
  }
  //end::solution[]

  public static void main( String[] args ) {
    String s = compressSpace( "Hallo.   Das  ist ein  Beispiel." );
    System.out.println( s );
  }
}