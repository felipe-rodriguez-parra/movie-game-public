# Design Document: GuessTheMovie

## 1. Overview

**GuessTheMovie** es un juego interactivo en Java donde los jugadores adivinan el título de una película seleccionada aleatoriamente a partir de una lista.

El programa utiliza archivos externos para cargar películas y guardar puntuaciones.

## 2. High-Level Architecture

El sistema está compuesto por tres componentes principales:

1. **Clase principal: GuessTheMovie**

   - Contiene la lógica principal del flujo del juego.
   - Maneja la carga de datos, inicio del juego, y actualizaciones del ranking.

2. **Clase GameSession**

   - Representa una sesión individual de juego.
   - Administra la lógica de adivinanza, estado del juego, y cálculo de puntuaciones.

3. **Clase interna ScoreEntry**

   - Representa las entradas del ranking.
   - Se utiliza para guardar y comparar puntuaciones de los jugadores.

## 3. Functional Requirements

### 3.1 Funcionalidades clave

- **Inicio del juego**: Permitir al jugador comenzar una partida proporcionando un nombre de usuario.
- **Carga de películas**: Leer un archivo de texto con títulos de películas para seleccionar una al azar.
- **Interacción del jugador**:
  - Adivinar letras del título.
  - Adivinar el título completo.
  - Ver pistas como letras reveladas y errores acumulados.
- **Sistema de puntuación**:
  - Incrementar la puntuación por respuestas correctas.
  - Reducir la puntuación por errores.
- **Ranking persistente**:
  - Guardar las mejores puntuaciones en un archivo binario.
  - Mostrar el top 5 en cada finalización del juego.

## 4. Non-Functional Requirements

- **Portabilidad**: El programa debe ejecutarse en cualquier sistema operativo que soporte Java 8 o superior.
- **Persistencia**: Los datos de las películas y puntuaciones deben mantenerse entre ejecuciones del programa.
- **Robustez**: Manejar errores de entrada, como archivos inexistentes o entradas no válidas del jugador.
- **Interactividad**: Proveer una experiencia fluida y comprensible para el jugador.

## 5. Class Design

### 5.1 GuessTheMovie

- **Responsabilidades**:
  - Controlar el flujo del programa.
  - Gestionar el inicio y fin de las sesiones de juego.
  - Cargar películas y puntuaciones.
  - Actualizar y mostrar el ranking.

#### Atributos

- `MAX_ATTEMPTS`: Número máximo de intentos permitidos (int).
- `MOVIES_FILE`: Ruta al archivo de texto con películas (String).
- `SCORES_FILE`: Ruta al archivo binario con puntuaciones (String).
- `MAX_RANKING`: Número máximo de entradas en el ranking (int).

#### Métodos principales

- `startGame(String nickname)`: Inicia el juego para el usuario proporcionado.
- `loadMovies()`: Lee las películas del archivo.
- `playGame(String movie, String nickname)`: Ejecuta una partida de adivinanza.
- `endGame(GameSession session)`: Finaliza la partida y actualiza el ranking.
- `updateRanking(String nickname, int score)`: Actualiza el ranking con la puntuación de un jugador.
- `loadRanking()`: Carga el ranking desde el archivo.
- `saveRanking(List<ScoreEntry> ranking)`: Guarda el ranking en el archivo.
- `displayRanking()`: Muestra el ranking actual.

### 5.2 GameSession

- **Responsabilidades**:
  - Gestionar el estado del juego para un jugador.
  - Implementar la lógica de adivinanza.
  - Controlar los intentos restantes y actualizar la puntuación.

#### Atributos

- `movie`: Título de la película a adivinar (String).
- `maxAttempts`: Máximo de intentos permitidos (int).
- `attempts`: Intentos restantes (int).
- `score`: Puntuación del jugador (int).
- `nickname`: Nombre del jugador (String).
- `guessedLetters`: Letras correctamente adivinadas (Set).
- `incorrectLetters`: Letras incorrectas (Set).
- `scanner`: Scanner para leer entradas del jugador (Scanner).

#### Métodos principales

- `start()`: Comienza la sesión interactiva.
- `displayGameState()`: Muestra el estado actual del juego.
- `guessLetter()`: Procesa la adivinanza de una letra.
- `guessTitle()`: Procesa la adivinanza del título completo.
- `getMaskedTitle()`: Genera el título con letras adivinadas ocultando las demás.
- `getMovie()`: Devuelve el título de la película.
- `getScore()`: Devuelve la puntuación actual.
- `getNickname()`: Devuelve el nombre del jugador.

### 5.3 ScoreEntry

- **Responsabilidades**:
  - Representar una entrada en el ranking.
  - Proveer una forma de serializar los datos de las puntuaciones.

#### Atributos

- `nickname`: Nombre del jugador (String).
- `score`: Puntuación obtenida (int).

#### Métodos principales

- `ScoreEntry(String nickname, int score)`: Constructor para inicializar una entrada.

## 6. Data Flow Diagram

1. **Entrada del jugador**: El usuario proporciona un nombre y elige opciones durante el juego.
2. **Carga de datos**: Se cargan películas y puntuaciones desde los archivos correspondientes.
3. **Interacción del juego**: Adivinanza de letras o título completo.
4. **Actualización de estado**: Actualización de intentos, puntuación y letras adivinadas.
5. **Persistencia de datos**: Al final del juego, se actualiza el ranking en el archivo binario.
6. **Salida**: El programa muestra el estado del juego y el ranking final.

## 7. File Structure

```
/src
  |-- GuessTheMovie.java
  |-- GameSession.java
  |-- movies.txt
  |-- ranking.dat
```

## 8. Error Handling

- **Archivos no encontrados**: Mostrar mensajes claros al usuario y permitir que el programa continúe si es posible.
- **Entradas inválidas**: Validar las opciones del menú y las entradas del usuario, solicitando nuevas si no son válidas.
- **Serialización fallida**: Manejar excepciones al leer/escribir el archivo de puntuaciones.



