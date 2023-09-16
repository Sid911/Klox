## Klox Parser grammar

```text
expression      -> equality;
equality        -> comparison(("!="| "==") comparison)*;
comparison      -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
unary           -> ( "!" | "-" ) unary
               | primary ;
primary         -> NUMBER | STRING | "true" | "false" | "nil"
               | "(" expression ")" ;
```


