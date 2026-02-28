### 1. Crear un nuevo estudiante

* **Método:** `POST`
* **URL:** `http://localhost:8080/api/students`
* **Cuerpo de la Petición (JSON):**

RESPUESTA:

{
    "firstName": "Ana",
    "lastName": "García",
    "email": "ana.garcia@estudiante.com",
    "birthDate": "2001-03-12",
    "id": 1,
    "phone": "3004445566"
}

codigo de estado: 
201 Created

### 2. Obtener la lista completa

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/students`
* **Respuesta del Servidor (Completar):**
[
    {
        "firstName": "Ana",
        "lastName": "García",
        "email": "ana.garcia@estudiante.com",
        "birthDate": "2001-03-12",
        "id": 1,
        "phone": "3004445566"
    }
]


* **Código de Estado (Status Code):** `200 oK`

---

### 3. Buscar estudiante por ID (Existente)

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/students/1`
* **Respuesta del Servidor (Completar):**

{
    "firstName": "Ana",
    "lastName": "García",
    "email": "ana.garcia@estudiante.com",
    "birthDate": "2001-03-12",
    "id": 1,
    "phone": "3004445566"
}

* **Código de Estado (Status Code):** `200 ok`

---

### 4. Buscar estudiante por Email

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/students/email/ana.garcia@estudiante.com`
* **Respuesta del Servidor (Completar):**
{
    "firstName": "Ana",
    "lastName": "García",
    "email": "ana.garcia@estudiante.com",
    "birthDate": "2001-03-12",
    "id": 1,
    "phone": "3004445566"
}


* **Código de Estado (Status Code):** `200 ok`

---

### 5. Actualizar datos del estudiante

* **Método:** `PUT`
* **URL:** `http://localhost:8080/api/students/1`
* **Cuerpo de la Petición (JSON):**

```json
{
  "firstName": "Ana María",
  "lastName": "García",
  "email": "ana.garcia@estudiante.com",
  "birthDate": "2001-03-12",
  "phone": "3119998877"
}

```

* **Respuesta del Servidor (Completar):**

{
    "firstName": "Ana María",
    "lastName": "García",
    "email": "ana.garcia@estudiante.com",
    "birthDate": "2001-03-12",
    "id": 1,
    "phone": "3119998877"
}

* **Código de Estado (Status Code):** `200 OK`

---

### 6. Escenario de Error: Buscar ID inexistente

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/students/999`
* **Respuesta del Servidor (Completar):**
 1

* **Código de Estado (Status Code):** `404 Not Found`

---

### 7. Eliminar el registro

* **Método:** `DELETE`
* **URL:** `http://localhost:8080/api/students/1`
* **Respuesta del Servidor (Completar):**

 1

* **Código de Estado (Status Code):** `204 No Content`

---

## 📝 Cuestionario de Análisis

**Instrucciones:** Responda las siguientes preguntas basándose en su experiencia durante el laboratorio y el código del proyecto.

1. **¿Cuál es la diferencia entre los códigos de estado 200 y 201? ¿En qué endpoints se obtuvieron cada uno?**
* 200 OK: Indica que la solicitud fue exitosa y el servidor devuelve el recurso solicitado. Se obtiene típicamente en endpoints GET (consultar datos) y PUT/PATCH (actualizar datos existentes).
201 Created: Indica que la solicitud fue exitosa y se creó un nuevo recurso. Se obtiene en endpoints POST (crear nuevos registros, ej. POST /usuarios).


2. **En el escenario de error (punto 6), ¿qué información devuelve la API y por qué es importante para un desarrollador frontend recibir un código 404 en lugar de un código 500?**
* *Respuesta:*
Cuando ocurre un error en el punto 6, la API devuelve información como:

El código de estado (404 Not Found)
Un mensaje descriptivo (ej. "Usuario no encontrado")

¿Por qué es importante el 404 y no el 500?
404500Error del cliente (recurso inexistente)Error interno del servidorEl frontend sabe que el recurso no existe y puede actuar (mostrar "no encontrado")El frontend no sabe qué salió mal ni cómo procederPredecible y manejableIndica fallo inesperado del sistema
El 404 permite al frontend tomar decisiones precisas en la UI, como redirigir al usuario o mostrar un mensaje claro, en lugar de un genérico "algo salió mal".

3. **¿Qué sucede en la base de datos PostgreSQL cuando se ejecuta con éxito la petición DELETE? (Explique brevemente en términos de persistencia).**
* *Respuesta:*
Cuando se ejecuta un DELETE exitoso:
El registro es eliminado permanentemente de la tabla correspondiente.
El cambio se persiste en disco: una vez confirmada la transacción (COMMIT), el dato no puede recuperarse a menos que exista un respaldo.
PostgreSQL libera el espacio lógico de la fila (aunque el espacio físico se recupera después con VACUUM).
Si hay claves foráneas con ON DELETE CASCADE, los registros relacionados en otras tablas también se eliminan automáticamente.

4. **Si intentara crear un estudiante con el mismo email que ya existe en la base de datos, ¿qué cree que sucedería y qué código de error sería el más adecuado para devolver?**
* *Respuesta:*
Si el campo email tiene una restricción UNIQUE en PostgreSQL (lo cual es buena práctica), sucedería lo siguiente:
A nivel de base de datos:
PostgreSQL lanzaría un error de violación de restricción única (unique_violation, código 23505)
La transacción se revertiría automáticamente
A nivel de API, el código de error más adecuado sería:
409 Conflict
Porque indica que la solicitud entra en conflicto con el estado actual del recurso en el servidor.

5. **¿Por qué utilizamos el método PUT para actualizar y no el método POST? ¿Cuál es la convención técnica detrás de esta decisión?**
* *Respuesta:*
La diferencia está en dos conceptos técnicos clave del protocolo HTTP:
Idempotencia:

PUT es idempotente: ejecutarlo 1 vez o 10 veces con los mismos datos produce el mismo resultado. El recurso queda en el mismo estado final.
POST NO es idempotente: ejecutarlo múltiples veces puede crear múltiples recursos nuevos.

Semántica del método:
MétodoSemánticaUso correctoPOST"Crea algo nuevo"POST /estudiantes → crea un estudiantePUT"Reemplaza este recurso"PUT /estudiantes/5 → reemplaza el estudiante 5PATCH"Modifica parcialmente"PATCH /estudiantes/5 → actualiza solo algunos campos
La convención REST establece que las URLs con identificador explícito (/estudiantes/:id) + PUT significan "toma este recurso y reemplázalo completamente", lo cual comunica intención clara tanto al servidor como a otros desarrolladores que consuman la API




