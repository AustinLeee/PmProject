package com.emailim.main;
import com.emailim.cofig.Account;
import com.emailim.cofig.ObjectUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class MessageDialog
  extends JDialog
{
  private final JPanel contentPanel = new JPanel();
  private JTextField receiveHost;
  private JTextField sentHost;
  private JTextField email;
  private JTextField user;
  private JTextField toEmail;
  private JPasswordField password;
  private static Account account;
  
  public MessageDialog(JTextPane messagePane, EmailUtil emailUtil, StringBuffer sff)
  {
    if (account == null)
    {
      ObjectUtil ou = new ObjectUtil();
      account = ou.open();
    }
    setBounds(100, 100, 450, 330);
    getContentPane().setLayout(new BorderLayout());
    this.contentPanel.setForeground(Color.LIGHT_GRAY);
    this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(this.contentPanel, "Center");
    this.contentPanel.setLayout(null);
    int y = 10;
    JLabel label = new JLabel("发送人服务器信息");
    label.setBounds(72, y, 150, 15);
    this.contentPanel.add(label);
    JSeparator se = new JSeparator();
    se.setBounds(180, y + 8, 200, 2);
    this.contentPanel.add(se);
    
    y = 30;
    label = new JLabel("接收邮件(POP3)：");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.receiveHost = new JTextField(account.getReceiveHost());
    this.receiveHost.setBounds(200, y, 150, 27);
    this.contentPanel.add(this.receiveHost);
    this.receiveHost.setColumns(10);
    
    y += 30;
    label = new JLabel("发送邮件(SMTP)：");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.sentHost = new JTextField(account.getSendHost());
    this.sentHost.setBounds(200, y, 150, 27);
    this.contentPanel.add(this.sentHost);
    this.sentHost.setColumns(10);
    
    y += 30;
    label = new JLabel("邮箱地址:");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.email = new JTextField(account.getFromEmail());
    this.email.setBounds(200, y, 150, 27);
    this.contentPanel.add(this.email);
    this.email.setColumns(10);
    
    y += 30;
    label = new JLabel("帐户名:");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.user = new JTextField(account.getName());
    this.user.setBounds(200, y, 150, 27);
    this.contentPanel.add(this.user);
    this.user.setColumns(10);
    
    y += 30;
    label = new JLabel("密码:");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.password = new JPasswordField(account.getPassword());
    this.password.setBounds(200, y, 150, 27);
    this.password.setColumns(10);
    this.contentPanel.add(this.password);
    
    y += 30;
    label = new JLabel("接收人邮箱地址");
    label.setBounds(72, y, 150, 15);
    this.contentPanel.add(label);
    se = new JSeparator();
    se.setBounds(170, y + 8, 200, 2);
    this.contentPanel.add(se);
    
    y += 30;
    label = new JLabel("邮箱地址:");
    label.setBounds(90, y, 150, 15);
    this.contentPanel.add(label);
    
    this.toEmail = new JTextField(account.getToEmail());
    this.toEmail.setBounds(200, y, 150, 27);
    this.contentPanel.add(this.toEmail);
    this.toEmail.setColumns(10);
    
    y += 40;
    JButton save = new JButton("保存");
    save.setBounds(150, y, 60, 30);
    save.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageDialog.account.setReceiveHost(MessageDialog.this.receiveHost.getText());
        MessageDialog.account.setSendHost(MessageDialog.this.sentHost.getText());
        MessageDialog.account.setFromEmail(MessageDialog.this.email.getText());
        MessageDialog.account.setName(MessageDialog.this.user.getText());
        MessageDialog.account.setPassword(MessageDialog.this.password.getText());
        MessageDialog.account.setToEmail(MessageDialog.this.toEmail.getText());
        ObjectUtil ou = new ObjectUtil();
        ou.save(MessageDialog.account);
        MessageDialog.this.dispose();
      }
    });
    this.contentPanel.add(save);
    
    JButton cancel = new JButton("关闭");
    cancel.setBounds(220, y, 60, 30);
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageDialog.this.dispose();
      }
    });
    this.contentPanel.add(cancel);
  }
  
  public static Account getAccount()
  {
    return account;
  }
  
  public static void setAccount(Account account)
  {
    account = account;
  }
}

