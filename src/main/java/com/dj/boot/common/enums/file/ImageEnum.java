package com.dj.boot.common.enums.file;

public enum ImageEnum {
    JPEG("jpeg"),
    JPG("jpg"),
    JPE("jpe"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp");

    private String name;

    ImageEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
