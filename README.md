# UpLang Release 0.0.x

## INTRODUCTION
Welcome to the code base of UpLang! Here are listed release notes for new versions for the project, tips on the language, how to install it, and much more.

## ABOUT
UpLang is a really light-weight programming language based on BASIC syntax. It was initially created as a fun side-project by Mattias Zurkovic. As of now, the written UpLang program is translated to Java code, witch then gets compiled through the JVM.

## Hello, World!
As mentioned previosly, UpLang has very similar syntax to BASIC code. However, if you are not fimiliar with BASIC, here is a sample program in UpLang!
```
PRINT "Hello, World!"
```

## RUNNING A PROGRAM
Once you have written your .up UpLang program file you need to install the UpLand translator. To do this, you need to have the source file and compile it. Once that is compiled and binded globally, run it as a Java file and pass your `.up` program file as the first and only command line argument. This will translate the UpLang code and get compiled and executed!

For example, say we saved our `Hello, World!` program to `hello.up`. To run the peice of code, we would pop open our terminal and enter the following:
```
$ java up hello.up
Hello, World!
```