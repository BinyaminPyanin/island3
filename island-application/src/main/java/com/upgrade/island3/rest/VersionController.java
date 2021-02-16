package com.upgrade.island3.rest;


import com.upgrade.island3.model.SystemInformation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

/**
 * VersionController
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@Slf4j
@RestController
@Tag(name = "System Git Information Operations")
public class VersionController {
    private static final String GIT_COMMIT_ID = "git.commit.id";
    private static final String NOT_AVAILABLE = "NOT_AVAILABLE";
    private static final String BUILD_VERSION = "git.build.version";
    private static final String GIT_BUILD_TIME = "git.build.time";
    private static final String GIT_PROPERTIES_FILE_NAME = "git.properties";

    private final SystemInformation systemInformation;

    public VersionController() {
        Properties props = readGitProperties();
        systemInformation = new SystemInformation();
        systemInformation.setVersion(props.getProperty(BUILD_VERSION, NOT_AVAILABLE));
        systemInformation.setBuildTimestamp(props.getProperty(GIT_BUILD_TIME, NOT_AVAILABLE));
        systemInformation.setGitHash(props.getProperty(GIT_COMMIT_ID, NOT_AVAILABLE));
    }

    @Operation(summary = "Returns the system and git information", description = "Returns the system and git information for UpgradeIsland application.")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SystemInformation.class)))
    @GetMapping(path = "/appgitinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public SystemInformation appGitInfo() {
        return this.systemInformation;
    }

    @SneakyThrows(IOException.class)
    private Properties readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties props = new Properties();
        props.load(classLoader.getResourceAsStream(GIT_PROPERTIES_FILE_NAME));
        return props;
    }
}
