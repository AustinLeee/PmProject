package com.emailim.cofig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ObjectUtil
{
  private JAXBContext jc;
  private String path = null;
  
  public ObjectUtil()
  {
    try
    {
      this.path = "account.xml";
      System.out.println(this.path);
      this.jc = JAXBContext.newInstance("com.emailim.cofig");
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, "出错:" + e.getMessage());
      e.printStackTrace();
    }
    System.out.println("1===:" + this.path);
  }
  
  public Account open()
  {
    Account account = null;
    try
    {
      Unmarshaller u = this.jc.createUnmarshaller();
      File f = new File(this.path);
      if (f.exists())
      {
        InputStream in = new FileInputStream(f);
        if (in != null) {
          account = (Account)u
            .unmarshal(in);
        }
        in.close();
      }
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, "出错:" + e.getMessage());
      e.printStackTrace();
    }
    if (account == null)
    {
      ObjectFactory of = new ObjectFactory();
      account = of.createAccount();
    }
    return account;
  }
  
  public void save(Object element)
  {
    try
    {
      Marshaller m = this.jc.createMarshaller();
      
      OutputStream os = new FileOutputStream(new File(this.path));
      m.marshal(element, os);
      os.close();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, "出错:" + e.getMessage());
      e.printStackTrace();
    }
  }
}

