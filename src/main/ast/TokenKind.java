package main.ast;

public enum TokenKind {
    // critical control tokens
    Illegal, // signifies an unidentifiable token
    Eof,     // end of file or eof symbol //\n

    // identifiers and literals
    Identifier, // variable names
    Integer,    // int type
    String,     // string type
    Tag,       // step name
    Parameter,

    // operators
    Bind, // assignment operator
    Plus, // addition and concatenation

    // delimiters
    Comma, // delimiting options

    // precedence tokens
    LParen,
    RParen,

    // line type designators -> [//, //*, /*]
    Slash,
    Aster,

    // keywords
    Set,
    Proc,
    Exec,
    Dd,

    // Miscellaneous items
    Comment,
    Executable,
    SysinMarker,
}
