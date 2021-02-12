package com.upgrade.island3.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.upgrade.island3.IslandApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * OpenApiSpecificationGeneration
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@SpringBootTest(classes = IslandApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.application.admin.enabled=true"})
@ActiveProfiles("test")
class OpenApiSpecificationGeneration {

    private static final String API_DOC_URL = "http://localhost:8080/v3/api-docs";

    private static final String OUTPUT_FILE_NAME = "openapi.yaml";

    private static final Path OUTPUT_DIR = Paths.get("src", "main", "resources", "openapi");

    @Test
    void generateOpenApiSpecification() throws IOException {
        String version = System.getProperty("git.build.version", null);
        URL urlForGetRequest = new URL(API_DOC_URL);
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, responseCode);
        Files.createDirectories(OUTPUT_DIR);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ObjectNode root = (ObjectNode) objectMapper.readTree(connection.getInputStream());
        ObjectNode info = (ObjectNode) root.get("info");
        if (info != null && version != null) {
            info.put("version", version);
        }
        root.remove("servers");
        try (BufferedWriter writer = Files.newBufferedWriter(OUTPUT_DIR.resolve(OUTPUT_FILE_NAME), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            objectMapper.writeValue(writer, root);
        }
    }
}
