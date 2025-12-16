class Main {

  public static void main(String[] args) {
    (new Main()).init();
  }

  void print(Object o){ System.out.println(o); }
  void printt(Object o){ System.out.print(o); }

  void init(){

    // ENCODE 

    String file = Input.readFile("Original.txt").toLowerCase();

    String encoded1 = morseEncrypt(file);
    Input.writeFile("Encode1.txt", encoded1);

    String encoded2 = asciiEncode(encoded1);
    Input.writeFile("Encode2.txt", encoded2);

    String encoded3 = brailleEncode(encoded2);
    Input.writeFile("Encode3.txt", encoded3);

    //  DECODE 

    String file2 = Input.readFile("Encode3.txt");
    String decoded = morseDecrypt(
                        asciiDecode(
                          brailleDecode(file2)
                        )
                     );
    Input.writeFile("Decode.txt", decoded); 
    String decoded3 = brailleDecode(file2);
    Input.writeFile("Decode3.txt", decoded3);

    String decoded2 = asciiDecode(decoded3);
    Input.writeFile("Decode2.txt", decoded2);
  }

  // LEVEL 2 ASCII 
  String asciiEncode(String txt){
    StringBuilder build = new StringBuilder();
    for(int i = 0; i < txt.length(); i++){
      build.append((int)txt.charAt(i)).append(" ");
    }
    return build.toString();
  }

  String asciiDecode(String txt){
    StringBuilder build = new StringBuilder();
    String[] parts = txt.split(" ");
    for(String p : parts){
      if(!p.isEmpty())
        build.append((char)Integer.parseInt(p));
    }
    return build.toString();
  }

// LEVEL 3 BRAILLE 
String brailleEncode(String txt){
StringBuilder build = new StringBuilder();
for(int i = 0; i < txt.length(); i++){
char c = txt.charAt(i);
if(c == '\n'){
build.append('\n');
} else {
build.append((char)(0x2800 + c));
}
}
return build.toString();
}

String brailleDecode(String txt){
StringBuilder build = new StringBuilder();
for(int i = 0; i < txt.length(); i++){
char c = txt.charAt(i);
if(c == '\n'){
build.append('\n');
} else {
build.append((char)(c - 0x2800));
}
}
return build.toString();
}

  //  LEVEL 1 MORSE 
  String[] morse = {
    ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---",
    "-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-",
    "..-","...-",".--","-..-","-.--","--.."
  };

  String morseEncrypt(String txt){
    StringBuilder build = new StringBuilder();
    char ch;

    for(int x = 0; x < txt.length(); x++){
      ch = txt.charAt(x);

      if(ch >= 'a' && ch <= 'z'){
        build.append(morse[ch - 'a']).append(" ");
      }
      else if(ch == ' '){
        build.append("/ ");
      }
      else{
        build.append("# ");
      }
    }
    return build.toString();
  }

  String morseDecrypt(String txt){
    StringBuilder build = new StringBuilder();
    String current = "";

    for(int x = 0; x < txt.length(); x++){
      char ch = txt.charAt(x);

      if(ch != ' ')
        current += ch;
      else{
        build.append(decodeMorse(current));
        current = "";
      }
    }
    build.append(decodeMorse(current));

    return build.toString();
  }

  String decodeMorse(String current){
    if(current.equals("/")) return " ";
    if(current.equals("#")) return "#";

    for(int i = 0; i < morse.length; i++){
      if(morse[i].equals(current))
        return "" + (char)('a' + i);
    }
    return "";
  }
}