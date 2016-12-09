INC_DIR 	=	include
SRC_DIR 	= 	src 
OBJ_DIR 	= 	build
LIB_DIR 	= 	lib

CC 			= 	g++
CFLAGS 		= 	-Wall -pedantic -std=c++11 -ansi -I. -Iinclude
ARCHIVE 	= 	ar 

linux:		petfera.so prog_dinamico

windows:	petfera.dll prog_dinamico.exe

# LINUX

petfera.so: src/main.cpp src/funcionario.cpp src/animal.cpp include/funcionario.h include/animal.h include/erros.h
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -fPIC -c src/main.cpp -o build/main.o
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -fPIC -c src/funcionario.cpp -o build/funcionario.o
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -fPIC -c src/animal.cpp -o build/animal.o
	g++ -shared -fPIC -o lib/$@ build/main.o build/funcionario.o build/animal.o
	@echo "+++ [Biblioteca dinamica criada em lib/$@] +++"

prog_dinamico:
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 src/exportar.cpp -L lib lib/petfera.so -o build/prog_dinamico

# WINDOWS

petfera.dll: src/funcionario.cpp src/animal.cpp src/main.cpp include/funcionario.h include/erros.h include/animal.h
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -c src/funcionario.cpp -o build/funcionario.o
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -c src/animal.cpp -o build/animal.o
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 -c src/main.cpp -o build/main.o
	g++ -shared -o lib/$@ build/main.o build/funcionario.o build/animal.o
	@echo "+++ [Biblioteca dinamica criada em lib/$@] +++"

prog_dinamico.exe:
	g++ -Wall -pedantic -ansi -I. -I include -std=c++11 src/main.cpp lib/petfera.dll -o build/prog_dinamico.exe

clean:
	@echo "Removendo arquivos objeto e executaveis/binarios..."
	@rm -rf build/*