# Acceso a datos [ 2º DAM ] - Examen 1ª Evaluación

## Contenidos
1. Ficheros
   1. Binarios
   2. De texto
   3. De acceso aleatorio
2. Bases de datos relacionales:
   1. Sentencias parametrizadas
   2. Transacciones
   3. Lotes
   4. Funciones y procedimientos

## Instrucciones

- Leer todo el contenido de este fichero detenidamente antes de empezar.
- Cada ejercicio puntúa diferente y está compuesto por una serie de apartados que van puntuando. 
Es altamente recomendable detectar qué ejercicios se pueden hacer de manera más sencilla y rápida para aprovechar el 
tiempo de la mejor manera posible.
- Todos los métodos tendrán que contemplar las excepciones necesarias.
- ⚠️ Se recuerda que, para los ejercicios de bases de datos, es necesario instalar el driver de MySQL.

## Enunciados

El examen consta de un único archivo en el que se irán creando los distintos métodos que se plantean más abajo. 
Dichos métodos se crearán con el mismo nombre que se propone en este enunciado y se llamarán desde el main para probarlos.

### - crearConexion() [2pts]
Crear un método que permita crear una conexión con una base de datos cuyos datos se facilitan a continuación. 
El método ha de retornar un objeto de tipo Connection, con la conexión a la base de datos. 
**No es necesario** que reciba los parámetros de la conexión como argumentos.

>⚠️ Es importante incluir el driver de MySQL en este proyecto para poder testear y ejecutar dicha conexión correctamente. 

- Host: containers-us-west-167.railway.app
- Puerto: 7395
- Nombre base de datos: nombre_primera-letra-apellido
- Usuario: nombre_primera-letra-apellido
- Contraseña: nombre_primera-letra-apellido_ + 4 últimos caracteres del DNI

Ejemplo: 
```
bbdd: rodrigo_s
usuario: rodrigo_s
contraseña: rodrigo_s_845Z
```

### insertarConTransaccion(File ficheroTexto) [4pts]

Crear método que reciba un fichero de texto y que sirva para insertar su contenido en la base de datos con una transacción.
Concretamente, el método ha de realizar las siguientes acciones:

- **[0,5pt]** Crear una tabla extrayendo el nombre de la tabla del nombre del archivo y quitándole la extensión 
(utilizar para ello el método `replace(".txt", "")`).


- **[1pt]** Los campos que definen la tabla, están en la primera fila del fichero de texto, extraerlos del fichero e 
incluirlos en la sentencia para crear la tabla 
(no es un fichero de acceso aleatorio, sólo hay que leer la primera fila para sacar dichos campos).


- **[1,5pt]** En la segunda fila, se recoge el nombre de los campos que se van a insertar y a partir de la tercera se recogen los registros a insertar.
Recorrer las filas con los datos con un bucle. Cada fila del fichero de texto representa un insert. Realizar cada insert por separado. 
Dentro de cada fila (registro), los distintos valores están separados por comas. Si, con cada fila se utiliza el método `.split(",")`, 
se genera un array que contiene los diferentes datos en las distintas posiciones del array. 
Iterando dicho array, se pueden insertar los distintos valores del registro. Utilizar una sentencia parametrizada. 
Como los datos contenidos en el array están en formato String, se pueden asignar todos a la sentencia parametrizada utilizando `.setString(indice,valor)`. 


- **[1pt]** Realizar el conjunto de inserts dentro de una transacción para que, en caso de que falle un insert, no se ejecute ninguno.
  (es necesario "aplicar" cada insert por cada registro que se quiera introducir)


### insertarConLote(File ficheroTexto) [1pt]
Crear un método similar al anterior, pero que en vez de utilizar transacciones, vaya añadiendo los distintos inserts a un lote
y cuando termine de iterar el fichero de texto, ejecute dicho lote.

### ejecutarSQL(File ficheroTexto) [2pt]
Método que recibe como argumento un fichero de texto con una sentencia SQL guardada en su interior. El método tiene que
leer el fichero de texto y ejecutar su contenido como una sentencia en la base de datos. 
Se recomienda crear una sentencia Statement, que luego se ejecuta con el método `execute`. 
Ejecutar este método desde el main, utilizando como argumento el fichero `funcion.txt`. 
Se trata de una función almacenada SQL, la cual quedará guardada en la base de datos.
No es necesario llamar a dicha función, ya que para eso se realizará un método en el siguiente apartado.

### ejecutarFuncion(String nombreFuncion, String argumentoFuncion) [1pt]
Método que recibe el nombre de una función, y el argumento necesario para su ejecución. El nombre 
de la función es hacerPedido, el argumento es `8` y lo que devuelve es un string. Preferentemente, realizar este ejercicio ejecutando un Statement 
con `executeQuery`, guardar el resultado en un ResultSet. El sql que hay que ejecutar es `{? = call nombreFunción(argumento)}`.
Crear un fichero de texto llamado `resultado_funcion.txt` que contenga lo que devuelve la función.

