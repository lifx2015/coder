package com.unsky.coder.mysql.po;

import java.util.List;

public class MysqlGenConfig {
	private String driverClass;
	private String url;
	private String user;
	private String pwd;
	private String basePath;
	private String packageInfo;
	private String databaseName;
	private String autoimport;
	private List<Table> tables;
	private boolean allTable;
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	public boolean isAllTable() {
		return allTable;
	}
	public void setAllTable(boolean allTable) {
		this.allTable = allTable;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getAutoimport() {
		return autoimport;
	}
	public void setAutoimport(String autoimport) {
		this.autoimport = autoimport;
	}
	
	
	
}
