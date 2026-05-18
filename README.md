# 🏢 UrbanizaT

> Sistema Operativo Vecinal (SOV) y aplicación móvil para la gestión inteligente y centralizada de comunidades de propietarios.

---

## 🚀 Descripción del Proyecto

**UrbanizaT** es una solución móvil nativa diseñada para eliminar la burocracia en papel y agilizar la comunicación comunitaria. Su enfoque central prioriza una experiencia de usuario (UX) sumamente intuitiva, pensada para permitir la inclusión activa de personas de avanzada edad en la vida de su bloque. 

Mediante una infraestructura escalable, centraliza reportes de averías, publicación de cuentas claras de forma anónima y la organización general vecinal.

---
## Código de comunidad para pruebas

Para registrarse como nuevo usuario, usar el siguiente código de comunidad:

**Código:** `534354` (Comunidad: Loma Linda)


## ✨ Características Principales

*   **🛠️ Gestión de Incidencias:** Reportes rápidos con descripción detallada y adjuntos fotográficos.
*   **📢 Tablón Comunitario:** Espacio cronológico para avisos independientes y anuncios de vecinos.
*   **📊 Transparencia Financiera:** Gráficas dinámicas e intuitivas de gastos e ingresos sin exponer datos nominativos.
*   **🗳️ Sistema de Encuestas:** Herramienta ágil de votación digital para agilizar la toma de decisiones críticas.
*   **📅 Calendario de Mantenimiento:** Agenda unificada para revisiones técnicas y alertas de técnicos del edificio.
*   **🔐 Control de Acceso Estricto:** Registro privado mediante un código de comunidad exclusivo e inicio de sesión por roles diferenciados (Vecino, Presidente, Administrador).

---

## 🛠️ Stack Tecnológico

El núcleo de la aplicación utiliza tecnologías móviles modernas y servicios escalables descentralizados en la nube:

*   **Lenguaje:** Kotlin
*   **Interfaz Gráfica:** Jetpack Compose (Arquitectura declarativa y moderna)
*   **Entorno de Desarrollo:** Android Studio
*   **Backend as a Service:** Supabase
*   **Base de Datos:** PostgreSQL
*   **Automatización integrada:** Triggers en PL/PhSQL para sincronización nativa

---

## 🏗️ Arquitectura y Diseño

UrbanizaT implementa un patrón robusto Cliente-Servidor estructurado bajo componentes modulares e independientes:

*   **Capa Cliente:** Aplicación nativa en sistema Android encargada del comportamiento visual y navegación fluida.
*   **Capa Lógica:** Procesamiento, validación estricta de permisos según roles y flujos de negocio alternativos.
*   **Capa de Datos:** Persistencia relacional en Supabase (Región West EU - Irlanda) y almacenamiento en nube para archivos multimedia.

---

## ⚙️ Guía de Instalación y Configuración

Sigue estos pasos detallados para clonar, configurar y desplegar el proyecto localmente utilizando **Android Studio**:

### 1. Requisitos Previos
*   Contar con **Android Studio** (versión *Ladybug* o superior recomendada).
*   Instalar el SDK de Android compatible con la API mínima del proyecto.
*   Una cuenta activa en **Supabase**.

### 2. Clonar el Repositorio
Abre tu terminal y ejecuta el comando de clonación, o impórtalo directamente desde la interfaz de Android Studio:
```bash
git clone https://github.com
```

### 3. Configuración del Entorno (Variables de Entorno)
Para que la app se conecte correctamente a tu instancia de base de datos en la nube, debes proveer las credenciales de Supabase:
1. Localiza el archivo `local.properties` en la raíz de tu proyecto Android Studio.
2. Añade tus claves del proyecto obtenidas desde el panel de Supabase:
   ```properties
   SUPABASE_URL="https://supabase.co"
   SUPABASE_ANON_KEY="tu-clave-anon-publica"
   ```

### 4. Compilación del Proyecto
1. Permite que Android Studio sincronice los archivos de configuración de Gradle (`Sync Project with Gradle Files`).
2. Ve al menú superior y selecciona **Build > Make Project** para verificar que todas las librerías auxiliares se descarguen correctamente.

### 5. Ejecución
*   Conecta un dispositivo físico Android con la *Depuración por USB* activa o inicia un Dispositivo Virtual (Emulador) desde el **Device Manager**.
*   Haz clic en el botón **Run 'app'** (icono de play verde 🟢) en la barra de herramientas superior.

---

## ⚡ Automatización en Backend: Trigger SQL

Para garantizar la integridad relacional de la plataforma, implementamos un procedimiento almacenado y un disparador automático (*Trigger*) en Supabase. Cada vez que un usuario completa el flujo de autenticación, su perfil se mapea de forma transparente en la tabla de negocio con el rol base de `VECINO`:

```sql
-- 1. Definición de la función encargada de procesar la inserción
BEGIN
    INSERT INTO public.usuario (
        email,
        name,
        door,
        comunity_code,
        rol
    ) VALUES (
        NEW.email,
        COALESCE(
            NEW.raw_user_meta_data->>'name',
            split_part(NEW.email, '@', 1)
        ),
        NEW.raw_user_meta_data->>'door',
        (NEW.raw_user_meta_data->>'comunity_code')::BIGINT,
        'VECINO'::public."Roles"
    );

    RETURN NEW;
END;

-- 2. Creación del Trigger que se dispara tras una inserción en auth.users
CREATE TRIGGER on_auth_user_created
AFTER INSERT ON auth.users
FOR EACH ROW
EXECUTE FUNCTION public.handle_new_user();
```

---

## 🔮 Prospectiva: UrbanizaT "Fase 2" (Integración IoT)

La arquitectura distribuida sobre Supabase y Jetpack Compose está proyectada para evolucionar la aplicación hacia un ecosistema de **Smart Building** y pertenencia local (Sistema Operativo Vecinal). La siguiente fase de desarrollo contempla la transición de reportes reactivos hacia una **presencia invisible automatizada**:

### 🛠️ Arquitectura de Sensores Autónomos
*   **Detección Preventiva de Daños:** Integración de sensores físicos de humedad en áreas críticas (ej. garajes de la comunidad) configurados mediante arquitecturas *hardware* de bajo consumo.
*   **Disparadores por Webhooks:** Ante una anomalía física, el sensor enviará una petición HTTP POST a un endpoint automatizado de Supabase.
*   **Flujo Automatizado de Incidencias:** El sistema generará e insertará una nueva incidencia en la base de datos automáticamente, notificando al Presidente de la comunidad y asignando el servicio al técnico asignado antes de que un vecino note el problema.

### 🏙️ Conectividad Urbana Global
A largo plazo, UrbanizaT dejará de operar de forma aislada para transformarse en un **nodo urbano**. La agregación inteligente de datos anónimos sobre incidencias y eficiencia energética comunitaria permitirá transferir métricas críticas a las plataformas de *Smart Cities* de los ayuntamientos locales, optimizando la conservación de infraestructuras público-privadas.

---

## 👥 Autores y Equipo

Desarrollado como Trabajo Final del Módulo de Proyecto para el **CFGS de Desarrollo de Aplicaciones Multiplataforma** (Curso 2025-2026):

*   **Ariana Toscano Villalba**
*   **Isabel Martinez Polo**
*   **Ainoa Dura Bordonado**
*   **Aleksander Trujillo Prokhorenko**
*   **Jhon Felipe Murcia Urrego**

*Docente tutor:* Albert Llabrés Darder

---

## 🛡️ Licencia e Información Académica

Este software es un proyecto oficial de carácter académico tutorizado y desarrollado bajo el marco educativo de **CEAC FP Oficial**. Todos los derechos reservados por el equipo de desarrollo.
