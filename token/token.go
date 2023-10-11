package token

// token identifier
type TokenKind string

type Token struct {
	Kind    TokenKind // token identifier
	Literal string    // data associated with the token
}

const (
	// critical control tokens
	Illegal = "Illegal" // signifies an unidentifiable token
	Eof     = "Eof"     // end of file or eof symbol //\n

	// identifiers and literals
	Identifier = "Identifier" // variable names
	Integer    = "Integer"    // int type
	String     = "String"     // string type

	// operators
	Bind = "=" // assignment operator
	Plus = "+" // addition and concatenation

	// delimiters
	Comma = "," // delimiting options

	// precedence tokens
	LParen = "("
	RParen = ")"

	// keywords
	Set  = "Set"
	Proc = "Proc"
	Exec = "Exec"
	Dd   = "Dd"
)
