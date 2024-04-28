package main.ast;

import java.util.ArrayList;
import java.util.Arrays;


public class Lexer {
    private final String input;
    private int pos;
    private int readPos;
    private char currentChar;
    private char previousChar;
    private Field field;
    private State state;
    private int lineNumber;
    
    public Lexer(String input) {
        this.input = input;
        this.readChar();
    }

    /**
     * reads the next {@code char} in the {@code input} and increments the position in the pass.
     */
    private void readChar() {
        this.currentChar = this.getNextChar();
        this.pos = readPos;
        this.readPos++;
    }

    private char getNextChar() {
        if (this.readPos >= this.input.length()) {
            return 0;
        }

        return this.input.charAt(this.readPos);
    }

    /**
     * reads the next {@code char} in the {@code input} but does not increment the position in the pass.
     * @return {@code char} pointed to by {@code this.currentChar}
     */
    private char peekChar() {
        return this.getNextChar();
    }

    private void swallowWhitespace() {
        if (Character.isWhitespace(this.currentChar)) {
            this.field.next();
        }
        while (Character.isWhitespace(this.currentChar)) {
            this.readChar();
        }
    }

    private Token newToken(TokenKind kind, char ch) {
        String str = "";

        if (ch != 0) {
            str = str + ch;
        }

        return new Token(kind, str);
    }

    private String readNumber() {
        int pos = this.pos;

        while (Character.isDigit(this.currentChar)) {
            this.readChar();
        }

        return this.input.substring(pos, this.pos);
    }

    private String readIdentifier() {
        int pos = this.pos;

        while (Character.isLetterOrDigit(this.currentChar)) {
            this.readChar();
        }

        return this.input.substring(pos, this.pos);
    }

    public Token nextToken() {
        Token token = null;

        this.swallowWhitespace();

        switch (this.currentChar) {
            case '=':
                token = newToken(TokenKind.Bind, this.currentChar);
                break;
            case '+':
                token = newToken(TokenKind.Plus, this.currentChar);
                break;
            case '(':
                token = newToken(TokenKind.LParen, this.currentChar);
                break;
            case ')':
                token = newToken(TokenKind.RParen, this.currentChar);
                break;
            case ',':
                token = newToken(TokenKind.Comma, this.currentChar);
                break;
            case '/':
                if (this.peekChar() == '/') { // start of a new line
                    this.readChar();

                    // could be a comment line
                    if (this.peekChar() == '*') {
                        token = new Token(TokenKind.Comment, "/*");
                    } else {
                        token = new Token(TokenKind.Executable, "//");
                    }
                } else if (this.peekChar() == '*') { // demarkation of sysin end
                    token = newToken(TokenKind.SysinMarker, this.currentChar);
                }
                break;
            case 0:
                token = newToken(TokenKind.Eof, this.currentChar);
                break;
            default: // identifiers or literals
                if (this.field == Field.Name){ // actually a step name 
                    token = new Token(TokenKind.Tag, this.readIdentifier()); 
                    return token;
                }

                if (Character.isDigit(this.currentChar)) {
                    token = new Token(TokenKind.Integer, this.readNumber());
                    return token;
                } else if (Character.isLetter(this.currentChar)) {
                    token = new Token(TokenKind.Identifier, this.readIdentifier());
                    return token;
                }
                token = newToken(TokenKind.Plus, this.currentChar);
                break;
        }

        this.readChar();
        return token;
    }


    public ArrayList<Token> tokenize(){ 
        
        // JCL is a 'unique' (read completely shit) language that depends on rows/columns,
        // so this is completely fucky-wucky with no solution but to commit hara-kiri
        for (String line : this.input.split("\n")) {
        }

        
        
        return null;
    }
}


