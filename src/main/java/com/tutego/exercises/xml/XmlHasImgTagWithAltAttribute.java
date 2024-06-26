package com.tutego.exercises.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class XmlHasImgTagWithAltAttribute {
  public static void main( String[] args ) throws URISyntaxException {
    String filename = "index.xhtml";
    Path source = Path.of( XmlHasImgTagWithAltAttribute.class.getResource( filename ).toURI() );
    reportMissingAltElements( source );
  }

  //tag::solution[]
  private static boolean containsAltTag( XMLStreamReader parser ) {
    return IntStream.range( 0, parser.getAttributeCount() )
                    .mapToObj( parser::getAttributeLocalName )
                    .anyMatch( s -> "alt".equalsIgnoreCase( s ) );
  }

  private static void reportMissingAltElements( Path path ) {
    try ( InputStream is = Files.newInputStream( path ) ) {
      for ( XMLStreamReader parser = XMLInputFactory.newInstance().createXMLStreamReader( is );
            parser.hasNext(); ) {
        parser.next();
        boolean isStartElement = parser.getEventType() == XMLStreamConstants.START_ELEMENT;
        if ( isStartElement ) {
          boolean isImgTag = "img".equalsIgnoreCase( parser.getLocalName() );
          if ( isImgTag && ! containsAltTag( parser ) )
            System.err.printf( "img does not contain alt attribute:%n%s%n", parser.getLocation() );
        }
      }
    }
    catch ( IOException | XMLStreamException e ) {
      e.printStackTrace();
    }
  }
  //end::solution[]
}
