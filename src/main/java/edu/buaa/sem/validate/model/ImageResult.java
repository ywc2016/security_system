package edu.buaa.sem.validate.model;

import java.util.Set;

/**
 * Created by lonecloud on 17/2/6.
 */
public class ImageResult {
    /**
     * 生成文件名
     */
    private String name;
    /**
     * 第几张图片
     */
    private Set<Integer> keySet;
    /**
     * 图片唯一标示
     */
    private String uniqueKey;
    /**
     * 选择什么东西
     */
    private String tip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<Integer> keySet) {
        this.keySet = keySet;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
