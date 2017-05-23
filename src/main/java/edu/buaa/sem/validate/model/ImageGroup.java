package edu.buaa.sem.validate.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lonecloud on 17/2/6.
 */
public class ImageGroup {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 数量
	 */
	private int count;
	/**
	 * 位置
	 */
	private Set<String> paths = new HashSet<>();

	public ImageGroup(String name, int count, String... path) {
		this.name = name;
		this.count = count;
		paths.addAll(Arrays.asList(path));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Set<String> getPaths() {
		return paths;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}
}
