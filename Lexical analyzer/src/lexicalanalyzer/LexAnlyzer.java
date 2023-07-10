package lexicalanalyzer;


import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.io.InputStreamReader;


public class LexAnlyzer {

    static final int MAX_LEXEMES = 1000;
    // Declare an array of char for store lexemes and make it global.
    static char[] lexeme = new char[MAX_LEXEMES];
    // Declare an array of char which will store the current lexeme when an error occures, because the lexeme will change.
    static char[] lexemeError = new char[MAX_LEXEMES];
    // Declare a global variable to store the current index of the lexeme 
    static int indexOfLexeme = 0;
    // Declare a global varbile to store the currnt state fo the index when an error occures 
    static int indexOfErrorLexeme = 0;
    // errorFlag variable is set to true when an error occures
    static boolean errorFlag = false;
    // fixedFlag variable is set to true when to return the first characater 
    static boolean fixedFlag = false;
    // insertionFlag variable is set to true when we return the next character which will be \n to enter the correct state
    static boolean insertionFlag = false;
    static int indexOfError = 0;
    static boolean endFileFlag = false;
    static int lineCounter = 0;

    // reserved words array
    static final String[] reservedWords = {"abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const", "continue", "default",
        "do", "double", "else", "enum", "extends", "false", "final", "finally",
        "float", "for", "goto", "if", "implements", "import", "instanceof",
        "int", "interface", "long", "native", "new", "null", "package", "private",
        "protected", "public", "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws", "transient", "true",
        "try", "void", "volatile", "while"};

    public static void main(String[] args) throws IOException {
        // declare file to read from
        File file = new File("input11.txt");
        // buffer reader, reads a single character
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
        // read character from the file using getToken()
        char lookahead = getToken(reader);
        int state = 0;
        while (!endFileFlag) {
            if (lookahead == '\0') {
                endFileFlag = true;
            }
            switch (state) {
                case 0:
                    // reset the indexOfLexeme at the start of each 0 state
                    indexOfLexeme = 0;
                    // Whitespaces and tabs
                    if (lookahead == '\r' || lookahead == '\t' || lookahead == ' '
                            || lookahead == '\n' || lookahead == '\r') {
                        state = 0;
                        lookahead = getToken(reader);
                    } // underscore and Letter for Identifiers
                    else if (isLetter(lookahead) || lookahead == '_') {
                        state = 1;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Plus sign
                    else if (lookahead == '+') {
                        state = 3;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Minus sign
                    else if (lookahead == '-') {
                        state = 7;
                        lexeme[indexOfLexeme++] = lookahead;
                    } //  Multiplication sign
                    else if (lookahead == '*') {
                        state = 11;
                        lexeme[indexOfLexeme++] = lookahead;
                    } //  Divison sign
                    else if (lookahead == '/') {
                        state = 14;
                        lexeme[indexOfLexeme++] = lookahead;
                    } //  Modulus sign
                    else if (lookahead == '%') {
                        state = 23;
                        lexeme[indexOfLexeme++] = lookahead;
                    } //  Equal sign
                    else if (lookahead == '=') {
                        state = 26;
                        lexeme[indexOfLexeme++] = lookahead;
                    } //  Greater than sign
                    else if (lookahead == '>') {
                        state = 29;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Less than sign
                    else if (lookahead == '<') {
                        state = 32;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Not equal sign 
                    else if (lookahead == '!') {
                        state = 35;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // And sign
                    else if (lookahead == '&') {
                        state = 38;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Or sign
                    else if (lookahead == '|') {
                        state = 41;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Check if numbers
                    else if (isDigit(lookahead)) {
                        state = 44;
                        lexeme[indexOfLexeme++] = lookahead;
                    }// Flaoting point
                    else if (lookahead == '.') {
                        state = 48;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Double quotation mark
                    else if (lookahead == '"') {
                        state = 52;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Single Qoute 
                    else if (lookahead == '\'') {
                        state = 58;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Left square Bracket 
                    else if (lookahead == '[') {
                        state = 64;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Right square bracket 
                    else if (lookahead == ']') {
                        state = 65;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Left curly brackets 
                    else if (lookahead == '{') {
                        state = 66;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Right curly brackets 
                    else if (lookahead == '}') {
                        state = 67;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Left parentheses 
                    else if (lookahead == '(') {
                        state = 68;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Right parentheses 
                    else if (lookahead == ')') {
                        state = 69;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Semicolon 
                    else if (lookahead == ';') {
                        state = 70;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Colon 
                    else if (lookahead == ':') {
                        state = 71;
                        lexeme[indexOfLexeme++] = lookahead;
                    } // Comma 
                    else if (lookahead == ',') {
                        state = 72;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 73;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 1:
                    lookahead = getToken(reader);
                    if (isLetter(lookahead) || isDigit(lookahead) || lookahead == '_') {
                        state = 1;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 2;
                    }
                    break;
                case 2:
                    lexeme[indexOfLexeme] = '\0';
                    if (isKeyword(lexeme)) {
                        printToken(lexeme, "Reserved Word");
                    } else {
                        printToken(lexeme, "ID");
                    }
                    state = 0;
                    break;
                case 3:
                    lookahead = getToken(reader);
                    if (lookahead == '+') {
                        state = 4;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '=') {
                        state = 5;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 6;
                    }
                    break;
                case 4:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "INCREMENT");
                    state = 0;
                    break;
                case 5:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "ADD_ASSIGN");
                    state = 0;
                    break;
                case 6:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "ADDITION");
                    state = 0;
                    break;
                case 7:
                    lookahead = getToken(reader);
                    if (lookahead == '-') {
                        state = 8;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '=') {
                        state = 9;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 10;
                    }
                    break;
                case 8:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "DECREMENT");
                    state = 0;
                    break;
                case 9:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "MINUS_ASSIGN");
                    state = 0;
                    break;
                case 10:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "MINUS");
                    state = 0;
                    break;
                case 11:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 12;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 13;
                    }
                    break;
                case 12:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "MULT_ASSIGN");
                    state = 0;
                    break;
                case 13:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "MULTIPLICATION");
                    state = 0;
                    break;
                case 14:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 15;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '/') {
                        state = 16;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '*') {
                        state = 19;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 18;
                    }
                    break;
                case 15:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "DIVISION_ASSIGN");
                    state = 0;
                    break;
                case 16:
                    lookahead = getToken(reader);
                    if (lookahead == '\0' || lookahead == '\n' || Character.isISOControl(lookahead)) {
                        state = 17;
                    } else {
                        state = 16;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 17:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "SINGLE_COMMENT");
                    state = 0;
                    break;
                case 18:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "DIVISION");
                    state = 0;
                    break;
                case 19:
                    lookahead = getToken(reader);
                    if (lookahead == '*') {
                        state = 20;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '\0') {
                        // Error call
                        state = 22;
                    } else {
                        state = 19;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 20:
                    lookahead = getToken(reader);
                    if (lookahead == '/') {
                        state = 21;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '\0') {
                        // Error call
                        state = 22;
                    } else {
                        state = 19;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 21:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "MULTI_COMMENT");
                    state = 0;
                    break;
                case 22:
                    lexeme[indexOfLexeme] = '\0';
                    // Call this method for the program to handle the occured error
                    isError();
                    // Also get the next Token to go to state zero 
                    lookahead = getToken(reader);
                    state = 0;
                    break;
                case 23:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 24;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 25;
                    }
                    break;
                case 24:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "MODULUS_ASSIGN");
                    state = 0;
                    break;
                case 25:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "MODULUS");
                    state = 0;
                    break;
                case 26:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 27;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 28;
                    }
                    break;
                case 27:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LOGICAL_EQUAL");
                    state = 0;
                    break;
                case 28:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "ASSIGNMENT_OPERATOR");
                    state = 0;
                    break;
                case 29:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 30;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 31;
                    }
                    break;
                case 30:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "MORE_THAN_EQUAL");
                    state = 0;
                    break;
                case 31:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "MORE_THAN");
                    state = 0;
                    break;
                case 32:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 33;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 34;
                    }
                    break;
                case 33:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LESS_THAN_EQUAL");
                    state = 0;
                    break;
                case 34:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "LESS_THAN");
                    state = 0;
                    break;
                case 35:
                    lookahead = getToken(reader);
                    if (lookahead == '=') {
                        state = 36;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 37;
                    }
                    break;
                case 36:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "NOT_EQUAL");
                    state = 0;
                    break;
                case 37:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "NEGATE");
                    state = 0;
                    break;
                case 38:
                    lookahead = getToken(reader);
                    if (lookahead == '&') {
                        state = 39;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 40;
                    }
                    break;
                case 39:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LOGICAL_AND");
                    state = 0;
                    break;
                case 40:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "LOGICAL_AND_NON_SHORT_CIRCUIT");
                    state = 0;
                    break;
                case 41:
                    lookahead = getToken(reader);
                    if (lookahead == '|') {
                        state = 42;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 43;
                    }
                    break;
                case 42:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LOGICAL_OR");
                    state = 0;
                    break;
                case 43:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "LOGICAL_OR_NON_SHORT_CIRCUIT");
                    state = 0;
                    break;
                case 44:
                    lookahead = getToken(reader);
                    if (isDigit(lookahead)) {
                        state = 44;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '.') {
                        state = 46;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 45;
                    }
                    break;
                case 45:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "INTEGER");
                    state = 0;
                    break;
                case 46:
                    lookahead = getToken(reader);
                    if (isDigit(lookahead)) {
                        state = 46;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 47;
                    }
                    break;
                case 47:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "REAL");
                    state = 0;
                    break;
                case 48:
                    lookahead = getToken(reader);
                    if (isDigit(lookahead)) {
                        state = 49;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 51;
                    }
                    break;
                case 49:
                    lookahead = getToken(reader);
                    if (isDigit(lookahead)) {
                        state = 49;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 50;
                    }
                    break;
                case 50:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "REAL");
                    state = 0;
                    break;
                case 51:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    printToken(lexeme, "DOT_DELIMETER");
                    state = 0;
                    break;
                case 52:
                    // Double quote state
                    lookahead = getToken(reader);
                    //Character.isISOControl: check if passed character is in an ISO (takes '\u003F') form or not
                    if (lookahead == '\n' || lookahead == '\0' || Character.isISOControl(lookahead)) {
                        state = 53;
                    } else if (lookahead == '"') {
                        state = 55;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '\\') {
                        state = 56;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 54;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 53:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "DOUBLE_QUOTE");
                    state = 0;
                    break;
                case 54:
                    lookahead = getToken(reader);
                    if (lookahead == '\\') {
                        state = 56;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '\n' || lookahead == '\0' || Character.isISOControl(lookahead)) {
                        state = 57;
                    } else if (lookahead == '"') {
                        state = 55;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 54;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 55:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "STRING_LITERAL");
                    state = 0;
                    break;
                case 56:
                    lookahead = getToken(reader);
                    if (lookahead == '\n' || lookahead == '\0' || Character.isISOControl(lookahead)) {
                        state = 57;
                    } else if (lookahead == '"') {
                        state = 57;
                    } else {
                        state = 54;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 57:
                    lexeme[indexOfLexeme] = '\0';
                    // Call this method for the program to handle the occurred error
                    isError();
                    // Also get the next Token to go to state zero
                    lookahead = getToken(reader);
                    state = 0;
                    break;
                case 58:
                    lookahead = getToken(reader);
                    if (Character.isISOControl(lookahead) || lookahead == '\n' || lookahead == '\0') {
                        state = 59;
                    } else if (lookahead == '\\') {
                        state = 62;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else if (lookahead == '\'') {
                        state = 63;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 60;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 59:
                    lexeme[indexOfLexeme] = '\0';
                    printToken(lexeme, "SINGLE_QUOTE");
                    state = 0;
                    break;
                case 60:
                    lookahead = getToken(reader);
                    if (lookahead == '\'') {
                        state = 61;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 63;
                        lexeme[indexOfLexeme++] = lookahead;
                    }
                    break;
                case 61:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "CHAR_LITERAL");
                    state = 0;
                    break;
                case 62:
                    lookahead = getToken(reader);
                    if (lookahead == 'n' || lookahead == 't' || lookahead == 'r' || lookahead == 'b' || lookahead == 'f' || lookahead == '\\') {
                        state = 60;
                        lexeme[indexOfLexeme++] = lookahead;
                    } else {
                        state = 63;
                    }
                    break;
                case 63:
                    lexeme[indexOfLexeme] = '\0';
                    // Call this method for the program to handle the occurred error
                    isError();
                    // Also get the next Token to go to state zero
                    lookahead = getToken(reader);
                    state = 0;
                    break;
                case 64:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LEFT_SQUARE_BRACKET");
                    state = 0;
                    break;
                case 65:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "RIGHT_SQUARE_BRACKET");
                    state = 0;
                    break;
                case 66:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LEFT_CURELY_BRACKET");
                    state = 0;
                    break;
                case 67:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "RIGHT_CURELY_BRACKET");
                    state = 0;
                    break;
                case 68:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "LEFT_PARENTHESES");
                    state = 0;
                    break;
                case 69:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "Right_PARENTHESES");
                    state = 0;
                    break;
                case 70:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "SEMI_COLON");
                    state = 0;
                    break;
                case 71:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "COLON");
                    state = 0;
                    break;
                case 72:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "COMMA");
                    state = 0;
                    break;
                case 73:
                    lexeme[indexOfLexeme] = '\0';
                    // to continue the loop without problems change to a the new letter to get to
                    // state 0 and start again
                    lookahead = getToken(reader);
                    printToken(lexeme, "Unable to be resolved");
                    state = 0;
                    break;
            }
        }
    }

    // getToken method will read a new character by passing reader BufferReader Type,when reaches the end of file it will return null character \0
    public static char getToken(BufferedReader reader) throws IOException {
        if (errorFlag) {
            if (!fixedFlag) {
                fixedFlag = true;
                indexOfError++;
                return lexemeError[0];
            } else if (fixedFlag && !insertionFlag) {
                insertionFlag = true;
                return '\n';
            } else {
                if (indexOfError == indexOfErrorLexeme) {
                    resetErrorFlags();
                } else {
                    return lexemeError[indexOfError++];
                }
            }
        }

        int n = reader.read();
        if (n == -1) {
            return '\0';
        }
        return (char) n;
    }

    private static void resetErrorFlags() {
        errorFlag = false;
        fixedFlag = false;
        insertionFlag = false;
        indexOfError = 0;
        indexOfErrorLexeme = 0;
        lexemeError = new char[MAX_LEXEMES];
    }

    public static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    public static void printLexeme(char[] lexeme) {
        for (int i = 0; i < lexeme.length; i++) {
            if (lexeme[i] == '\0') {
                break;
            }
            System.out.print(String.valueOf(lexeme[i]));
        }
        System.out.printf("\t");
    }

    public static void printToken(char[] lexeme, String token) {
        printLexeme(lexeme);
        System.out.println(" " + token);
    }

    public static boolean isKeyword(char[] lexeme) {
        String keyword = new String(lexeme);
        for (String reservedWord : reservedWords) {
            if (reservedWord.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    public static String charToString(char[] lexeme) {
        StringBuilder sb = new StringBuilder();
        for (char n : lexeme) {
            if (n == '\0') {
                break;
            }
            sb.append(n);
        }
        return sb.toString();
    }

    public static void isError() {
        errorFlag = true;
        endFileFlag = false;
        indexOfErrorLexeme = indexOfLexeme;
        indexOfLexeme = 0;
        lexemeError = lexeme.clone();
    }
}