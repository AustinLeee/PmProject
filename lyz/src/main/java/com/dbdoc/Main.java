package com.dbdoc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbdoc.db.model.provider.TableProvider;
import com.dbdoc.utils.FreemarkerUtils;


public class Main {
	public static final String TEMPLEATE_DEFAUTL="template/dbdoc_siample.xml";
	public static final String OUTER_default="d:\\tmp\\dbdoc_siample.doc";
	
	@SuppressWarnings("rawtypes")
	public static void main(String args[]) throws IOException{
		Map propMap=new HashMap();
		try {
			List tables = TableProvider.getInstance().getAllTables();
			propMap.put("tableList", tables);
			FreemarkerUtils.writeTemplateToFile(Main.TEMPLEATE_DEFAUTL, propMap, Main.OUTER_default);
		} catch (SQLException e) {
		}
		Runtime.getRuntime().exec("cmd.exe /c start c:\\temp-output");
	}
}
