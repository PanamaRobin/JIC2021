# Proyecto JIC
## Instrucciones para clonar

1. Descargar e instalar git en sus computadoras.
[Click en este link para descargarlo](https://git-scm.com/downloads)

2. Abrir la consola git bash en la ubicación donde quieren guardar el proyecto (Click derecho -> Git bash here), preferiblemente busquen la ubicación donde se guardan todos sus proyectos de Android Studio. ` (C:\Users\jlher\AndroidStudioProjects) `

3. Dentro de la consola Git Bash, ejecutan el comando
``` 
git clone -b <nombre de la rama> https://github.com/PanamaRobin/JIC2021.git

En la parte del nombre de la rama van a poner sus respectivas ramas:
- Josias: josias
- Morales: morales
- Nicolas: nico

Por ejemplo:
git clone -b josias https://github.com/PanamaRobin/JIC2021.git
``` 
` De esta manera se van a asegurar de descargar sus respectivas ramas y no tener conflictos más adelante. `

4. Una vez que se clone el proyecto, lo abren desde Android Studio y empiezan a trabajar en sus respectivas vistas.

```
MainActivity - activity_main [Josias]
NuevaSolicitudActivity - activity_nueva_solicitud [Morales]
SolicitudesActivity - activity_solicitudes [Herrera]
```
---
## Manejo de versiones en sus ramas
Cada día que terminen trabajos o luego de finalizar una tarea importante, deben asegurarse de subir sus cambios a sus respectivas ramas, para esto deben seguir estas instrucciones. 

` ***DE NO QUEDAR CLAROS POR FAVOR CHATEENME*** `

**Existen varias maneras de manejar las versiones de sus proyectos, pero la que van a utilizar va a ser dentro de las opciones del Android Studio, asegurense de hacer esto antes de empezar a trabajar.** 

1. Diríjanse a la pestaña de la barra de tareas que dice **VCS**.

2. Se dirijen a la opción **Commit** y le dan click.

3. Luego deberán incluir un mensaje con los cambios o avances realizados al proyecto.

4. Le dan click al triángulo en el botón **Commit** y seleccionan la opción ` Commit and Push `.

5. Se aseguran de subir los cambios a sus respectivas ramas, en caso de desplegarse un mensaje de que tienen warnings y 0 errores, le dan click en ` Commit and Push ` y listo.

6. Esto deben de asegurarse de siempre hacerlo en sus ramas, ya que si intentan subir a la rama master les enviará un mensaje de error.

---
## Unión del proyecto
En algún momento nos tocará hacer la unión de las partes de proyecto, esta parte es un poco más complicada y requiere que todos nos conectemos un día para explicárselos con más detalle.

---
## Archivos y carpetas del proyecto
Dentro del proyecto se encuentran 2 aspectos importantes:
- La carpeta **Drawable**.
- Las vistas y la referencia a los objetos en drawable, como íconos o backgrounds.

Dentro de la carpeta **Layouts** se encuentran todas las vistas, les dejé una vista ejemplo llamada:
``` 
example_layout.xml 
``` 

Esta vista es simple y les dejé un par de comentarios para que se guíen dentro de la creación de sus vistas.

---
## Documentación y apoyo
Guíense de tutoriales en Youtube o Google, busquen ejemplos o cosas que les salgan en los errores para poder resolver, de cualquier modo les voy a dejar una lista de reproducción donde me encargué de guardar vídeos que me fueron de apoyo durante la JIC del año pasado. ` Los vídeos están en Inglés así que si no entienden solo fíjense en lo que están haciendo para que lo puedan replicar `.

[Link de la lista de reproducción](https://www.youtube.com/playlist?list=PLPz6Mz6sWMAU_rXvcG1OwJ1WGzea0_8t3)