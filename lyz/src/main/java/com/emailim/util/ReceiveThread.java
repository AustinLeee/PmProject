package com.emailim.util;

import com.emailim.main.EmailUtil;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class ReceiveThread
  extends Thread
{
  private JTextPane messagePane;
  private EmailUtil emailUtil;
  private StringBuffer sff;
  
  public ReceiveThread(JTextPane messagePane, EmailUtil emailUtil, StringBuffer sff)
  {
    this.messagePane = messagePane;
    this.emailUtil = emailUtil;
    this.sff = sff;
  }
  
  public void run()
  {
    try
    {
      boolean b = true;
      while (b)
      {
        System.out.println("正在获取邮件 ");
        b = this.emailUtil.receive(this.messagePane, this.sff);
        if (!b) {
          JOptionPane.showMessageDialog(null, "登录邮箱出错，请检查邮箱配置信息！");
        }
        sleep(2000L);
      }
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      System.out.println("Exception: " + e.getMessage());
    }
  }
}