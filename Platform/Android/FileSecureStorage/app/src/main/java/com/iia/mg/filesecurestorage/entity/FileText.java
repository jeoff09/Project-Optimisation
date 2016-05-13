package com.iia.mg.filesecurestorage.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ProtoConcept GJ on 12/05/2016.
 */
public class FileText {

    private String name;
    private String content;
    private String path;
    private String updated_at;

    public FileText( String content, String name, String path, String updated_at) {
        this.content = content;
        this.name = name;
        this.path = path;
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
