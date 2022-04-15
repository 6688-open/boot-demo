package com.dj.boot.common.enums.file;

/**
 * 文件类型主要划分为图片和文件两种，判断的依据MIME Type,
 * 在这里只设置了image与application两种，例如（application/zip,image/*,application/pdf,application/msword）
 * <p/>
 */
public enum FileType {

    IMAGE(1, "image"),

    /**
     * MultipartFile.getContentType是application和application
     */
    FILE(2, "appli");

    private int index;

    private String name;

    FileType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
