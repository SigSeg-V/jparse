package main.repl;

import main.ast.Token;
import main.ast.TokenKind;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Token token = new Token(TokenKind.Executable, "IEFJKT12");
        System.out.println(token);
    }
}