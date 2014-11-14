package com.dbdoc.db.proxool;

import java.sql.*;
import java.util.logging.Logger;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import java.io.*;

public class DBConnectionPoolManager {  
	
	private static Logger log = Logger.getAnonymousLogger(); 
	private static DBConnectionPoolManager dbcpm = null;
    private Connection con = null;
    
    /***************************************************************************
	 * ���캯��
	 * 
	 * @return DBConnectionPoolManager
	 */
	private DBConnectionPoolManager() {
		log.info(getClass().getResource("/").getPath());
		InputStream is = getClass().getResourceAsStream("/jdbc_proxool.xml");
		try {
			JAXPConfigurator.configure(new InputStreamReader(is), false);
		} catch (Exception e) {
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
	}
    
    /***************************************************************************
	 * ��ȡʵ�� ��̬
	 * 
	 * @return DBConnectionPoolManager
	 */
    public static synchronized DBConnectionPoolManager getInstance(){
	   if(null==dbcpm)
	        dbcpm =new DBConnectionPoolManager();
	    return dbcpm;
	}
    
    /***
     * ��ȡ����
     * @return
     */
    public Connection getConnection(){
        try{ 
        	con = DriverManager.getConnection("proxool.gxgljsyh");
        }catch(Exception e){
        	log.info("-<<<<<<���ݿ�����ʧ��,����proxool.xml�ļ������Ƿ���ȷ!>>>>>>");
         	log.info(e.getMessage());
        }
        return con;
    }
  
    public static void main(String[] args){
    	try{
    		if (DBConnectionPoolManager.getInstance().getConnection()!=null){
    			log.info("- <<<<<<���ӳɹ���>>>>>>");
        	}else{
        		log.info("-<<<<<<����ʧ�ܣ�>>>>>>");
        	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    }
} 

