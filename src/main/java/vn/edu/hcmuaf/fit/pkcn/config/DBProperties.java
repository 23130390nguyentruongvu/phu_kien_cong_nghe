package vn.edu.hcmuaf.fit.pkcn.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    private static Properties prop = new Properties();

    static {
        try {
            // Load file db.properties
            InputStream input = DBProperties.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new IOException("Cannot find db.properties file in classpath");
            }

            prop.load(input);
            input.close();

            System.out.println("Properties loaded successfully!");

        } catch (IOException e) {
            System.err.println("Failed to load db.properties");
            e.printStackTrace();
            throw new RuntimeException("Cannot load database properties", e);
        }
    }

    public static String getHost() {
        return prop.getProperty("db.host");
    }

    public static String getPort() {
        return prop.getProperty("db.port");
    }

    public static String getUsername() {
        return prop.getProperty("db.username");
    }

    public static String getPassword() {
        return prop.getProperty("db.password");
    }

    public static String getDbname() {
        return prop.getProperty("db.name");
    }

    public static void main(String[] args) {
        System.out.println("Host: " + getHost());
        System.out.println("Port: " + getPort());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());
        System.out.println("Database: " + getDbname());
    }
}
