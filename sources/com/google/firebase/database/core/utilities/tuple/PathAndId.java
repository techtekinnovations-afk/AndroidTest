package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;

public class PathAndId {
    private long id;
    private Path path;

    public PathAndId(Path path2, long id2) {
        this.path = path2;
        this.id = id2;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.id;
    }
}
