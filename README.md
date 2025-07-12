# Interpreter for Lox Language (Java)

This project is an interpreter for the [Lox programming language](http://craftinginterpreters.com/), implemented in Java. The Lox language is a dynamically-typed, object-oriented language designed for learning about interpreters. This implementation closely follows the structure and concepts described in the book “Crafting Interpreters” by Bob Nystrom.

## Features

- **Lexical Analysis**: Converts source code into tokens.
- **Parsing**: Transforms tokens into an abstract syntax tree (AST).
- **Evaluation**: Interprets the AST and executes the code.
- **Error Handling**: Provides meaningful syntax and runtime error messages.
- **Support for Variables, Functions, Classes**: Implements core features of the Lox language.
- **Interactive REPL and Script Mode**: Run Lox code interactively or from source files.

## Getting Started

### Prerequisites

- Java 8 or above
- Maven or Gradle (for building)

### Building

Clone the repository and build the project:

```bash
git clone https://github.com/yourusername/interpreter-lox-java.git
cd interpreter-lox-java
mvn package
```

Or, if using Gradle:

```bash
gradle build
```

### Running

#### REPL (Interactive Mode)

```bash
java -jar target/interpreter-lox-java.jar
```

#### Script Mode

```bash
java -jar target/interpreter-lox-java.jar path/to/script.lox
```

## Example

Here’s a simple example of Lox code:

```lox
print "Hello, World!";
var a = 5;
var b = 10;
print a + b;
```

## Project Structure

- `src/main/java/com/lox/` – Core source files:
  - `Lox.java`: Entry point
  - `Scanner.java`: Tokenizer
  - `Parser.java`: AST generator
  - `Interpreter.java`: AST evaluator
  - `Expr.java`, `Stmt.java`: AST node definitions
  - `Token.java`: Token representation
- `src/test/java/com/lox/` – Unit tests

## Contributing

Contributions are welcome! Please open issues or submit pull requests.

## References

- [Crafting Interpreters Book](http://craftinginterpreters.com/)
- [Lox Language Specification](http://craftinginterpreters.com/contents.html)

## License

This project is licensed under the MIT License.
