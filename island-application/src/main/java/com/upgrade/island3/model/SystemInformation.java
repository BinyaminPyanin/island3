package com.upgrade.island3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * SystemInformation
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@Schema(description = "System information.")
@Data
public class SystemInformation {
    @Schema(description = "The version of UpgradeIsland application.")
    private String version = "";
    @JsonProperty("build-timestamp")
    @Schema(name = "build-timestamp", description = "The build timestamp of the current version.")
    private String buildTimestamp;
    @JsonProperty("git-hash")
    @Schema(name = "git-hash", description = "The git hash of the current version.")
    private String gitHash;
}
