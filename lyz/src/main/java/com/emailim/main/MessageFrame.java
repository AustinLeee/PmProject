package com.emailim.main;

import com.emailim.cofig.Account;
import com.emailim.util.DateUtil;
import com.emailim.util.ReceiveThread;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MessageFrame
  extends JFrame
{
  private JPanel contentPane;
  private static JTextPane messagePane;
  private static JEditorPane sentPane;
  private static EmailUtil emailUtil;
  private JButton button_1;
  private static StringBuffer sff = new StringBuffer();
  
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          MessageFrame frame = new MessageFrame();
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
  
  public MessageFrame()
  {
    emailUtil = new EmailUtil();
    setDefaultCloseOperation(3);
    setBounds(100, 100, 542, 506);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(this.contentPane);
    this.contentPane.setLayout(null);
    
    messagePane = new JTextPane();
    messagePane.setContentType("text/html");
    messagePane.setEditable(false);
    JScrollPane scrollPane1 = new JScrollPane(messagePane);
    scrollPane1.setBounds(10, 10, 506, 290);
    this.contentPane.add(scrollPane1);
    
    sentPane = new JEditorPane();
    JScrollPane scrollPane = new JScrollPane(sentPane);
    scrollPane.setBounds(10, 320, 506, 110);
    this.contentPane.add(scrollPane);
    JButton button = new JButton("关闭");
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });
    button.setBounds(388, 435, 58, 27);
    this.contentPane.add(button);
    
    this.button_1 = new JButton("发送");
    this.button_1.setEnabled(false);
    this.button_1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (MessageDialog.getAccount() != null) {
          try
          {
            String newtext = MessageFrame.sentPane.getText();
            MessageFrame.emailUtil.send(newtext);
            String sentUser = "<span style=\"color:#6B8E23;\">" + MessageDialog.getAccount().getName() + "&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtil.dateToStr(new Date()) + "</span><br/>";
            MessageFrame.sff.append(sentUser).append(newtext).append("<br/><br/>");
            MessageFrame.messagePane.setText(MessageFrame.sff.toString());
            MessageFrame.sentPane.setText("");
          }
          catch (Exception e1)
          {
            e1.printStackTrace();
          }
        } else {
          JOptionPane.showMessageDialog(null, "请先配置邮箱信息！", "错误", 2);
        }
      }
    });
    this.button_1.setBounds(458, 435, 58, 27);
    this.contentPane.add(this.button_1);
    
    JButton button_2 = new JButton("配置");
    button_2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageDialog d = new MessageDialog(MessageFrame.messagePane, MessageFrame.emailUtil, MessageFrame.sff);
        d.setModal(true);
        d.setVisible(true);
      }
    });
    button_2.setBounds(10, 431, 52, 30);
    this.contentPane.add(button_2);
    
    JButton button_3 = new JButton("登录");
    button_3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageFrame.this.button_1.setEnabled(true);
        ReceiveThread rt = new ReceiveThread(MessageFrame.messagePane, MessageFrame.emailUtil, MessageFrame.sff);
        rt.start();
      }
    });
    button_3.setBounds(69, 433, 52, 30);
    this.contentPane.add(button_3);
  }
}