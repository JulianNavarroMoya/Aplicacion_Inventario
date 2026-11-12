package org.example.aplicacion_inventario.data;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {

    /** Nombre del fichero de configuración dentro de los recursos. */
    private static final String CONFIG_FILE = "application.properties";

    /** Configuración leída de {@code application.properties} (puede estar vacía). */
    private static final Properties config = loadConfig();

    /** URL de conexión a la base de datos MySQL. */
    private static final String url = resolve("db.url", "DB_URL", "jdbc:mysql://localhost:3309/ad");

    /** Nombre de usuario: variable de entorno {@code USER}, properties o {@code user}. */
    private static final String username = resolve("db.user", "USER", "user");

    /** Contraseña: variable de entorno {@code PASSWORD} o entrada {@code db.password}. */
    private static final String password = resolve("db.password", "PASSWORD", null);

    /** Instancia única del {@link DataSource}, creada de forma perezosa. */
    private static DataSource dataSource = null;

    /**
     * Devuelve el {@link DataSource} compartido, creándolo si aún no existe.
     *
     * @return el {@link DataSource} configurado para conectar con MySQL
     * @throws RuntimeException si no se ha configurado la contraseña
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            if (password == null || password.isBlank()) {
                throw new RuntimeException(
                        "No se ha configurado la contraseña de la base de datos. Define db.password en "
                                + CONFIG_FILE + " (src/main/resources) o la variable de entorno PASSWORD.");
            }
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(url);
            ds.setUser(username);
            ds.setPassword(password);
            dataSource = ds;
        }
        return dataSource;
    }

    /**
     * Resuelve un valor de configuración. Prioridad: variable de entorno,
     * {@code application.properties} y, por último, el valor por defecto.
     *
     * @param propertyKey clave dentro de {@code application.properties}
     * @param envKey      nombre de la variable de entorno
     * @param defaultValue valor devuelto si no hay nada configurado
     * @return el valor resuelto, o {@code defaultValue} si no existe
     */
    private static String resolve(String propertyKey, String envKey, String defaultValue) {
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromFile = config.getProperty(propertyKey);
        if (fromFile != null && !fromFile.isBlank()) {
            return fromFile;
        }
        return defaultValue;
    }

    /**
     * Carga {@code application.properties} desde los recursos de la aplicación.
     *
     * @return las propiedades cargadas, o unas vacías si el fichero no existe
     */
    private static Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream is = DataSourceFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            // Si no se puede leer la configuración se usan los valores por defecto.
        }
        return props;
    }
}
