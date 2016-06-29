package com.unsky.coder.mysql.po;

/**
 * 
 * 字段类 用于存储数据库中的数据类型
 * 
 * @project coder
 * @package com.unsky.coder.mysql.po
 * @class Filed
 * @author unsky
 * @date 2016-6-29 9:40:26
 * @version 1.0
 * @copyRight 山东阿帕网络技术有限公司
 * @webSite http://www.kt56.com
 */
public class Field {
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 *  备注
	 */
	private String commment;
	
	public Field(String name, String type) {
		this.name = name;
		this.type = type;
	}
	public Field(String name, String type, String commment) {
		this.name = name;
		this.type = type;
		this.commment = commment;
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
	public String getCommment() {
		return commment;
	}
	public void setCommment(String commment) {
		this.commment = commment;
	}
	
}
