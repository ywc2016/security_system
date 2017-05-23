package edu.buaa.sem.validate.model;

import java.util.List;

/**
 * Created by lonecloud on 17/2/6.
 */
public class GenerateImageGroup {
	private ImageGroup keyGroup;

	private List<ImageGroup> groups;

	public GenerateImageGroup(ImageGroup keyGroup, List<ImageGroup> groups) {
		this.keyGroup = keyGroup;
		this.groups = groups;
	}

	public ImageGroup getKeyGroup() {
		return keyGroup;
	}

	public void setKeyGroup(ImageGroup keyGroup) {
		this.keyGroup = keyGroup;
	}

	public List<ImageGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ImageGroup> groups) {
		this.groups = groups;
	}
}
