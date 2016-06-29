package start;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
 * 
 * @project coder
 * @package com.unsky.coder.mysql
 * @class Test
 * @author unsky
 * @date 2016-6-29 8:25:32
 * @version 1.0
 * @copyRight 山东阿帕网络技术有限公司
 * @webSite http://www.kt56.com
 */
public class StartGenClass {

	private static ApplicationContext ac;

	public static String switchJavaType(int sqlType) {
		String typeName = "String";
		switch (sqlType) {
		case Types.INTEGER:
			typeName = "Integer";
			break;
		case Types.VARCHAR:
			typeName = "String";
		case Types.BIGINT:
			typeName = "Long";
		case Types.DECIMAL:
			typeName = "Double";
		case Types.DATE:
			typeName = "Date";
		default:
			typeName = "String";
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
			for (Table table : config.getTables()) {
				ResultSet rs = meta.getColumns(null, null, table.getTableName(), null);
				String packageinfo = config.getPackageInfo();
				System.out.println(packageinfo.replaceAll("\\.", "/"));
				StringBuffer content = new StringBuffer(
						"package " + packageinfo + ";\npublic class " + table.getEntityName() + "{\n");
				List<Field> fields = new ArrayList<Field>();
				while (rs.next()) {
					String type = switchJavaType(rs.getInt("DATA_TYPE"));
					String name = UtilString.transform(rs.getString("COLUMN_NAME"));
					String comment = rs.getString("REMARKS");

					content.append("\t/**\n\t *").append(comment).append("\n\t */\n");
					content.append("\tprivate " + type).append(" ").append(name).append(";\n");
					fields.add(new com.unsky.coder.mysql.po.Field(name, type));
				}
				for (Field field : fields) {
					content.append("\tpublic void set").append(UtilString.toUpperCaseFirstOne(field.getName()))
							.append("(").append(field.getType()).append(" ").append(field.getName())
							.append(") {\n\t\tthis.").append(field.getName()).append("=").append(field.getName())
							.append(";\n\t}\n");

					content.append("\tpublic ").append(field.getType()).append(" get")
							.append(UtilString.toUpperCaseFirstOne(field.getName())).append("(){\n\t\treturn this.")
							.append(field.getName()).append(";\n\t}\n");
				}
				content.append("}");
				System.out.println(content.toString());

				File file = new File(config.getBasePath() + packageinfo.replaceAll("\\.", "/") + "/"
						+ table.getEntityName() + ".java");
				BufferedWriter bWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
				bWriter.write(content.toString());
				bWriter.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
