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
		{
			input: `//STEP010  EXEC PGM=SORT
//SYSOUT   DD SYSOUT=*
//SORTIN   DD DSN=userid.IBMMF.INPUT,DISP=SHR
//SORTOUT  DD DSN=userid.IBMMF.OUTPUT,DISP=SHR
//SYSIN    DD *
  SORT FIELDS=(1,15,ZD,A)
  SUM FIELDS=NONE
/*`,
			expectedTokens: []token.Token{
				{Kind: token.Executable, Literal: "//"},
				{Kind: token.Tag, Literal: "STEP010"},
				{Kind: token.Exec, Literal: "EXEC"},
				{Kind: token.Parameter, Literal: "PGM=SORT"},
				{Kind: token.Executable, Literal: "//"},
				{Kind: token.Tag, Literal: "SYSOUT"},
				{Kind: token.Dd, Literal: "DD"},
				{Kind: token.Parameter, Literal: "SYSOUT=*"},
				{Kind: token.Executable, Literal: "//"},
				{Kind: token.Tag, Literal: "SORTIN"},
				{Kind: token.Dd, Literal: "DD"},
				{Kind: token.Parameter, Literal: "DSN=userid.IBMMF.INPUT"},
				{Kind: token.Parameter, Literal: "DISP=shr"},
				{Kind: token.Executable, Literal: "//"},
				{Kind: token.Tag, Literal: "SORTOUT"},
				{Kind: token.Dd, Literal: "DD"},
				{Kind: token.Parameter, Literal: "DSN=userid.IBMMF.OUTPUT"},
				{Kind: token.Parameter, Literal: "DISP=shr"},
				{Kind: token.Executable, Literal: "//"},
				{Kind: token.Tag, Literal: "SYSIN"},
				{Kind: token.Dd, Literal: "DD"},
				{Kind: token.Aster, Literal: "*"},
				{Kind: token.SysinMarker, Literal: "/*"},
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
