package lexer

import (
	"jparse/token"
	"unicode"
	"unicode/utf8"
)

// space delimited position in the row
//
//	ex. //ASDFGHJK EXEC PGM=ACOOLPGM inline comment
//		^ ^        ^    ^            ^
//
// ident  name     op   param        comment fields
type Field uint8

const (
	IdentifierField = iota
	NameField
	OperationField
	ParameterField
	CommentField
)

// Lexer takes a string input and converts it to an abstract syntax tree
type Lexer struct {
	input        string
	position     int   // current position in input (points to char)
	readPosition int   // current reading position in input (points to char + 1)
	char         rune  // current char, only characters one byte wide are valid
	field        Field // current 'column' in the statement
}

// New returns a ptr to a newly created Lexer instance
func New(input string) *Lexer {
	l := &Lexer{
		input: input,
	}
	l.readChar()
	return l
}

// loads the next character under inspection into char,
// then increments the position and readPosition
func (l *Lexer) readChar() {
	// moving the input up so that the deding target
	// is in the first position
	runeChar, runeSize := l.getNextChar()

	l.char = runeChar
	l.position = l.readPosition
	l.readPosition += runeSize // rune may be more than 1 byte wide
}

// looks ahead for the next char but does NOT increment position
// returns found char
func (l *Lexer) peekChar() rune {
	char, _ := l.getNextChar()
	return char
}

// __internal__
// reads next char and returns (rune instance, size of rune)
func (l *Lexer) getNextChar() (rune, int) {
	// moving the input up so that the deding target
	// is in the first position
	inpSlice := l.input[l.readPosition:]
	runeChar, runeSize := utf8.DecodeRune([]byte(inpSlice))
	if len(inpSlice) <= 0 {
		runeChar = 0 // end of file
	}

	return runeChar, runeSize
}

// returns the next token being read
func (l *Lexer) NextToken() token.Token {
	var tok token.Token
	l.swallowWhitespace()

	switch l.char {
	case '=':
		tok = newToken(token.Bind, l.char)
	case '+':
		tok = newToken(token.Plus, l.char)
	case '(':
		tok = newToken(token.LParen, l.char)
	case ')':
		tok = newToken(token.RParen, l.char)
	case ',':
		tok = newToken(token.Comma, l.char)
	case 0: // end of field
		tok = newToken(token.Eof, l.char)
	default: // identifiers or literals
		if unicode.IsDigit(l.char) { // number
			tok.Kind = token.Integer
			tok.Literal = l.readNumber()
			return tok
		} else if unicode.IsLetter(l.char) { // identifier
			tok.Literal = l.readIdentifier()
			tok.Kind = token.LookupIdentifier(tok.Literal)
			return tok
		}
	}

	l.readChar()
	return tok
}

// skips over whitespace tokens and increments the field counter
func (l *Lexer) swallowWhitespace() {
	if unicode.IsSpace(l.char) {
		l.field++
		l.readChar()
		for unicode.IsSpace(l.char) {
			l.readChar()
		}
	}
}

// creates a new Token object
func newToken(tokenKind token.TokenKind, ch rune) token.Token {
	str := ""
	if ch != 0 {
		str = string(ch)
	}
	return token.Token{
		Kind:    tokenKind,
		Literal: str,
	}
}

// returns the slice of digits from l.position to end
func (l *Lexer) readNumber() string {
	position := l.position

	for unicode.IsNumber(l.char) {
		l.readChar()
	}

	return l.input[position:l.position]
}

// returns the slice of characters from l.position to end
func (l *Lexer) readIdentifier() string {
	position := l.position

	for unicode.IsLetter(l.char) {
		l.readChar()
	}

	return l.input[position:l.position]
}
