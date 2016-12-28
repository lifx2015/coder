package start;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unsky.coder.mysql.po.Field;
import com.unsky.coder.mysql.po.MysqlGenConfig;
import com.unsky.coder.mysql.po.Table;
import com.unsky.util.UtilString;

/**
 * 根据数据库自动生成entity 类型，变量名，备注，setter/getter
 * v1.1 运通表处理 ,主要一下修改
 * 1>code自动生成
 * 2>delFlag 如果为空默认为1
 * 3>所有date类型 默认值为当前时间
 * @project coder
 * @package com.unsky.coder.mysql
 * @class Test
 * @author unsky
 * @date 2016-6-29 8:25:32
 * @version 1.1
 * @copyRight 山东阿帕网络技术有限公司
 * @webSite http://www.kt56.com
 */
public class StartGenYTClass {

	private static ApplicationContext ac;

	public static String switchJavaType(int sqlType) {
		String typeName = "String";
		switch (sqlType) {
		case Types.INTEGER:
			typeName = "Integer";
			break;
		case Types.VARCHAR:
			typeName = "String";
			break;
		case Types.BIGINT:
			typeName = "Long";
			break;
		case Types.DECIMAL:
			typeName = "Double";
			break;
		case Types.DATE:
			typeName = "Date";
			break;
		case Types.TIME:
			typeName = "Date";
			break;
		case Types.TIMESTAMP:
			typeName = "Date";
			break;
		case Types.LONGVARCHAR:
			typeName = "String";
			break;
		default:
			typeName = "String";
			System.err.println(sqlType);
			break;
		}
		return typeName;
	}

	public static void main(String[] args) {
		try {
			ac = new ClassPathXmlApplicationContext("context.xml");
			MysqlGenConfig config = (MysqlGenConfig) ac.getBean("config");
			Class.forName(config.getDriverClass());
			Connection connection = DriverManager.getConnection(config.getUrl() + "&characterEncoding=utf-8",
					config.getUser(), config.getPwd());
			DatabaseMetaData meta = connection.getMetaData();
			if(config.isAllTable())
			{
				List<Table> tables=new ArrayList<Table>();
				ResultSet as = connection.createStatement().executeQuery("SELECT * FROM information_schema.tables where TABLE_SCHEMA='"+config.getDatabaseName()+"'");
				while(as.next())
				{
					Table t=new Table();
					t.setEntityName(UtilString.transform(as.getString("TABLE_NAME")).substring(2)+"Entity");
					t.setTableName(as.getString("TABLE_NAME"));
					t.setRemark(as.getString("TABLE_COMMENT"));
					tables.add(t);
				}
				config.setTables(tables);
			}
			
			
			for (Table table : config.getTables()) {
				ResultSet rs = meta.getColumns(null, null, table.getTableName(), null);
				String packageinfo = config.getPackageInfo();
				StringBuffer header = new StringBuffer(
						"package " + packageinfo + ";\n\n");
				
				String[] autimports = config.getAutoimport().split(",");
				for (String autimport : autimports) {
					header.append("import ").append(autimport).append(";\n");
					
				}
				StringBuffer content = new StringBuffer("/** \n\t").append(table.getRemark()).append("\n*/\n");
				content.append("@Entity\n@Table(name = \""+table.getTableName()+"\")\n");
				content.append("public class " + table.getEntityName() + " extends IdEntity {\n");
				content.append("\tprivate static final long serialVersionUID = 1L;\n");
				
				List<Field> fields = new ArrayList<Field>();
				boolean isDateImp=false;
				while (rs.next()) {
					String type = switchJavaType(rs.getInt("DATA_TYPE"));
					
					if(!isDateImp&&"Date".equals(type))
					{
						isDateImp=true;
						header.append("import java.util.Date;\n");
					}
					
					
					String name = UtilString.transform(rs.getString("COLUMN_NAME"));
					String comment = rs.getString("REMARKS");
					if (name.equals("id")) {
						continue;
					}
					content.append("\t/**\n\t *").append(comment).append("\n\t */\n");
					content.append("\tprivate " + type).append(" ").append(name).append(";\n");
					fields.add(new Field(name, type));
				}
				for (Field field : fields) {
					if (field.getName().equals("id")) {
						continue;
					}
					content.append("\tpublic void set").append(UtilString.toUpperCaseFirstOne(field.getName()))
							.append("(").append(field.getType()).append(" ").append(field.getName())
							.append(") {\n\t\tthis.").append(field.getName()).append("=").append(field.getName())
							.append(";\n\t}\n");

					content.append("\tpublic ").append(field.getType()).append(" get")
							.append(UtilString.toUpperCaseFirstOne(field.getName())).append("(){\n\t\treturn ");
							if(field.getName().contains("delFlag")){
								content.append(field.getName()+"==null?Constants.NOT_DEL:"+field.getName());
							}else if(!field.getName().toLowerCase().contains("code")&&(field.getName().toLowerCase().contains("date")||
									field.getName().toLowerCase().contains("time"))){
								content.append(field.getName()+"==null?new Date():"+field.getName());
							}else if(field.getName().toLowerCase().equals("code")){
								content.append(field.getName()+"==null?UUIDUtil.getUUID32():"+field.getName());
							}else{
								content.append(field.getName());
							}
							content.append(";\n\t}\n");
				}
				content.append("}");
				System.out.println(header.append(content).toString());
/*
				File file = new File(config.getBasePath() + packageinfo.replaceAll("\\.", "/") + "/"
						+ table.getEntityName() + ".java");*/
//				BufferedWriter bWriter = new BufferedWriter(
//						new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
//				bWriter.write(header.append(content).toString());
//				bWriter.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
