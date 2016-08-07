#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char keywords[] = {"PRINT"};
char tokens[][16] = {"START"};

//**********************************************//
//		 	  Retreving File Contents 	     	//
//**********************************************//
char* file(char* path) {
	char* buf = 0;
	int fileLen;
	long len;

	// File initiation
	FILE* fp = fopen(path, "rb");

	// Allocate memory for file sizes and contents
	if (fp) {
		fseek(fp, 0, SEEK_END);
		len = ftell(fp);
		fseek(fp, 0, SEEK_SET);
		buf = malloc(len);
		if (buf) {
			fread(buf, 1, len, fp);
		}
	}
	// Close program file
	fclose(fp);

	if (buf) {
		// Return file contents
		return buf;
	}
}

void strAppend(char targetArray[2048], char obj) {
	int length;
	length = strlen(targetArray);
	targetArray[length] = obj;
	targetArray[length + 1] = 0;
}

//**********************************************//
//				Lexical Analysis 				//
//**********************************************//
char* lex(char contents[2048]) {
	int i;
	int len;
	char currTok[2048];
	char* tokPointer = currTok;
	char curr; // Current character that is being read
	for (i = 0; i < strlen(contents); i++) {
		// Remove spaces from program contents
		if (contents[i] == ' ') { 
			memmove(&contents[i], &contents[i + 1], strlen(contents) - i);
		}

		len = strlen(currTok);
		currTok[len] = contents[i];
		currTok[len + 1] = 0;
		currTok[0] = ':';
		
		printf("%s\n", currTok);

		if (!strcmp(currTok, ":PRINT")) {
			int length;
			length = strlen(tokens[i]);
			tokens[length] = "PRINT";
			//strAppend(tokens[length + 1], 'h');
			//strcpy(tokens, ":PRINT");
			printf("PRINT detected\n");
		}
	}

	printf("%s\n", contents);
}

//**********************************************//
//				Initializer Method				//
//**********************************************//
int main(int argv, char* argc[]) {
	char* cont = file(argc[1]);
	lex(cont);
	printf("%s\n", tokens[0]);
	return 0;
}