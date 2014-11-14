package com.dbdoc.test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.dbdoc.db.model.Table;
import com.dbdoc.db.model.provider.TableProvider;

public class TableProviderTest {
	public static void main(String args[]) throws SQLException{
		List tables = TableProvider.getInstance().getAllTables();
		for(int i = 0; i < tables.size(); i++ ) {
			Table table=(Table)tables.get(i);
			String tableName = table.getName();
			System.out.println("��"+i+":\""+tableName+"\":");
			System.out.println("������"+i+":\""+table.getType()+"\":");
//			if(null!=table.getColumns()){
//				System.out.print("�����ֶηֱ�Ϊ:(");
//				for (Iterator k = table.getColumns().iterator(); k.hasNext();) {
//					Column column = (Column) k.next();
//					System.out.print("\""+column.get_sqlName()+"-"+column.get_sqlTypeName()+"("+column.get_size()+")"+"\"��");
//				}
//				System.out.print(")\n");
//			}
		}
		Logger.global.info("--------------------��ӡ��ѡ�����  END----------------------");
	}
}
