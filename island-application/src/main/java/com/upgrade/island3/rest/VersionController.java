package com.upgrade.island3.rest;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
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
public class VersionController {

    private static final String GIT_COMMIT_ID = "git.commit.id";
    private static final String NOT_AVAILABLE = "NOT_AVAILABLE";
    private static final String BUILD_VERSION = "git.build.version";
    private static final String GIT_BUILD_TIME = "git.build.time";
    private static final String VERSION = "version";
    private static final String BUILD_TIMESTAMP = "build-timestamp";
    private static final String GIT_HASH = "git-hash";
    private static final String GIT_PROPERTIES_FILE_NAME = "git.properties";

    @GetMapping(path = "/appgitinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String appGitInfo() {

        Properties props = readGitProperties();
        JSONObject response = new JSONObject();
        response.put(VERSION, props.getProperty(BUILD_VERSION, NOT_AVAILABLE));
        response.put(BUILD_TIMESTAMP, props.getProperty(GIT_BUILD_TIME, NOT_AVAILABLE));
        response.put(GIT_HASH, props.getProperty(GIT_COMMIT_ID, NOT_AVAILABLE));
        return response.toJSONString();
    }

    @SneakyThrows(IOException.class)
    private Properties readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties props = new Properties();
        props.load(classLoader.getResourceAsStream(GIT_PROPERTIES_FILE_NAME));
        return props;
    }
}
