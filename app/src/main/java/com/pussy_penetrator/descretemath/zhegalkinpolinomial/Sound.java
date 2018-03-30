package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

/**
 * Created by Sex_predator on 13.02.2016.
 */
public class Sound {

    private String  mAssetPath;
    private String  mName;
    private Integer mId;

    public Sound(String folderPath, String fileName) {
        mName = fileName;
        mAssetPath = folderPath + "/" + fileName;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

}
