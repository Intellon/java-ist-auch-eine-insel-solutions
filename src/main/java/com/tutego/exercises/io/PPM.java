package com.tutego.exercises.io;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;

//tag::solution[]
class PPM {

  public interface RgbToGray {
    int toGray( int r, int g, int b );
  }

  private static final String MAGIC_NUMBER = "P3";
  private static final char[] ASCII_FOR_SHADE_OF_GRAY =
      "@MBENRWDFQASUbehGmLOYkqgnsozCuJcry1v7lit{}?j|()=~!-/<>\"^_';,:`. ".toCharArray();

  private PPM() { }

  private static String nextStringOrThrows( Scanner scanner, String msg ) {
    if ( ! scanner.hasNext() )
      throw new IllegalStateException( msg );
    return scanner.next();
  }

  private static int nextIntOrThrow( Scanner scanner, String msg ) {
    if ( ! scanner.hasNextInt() )
      throw new IllegalStateException( msg );
    return scanner.nextInt();
  }

  public static void renderP3PpmImage( Readable input, Appendable output ) throws IOException {
    // black = 00, white = 255
    final int CHARS_PER_RGB = 256 / ASCII_FOR_SHADE_OF_GRAY.length;
    IntUnaryOperator grayToAscii = gray -> ASCII_FOR_SHADE_OF_GRAY[ gray / CHARS_PER_RGB ];
    renderP3PpmImage( input, ( r, g, b) -> (r + g + b) / 3, grayToAscii, output );
  }

  public static void renderP3PpmImage( Readable input, RgbToGray rgbToGray,
                                       IntUnaryOperator grayToAscii, Appendable output )
      throws IOException {

    Scanner scanner = new Scanner( input );

    // Header P3
    String magicNumber = nextStringOrThrows( scanner, "End of file, missing header" );
    if ( ! magicNumber.equals( MAGIC_NUMBER ) )
      throw new IllegalStateException( "No P3 image file, but " + magicNumber );

    // Width Height
    int width  = nextIntOrThrow( scanner, "End of file or wrong format for width" );
    int height = nextIntOrThrow( scanner, "End of file or wrong format for height" );

    // Max color value
    int maxVal = nextIntOrThrow( scanner, "End of file or wrong format for max value" );
    if ( maxVal != 255 )
      throw new IllegalStateException(
          "Only the maximum color value 255 is allowed but was " + maxVal );

    // Matrix
    for ( int y = 0; y < height; y++ ) {
      for ( int x = 0; x < width; x++ ) {
        int r = nextIntOrThrow( scanner, "End of file or wrong format for red value" );
        int g = nextIntOrThrow( scanner, "End of file or wrong format for green value" );
        int b = nextIntOrThrow( scanner, "End of file or wrong format for blue value" );
        int gray = rgbToGray.toGray( r, g, b );
        output.append( (char) grayToAscii.applyAsInt( gray ) );
      }
      output.append( '\n' );
    }
  }
}
//end::solution[]

class PPMDemo {
  public static void main( String[] args ) throws IOException {

    String s = "" +
        "P3\n" +
        "3 2\n" +
        "255\n" +
        "255   0   0\n" +
        "  0 255   0\n" +
        "  0   0 255\n" +
        "255 255   0\n" +
        "255 255 255\n" +
        "  0   0   0";
    Readable stringReader = new StringReader( s );
    //    Readable stringReader = new FileReader( new File( "C:\\Users\\Christian\\Desktop\\for_print_300dpi logo only.ppm" ) );
    Appendable appendable = new StringBuilder();
    // The luminosity method is a more sophisticated version of the average method. It also averages the values, but it forms a weighted average to account for human perception. We’re more sensitive to green than other colors, so green is weighted most heavily. The formula for luminosity is 0.21 R + 0.72 G + 0.07 B.
    PPM.renderP3PpmImage( stringReader, appendable );
    System.out.println( appendable );
  }
}