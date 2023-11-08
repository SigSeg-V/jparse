package lexer

import (
	"jparse/token"
	"testing"
)

type lexerTestCase struct {
	input          string
	expectedTokens []token.Token
}

func TestNextToken(t *testing.T) {
	tests := []lexerTestCase{
		{
			input: `=+(),`,
			expectedTokens: []token.Token{
				{Kind: token.Bind, Literal: "="},
				{Kind: token.Plus, Literal: "+"},
				{Kind: token.LParen, Literal: "("},
				{Kind: token.RParen, Literal: ")"},
				{Kind: token.Comma, Literal: ","},
			},
		},
	}

	runTest(t, tests)
}

// runner for the test asserts
func runTest(t *testing.T, tests []lexerTestCase) {
	t.Helper()

	// run each test consecutively
	for _, test := range tests {
		l := New(test.input)
		for idx, expected := range test.expectedTokens {
			tok := l.NextToken()

			// asserting the token kind is the same
			if tok.Kind != expected.Kind {
				t.Fatalf("tests[%d]: token `Kind` incorrect, expected %v, got %v",
					idx, expected.Kind, tok.Kind)
			}

			// asserting the literal is the same
			if tok.Literal != expected.Literal {
				t.Fatalf("tests[%d]: token `Literal` incorrect, expected %v, got %v",
					idx, expected.Literal, tok.Literal)
			}
		}
	}
}
