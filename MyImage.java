package com.zqw;

public class MyImage {
	
	private String path;
	private String name;
	private String type;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MyImage [path=" + path + ", name=" + name + ", type=" + type + "]";
	}
}
