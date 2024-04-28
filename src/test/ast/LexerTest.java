package test.ast;

import org.junit.*;

import org.junit.Assert;
import main.ast.Token;
import main.ast.TokenKind;
import main.ast.Lexer;

import java.util.ArrayList;
import java.util.List;


class LexerTestCase {
    private String input;
    private List<Token> expectedTokens;

    public LexerTestCase(String input, List<Token> expectedTokens) {
        this.input = input;
        this.expectedTokens = expectedTokens;
    }

    public String getInput() {
        return input;
    }

    public List<Token> getExpectedTokens() {
        return expectedTokens;
    }
}

public class LexerTest {
    
    @Test
    public void testNextToken() {
        List<LexerTestCase> tests = new ArrayList<>();
        tests.add(new LexerTestCase(
            "=+(),",
            new ArrayList<Token>() {{
                add(new Token(TokenKind.Bind, "="));
                add(new Token(TokenKind.Plus, "+"));
                add(new Token(TokenKind.LParen, "("));
                add(new Token(TokenKind.RParen, ")"));
                add(new Token(TokenKind.Comma, ","));
            }}
        ));
        tests.add(new LexerTestCase(
            "//STEP010  EXEC PGM=SORT\n" +
            "//SYSOUT   DD SYSOUT=*\n" +
            "//SORTIN   DD DSN=userid.IBMMF.INPUT,DISP=SHR\n" +
            "//SORTOUT  DD DSN=userid.IBMMF.OUTPUT,DISP=SHR\n" +
            "//SYSIN    DD *\n" +
            "  SORT FIELDS=(1,15,ZD,A)\n" +
            "  SUM FIELDS=NONE\n" +
            "/*",
            new ArrayList<Token>() {{
                add(new Token(TokenKind.Executable, "//"));
                add(new Token(TokenKind.Tag, "STEP010"));
                add(new Token(TokenKind.Exec, "EXEC"));
                add(new Token(TokenKind.Parameter, "PGM=SORT"));
                add(new Token(TokenKind.Executable, "//"));
                add(new Token(TokenKind.Tag, "SYSOUT"));
                add(new Token(TokenKind.Dd, "DD"));
                add(new Token(TokenKind.Parameter, "SYSOUT=*"));
                add(new Token(TokenKind.Executable, "//"));
                add(new Token(TokenKind.Tag, "SORTIN"));
                add(new Token(TokenKind.Dd, "DD"));
                add(new Token(TokenKind.Parameter, "DSN=userid.IBMMF.INPUT"));
                add(new Token(TokenKind.Parameter, "DISP=SHR"));
                add(new Token(TokenKind.Executable, "//"));
                add(new Token(TokenKind.Tag, "SORTOUT"));
                add(new Token(TokenKind.Dd, "DD"));
                add(new Token(TokenKind.Parameter, "DSN=userid.IBMMF.OUTPUT"));
                add(new Token(TokenKind.Parameter, "DISP=SHR"));
                add(new Token(TokenKind.Executable, "//"));
                add(new Token(TokenKind.Tag, "SYSIN"));
                add(new Token(TokenKind.Dd, "DD"));
                add(new Token(TokenKind.Aster, "*"));
                add(new Token(TokenKind.SysinMarker, "/*"));
            }}
        ));

        runTest(tests);
    }

    private static void runTest(List<LexerTestCase> tests) {
        // run each test consecutively
        for (LexerTestCase test : tests) {
            Lexer l = new Lexer(test.getInput());
            for (int idx = 0; idx < test.getExpectedTokens().size(); idx++) {
                Token tok = l.nextToken();

                // asserting the token kind is the same
                Assert.assertEquals(String.format("tests[%d]: token `Kind` incorrect, expected %s, got %s",
                                idx, test.getExpectedTokens().get(idx).kind, tok.kind),
                                
                                test.getExpectedTokens().get(idx).kind, 
                                tok.kind
                        );

                // asserting the literal is the same
                Assert.assertEquals(String.format("tests[%d]: token `Literal` incorrect, expected %s, got %s",
                                idx, test.getExpectedTokens().get(idx).literal, tok.literal),
                                
                                test.getExpectedTokens().get(idx).literal,
                                 tok.literal
                        );
            }
        }
    }
}

