//------------------------------------------------------------------------------
// The com. package
// Philip R Brenan at appaapps dot com, Appa Apps Ltd Inc., 2022
//------------------------------------------------------------------------------
package com;

public class Ashley
 {public static void main(String[] args)
   {say("Hello World from class Ashley");
   }
  public static void say(Object...O)
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) {b.append(" "); b.append(o);}
    System.err.print(b.toString().substring(1)+"\n");
   }
 }
