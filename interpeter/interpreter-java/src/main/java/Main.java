import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, String> keywords = new HashMap<>();
static {
  keywords.put("and", "AND");
  keywords.put("class", "CLASS");
  keywords.put("else", "ELSE");
  keywords.put("false", "FALSE");
  keywords.put("for", "FOR");
  keywords.put("fun", "FUN");
  keywords.put("if", "IF");
  keywords.put("nil", "NIL");
  keywords.put("or", "OR");
  keywords.put("print", "PRINT");
  keywords.put("return", "RETURN");
  keywords.put("super", "SUPER");
  keywords.put("this", "THIS");
  keywords.put("true", "TRUE");
  keywords.put("var", "VAR");
  keywords.put("while", "WHILE");
}

  public static void main(String[] args) {
    boolean hadError = false;

    if (args.length < 2) {
      System.err.println("Usage: ./your_program.sh tokenize <filename>");
      System.exit(1);
    }

    String command = args[0];
    String filename = args[1];

    if (!command.equals("tokenize")) {
      System.err.println("Unknown command: " + command);
      System.exit(1);
    }

    String fileContents = "";
    try {
      fileContents = Files.readString(Path.of(filename));
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
      System.exit(1);
    }

    int line = 1;

    for (int idx = 0; idx < fileContents.length(); idx++) {
      char c = fileContents.charAt(idx);

      // Track line numbers
      if (c == '\n') {
        line++;
        continue;
      }

      // Skip whitespace
      if (c == ' ' || c == '\r' || c == '\t') {
        continue;
      }

      // Handle comments
      if (c == '/' && idx + 1 < fileContents.length() && fileContents.charAt(idx + 1) == '/') {
        while (idx < fileContents.length() && fileContents.charAt(idx) != '\n') {
          idx++;
        }
        idx--; // allow newline to be processed
        continue;
      }

      // Handle string literals
      if (c == '"') {
        int start = idx;
        idx++; // skip opening quote
        StringBuilder literal = new StringBuilder();
        boolean terminated = false;

        while (idx < fileContents.length()) {
          char ch = fileContents.charAt(idx);
          if (ch == '"') {
            terminated = true;
            break;
          } else if (ch == '\n') {
            line++;
          }
          literal.append(ch);
          idx++;
        }

        if (!terminated) {
          System.err.printf("[line %d] Error: Unterminated string.\n", line);
          hadError = true;
          break; // stop scanning
        }

        idx++; // skip closing quote

        // Output: STRING "lexeme" literal
        String lexeme = fileContents.substring(start, idx);
        System.out.printf("STRING %s %s\n", lexeme, literal.toString());
        idx--; // loop will increment idx again
        continue;
      }

      // Handle known tokens
      if (c == '/') {
        System.out.println("SLASH / null");
      } else if (c == '!') {
        if (idx + 1 < fileContents.length() && fileContents.charAt(idx + 1) == '=') {
          System.out.println("BANG_EQUAL != null");
          idx++;
        } else {
          System.out.println("BANG ! null");
        }
      } else if (c == '=') {
        if (idx + 1 < fileContents.length() && fileContents.charAt(idx + 1) == '=') {
          System.out.println("EQUAL_EQUAL == null");
          idx++;
        } else {
          System.out.println("EQUAL = null");
        }
      } else if (c == '<') {
        if (idx + 1 < fileContents.length() && fileContents.charAt(idx + 1) == '=') {
          System.out.println("LESS_EQUAL <= null");
          idx++;
        } else {
          System.out.println("LESS < null");
        }
      } else if (c == '>') {
        if (idx + 1 < fileContents.length() && fileContents.charAt(idx + 1) == '=') {
          System.out.println("GREATER_EQUAL >= null");
          idx++;
        } else {
          System.out.println("GREATER > null");
        }
      } else if (c == '(') {
        System.out.println("LEFT_PAREN ( null");
      } else if (c == ')') {
        System.out.println("RIGHT_PAREN ) null");
      } else if (c == '{') {
        System.out.println("LEFT_BRACE { null");
      } else if (c == '}') {
        System.out.println("RIGHT_BRACE } null");
      } else if (c == ',') {
        System.out.println("COMMA , null");
      } else if (c == '.') {
        System.out.println("DOT . null");
      } else if (c == ';') {
        System.out.println("SEMICOLON ; null");
      } else if (c == '*') {
        System.out.println("STAR * null");
      } else if (c == '+') {
        System.out.println("PLUS + null");
      } else if (c == '-') {
        System.out.println("MINUS - null");
      }
            // Handle number literals
      else if (Character.isDigit(c)) {
        int start = idx;

        // Consume the whole number (integer part)
        while (idx < fileContents.length() && Character.isDigit(fileContents.charAt(idx))) {
          idx++;
        }

        // Handle decimal part if present
        if (idx < fileContents.length() && fileContents.charAt(idx) == '.') {
          if (idx + 1 < fileContents.length() && Character.isDigit(fileContents.charAt(idx + 1))) {
            idx++; // consume '.'
            while (idx < fileContents.length() && Character.isDigit(fileContents.charAt(idx))) {
              idx++;
            }
          }
        }

        // Extract lexeme and literal
        String lexeme = fileContents.substring(start, idx);
        double value = Double.parseDouble(lexeme);
        System.out.printf("NUMBER %s %s\n", lexeme, value);
        idx--; // adjust for next loop increment
        continue;
      }
      
      
      else if (Character.isLetter(c) || c == '_') {
        int start = idx;
        while (idx < fileContents.length()) {
          char ch = fileContents.charAt(idx);
          if (Character.isLetterOrDigit(ch) || ch == '_') {
            idx++;
          } else {
            break;
          }
        }

        String lexeme = fileContents.substring(start, idx);
        String tokenType = keywords.getOrDefault(lexeme, "IDENTIFIER");

        System.out.printf("%s %s null\n", tokenType, lexeme);
        idx--; // account for loop increment
        continue;
      }


      else {
        // Unrecognized character = error
        System.err.printf("[line %d] Error: Unexpected character: %c\n", line, c);
        hadError = true;
      }
    }

    System.out.println("EOF  null");

    if (hadError) {
      System.exit(65);
    } else {
      System.exit(0);
    }
  }
}