//------------------------------------------------------------------------------
// The com.Ashley package: creating new code in our own image
//------------------------------------------------------------------------------
package com;

public class Ashley
 {public static void say(Object...O)                                            // Say words with spaces between them
   {int i = 0;
    final Chars c = new Chars();
    for(Object o: O)
     {if (i++ > 0) c.append(' ');
      c.append(o.toString());
     }
    c.err();
   }

  public static void stop(Object...O)                                           // Say something then stop with a trace back
   {say(O);
    new Exception().printStackTrace();
    System.exit(0);
   }

  static class Chars                                                            // Array of characters acting like a string but capable of storing unicode as utf32
   {int  used  = 0;
    int[]chars = new int[1];

    public int length()                                                         // Length of string
     {return used;
     }

    private void checkSpace(int N)                                              // Check we have enough space in the string to add more data
     {final int want = used + N;
      if (want >= chars.length)
       {for (int i = 0; i < 31; i++)
         {if ((1 << i) > want)
           {final int[]n = new int[1 << i];
            for (int j = 0; j < used; j++) n[j] = chars[j];
            chars = n;
            return;
           }
         }
       }
     }

    public void append(char c)                                                  // Append a character to a string
     {checkSpace(1);
      chars[used++] = c;
     }

    public void append(String c)                                                // Append a string to a string
     {final int N = c.length();
      checkSpace(N);
      for (int i = 0; i < N; i++) chars[used++] = c.charAt(i);
     }

    public  void append(int[]c)                                                 // Append array of characters
     {final int N = c.length;
      checkSpace(N);
      for (int i = 0; i < N; i++) chars[used++] = chars[i];
     }

    public  void append(Chars c)                                                // Append characters
     {final int N = c.length();
      checkSpace(N);
      for (int i = 0; i < N; i++) chars[used++] = c.chars[i];
     }

    public  void append(int decimal, int width)                                 // Append a decimal number right formatted in a field of specified width
     {for (int i = 0; i <  width; i++) append(' ');
      for (int i = 1; i <= width; i++)
       {final int n = decimal % 10;
        chars[used-i] = '0' + n;
        decimal -= n; decimal /= 10;
       }
      for (int i = width; i > 0; i--)                                           // Blank out leading zeroes
       {if (chars[used-i] != '0') return;
        chars[used-i] = ' ';
       }
     }

    public boolean equals(int[]c)                                               // Check  equal to a character array
     {if (c.length != length()) return false;
      for (int i = 0; i < used; i++) if (c[i] != chars[i]) return false;
      return true;
     }

    public boolean equals(Chars c)                                              // Check equals to another Chars
     {if (c.length() != length()) return false;
      for (int i = 0; i < used; i++) if (c.chars[i] != chars[i]) return false;
      return true;
     }

    public  void err()                                                          // Print as characters
     {for (int i = 0; i < used; i++) System.err.print((char)chars[i]);
      System.err.println("");
     }

    public  void out()                                                          // Print as characters
     {for (int i = 0; i < used; i++) System.out.print((char)chars[i]);
      System.out.println("");
     }
   }

  public static Chars Chars()                                                   // Construct a string made of array of characters
   {return new Chars();
   }

  public static Chars Chars(String s)                                           // Construct a string made of array of characters from a string
   {final Chars c = new Chars();
    c.append(s);
    return c;
   }

  public static void main(String[] args)                                        // Tests
   {int tests = 0;
    if (true)
     {final Chars c = new Chars(), d = new Chars();
      c.append("Hello");
      c.append(' ');
      c.append("World");
      d.append(c);
      assert(c.equals(d));
      ++tests;
    }
    if (true)
     {final Chars c = new Chars();
      c.append("a");
      c.append(42, 4);
      c.append("b");
      assert(c.equals(Chars("a  42b")));
      ++tests;
    }
    say("Passed all", tests, "tests");
    stop("Stopped");
   }
 }
