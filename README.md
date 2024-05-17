# Executar projeto

- Requisito Java 17

Para executar o código já compilado o comando usado é:

```
java -jar .\Gateway.jar .\lib\properties
```

Caso seja necessário recompilar o código os comando utilizados são:

```
javac -encoding UTF-8 .\src\googol\*.java -d .\
jar cfe UrlQueue.jar googol.UrlQueueApp .\googol\*.class
```