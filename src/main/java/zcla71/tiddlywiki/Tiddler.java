package zcla71.tiddlywiki;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Tiddler {
    // System tiddlers (kinda "hidden")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmssSSS")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmssSSS")
    private LocalDateTime modified;
    private String tags;
    private String text;
    private String title;

    // System tiddlers (the ones that show up at an empty file property list)
    private String author;
    @JsonProperty("core-version")
    private String coreVersion;
    @JsonProperty("current-tiddler")
    private String currentTiddler;
    private String dependents;
    private String description;
    private String library;
    private String list;
    private String name;
    @JsonProperty("plugin-priority")
    private String pluginPriority;
    @JsonProperty("plugin-type")
    private String pluginType;
    private String stability;
    private String type;
    private String version;

    // Custom properties
    private Map<String, String> customProperties;

    @JsonAnySetter
    public void setCustomProperties(String key, String value) {
        customProperties.put(key,value);
    }

    @JsonAnyGetter
    public Map<String, String> getCustomProperties(){
        return customProperties;
    }

    public Tiddler() {
        this.customProperties = new HashMap<>();
    }

    public Tiddler(String title) {
        this();
        this.title = title;
    }
}
