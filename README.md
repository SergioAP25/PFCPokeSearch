# PFCPokeSearch

PokeSearch es una aplicación Android de código abierto que permite buscar, filtrar y obtener información detallada
sobre cualquier Pokemon existente.
Hecha en Android estudio utilizando [PokeApi](https://pokeapi.co/) y [Firebase](https://firebase.google.com/?hl=es).

## Características
 - Sistema de login y registro básicos utilizando Firebase.
 - Sistema de login con Google utilizando Firebase.
 - Pantalla de Home que genera información sobre un Pokemon aleatorio.
 - Pantalla Pokédex donde se pueden filtrar Pokemon mediante filtros y sus combinaciones además de la posibilidad de añadirlos a favoritos
 - Pantalla de Favoritos donde puedes ver tus Pokemon favoritos y filtrarlos como en Pokédex
 - Pantalla Detalle a la cual se accede al hacer click en un Pokemon la cual muestra información del Pokemon seleccionado
 - Pantalla Imagen Completa a la cual se accede al hacer click en la imagen en la ventana de Detalle la cual muestra la imagen en pantalla completa
 - Pantalla de Options donde se muestra el email con el que el usuario ha hecho login y el tipo de proveedor además de poder hacer logout

## Capturas de pantalla
<img src="screenshots/pokedex.png" align="left"
width="200" hspace="10" vspace="10">
<img src="screenshots/detail.png" align="center"
width="200" hspace="10" vspace="10">

## Herramientas y librerías utilizadas
- [PokeApi](https://pokeapi.co/).
- [Firebase](https://firebase.google.com/?hl=es).
- [Retrofit2](https://square.github.io/retrofit/).
- [Dagger Hilt](https://dagger.dev/hilt/).
- Room
- [Picasso](https://square.github.io/picasso/).

## Información adicional
Los datos se van recogiendo de la Api e insertando uno a uno en la base de datos, por defecto la pestaña de Home mostrará a Bulbasaur, pero puede que la pestaña de Pokédex
aparezca vacía en un inicio. Aunque la carga de datos esté en progreso la aplicación se puede seguir utilizando igual, tan solo haz swipe en la pestaña de Pokédex o cambia
de pestaña y vuelve para ver cómo van apareciendo los Pokemon ya insertados en la base de datos.

## Funcionamiento
Para utilizar la aplicación simplemente habre el proyecto pfcpokesearch en Android Studio y ejecútalo
