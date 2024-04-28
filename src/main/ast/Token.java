package main.ast;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Token {

    public TokenKind kind;
    public String literal;

    public Token(TokenKind kind, String literal) {
        this.kind = kind;
        this.literal = literal;
    }

    private static final HashMap<String, TokenKind> Keywords = (HashMap<String, TokenKind>) Stream.of(new Object[][]{
                    {"SET",TokenKind.Set},
                    {"PROC",TokenKind.Proc},
                    {"EXEC",TokenKind.Exec},
                    {"DD",TokenKind.Dd},
            })
            .collect(Collectors.toMap(data -> (String) data[0], data -> (TokenKind)data[1]));

    public static Optional<TokenKind> lookupIdentifier(String identifier) {
        return Optional.ofNullable(Keywords.get(identifier));
    }

    @Override public String toString() {
        return this.kind.name() + ": " + this.literal;
    }
}




