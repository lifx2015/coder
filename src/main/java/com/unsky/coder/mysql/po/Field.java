package com.unsky.coder.mysql.po;

/**
 * 
 * �ֶ��� ���ڴ洢���ݿ��е���������
 * 
 * @project coder
 * @package com.unsky.coder.mysql.po
 * @class Filed
 * @author unsky
 * @date 2016-6-29 9:40:26
 * @version 1.0
 * @copyRight ɽ���������缼�����޹�˾
 * @webSite http://www.kt56.com
 */
public class Field {
	/**
	 * �ֶ���
	 */
	private String name;
	/**
	 * ����
	 */
	private String type;
	/**
	 *  ��ע
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
