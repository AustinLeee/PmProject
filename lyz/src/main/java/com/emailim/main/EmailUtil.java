package com.emailim.main;

import com.emailim.cofig.Account;
import com.emailim.util.DateUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

public class EmailUtil
{
  private Account accout;
  private static Session session;
  private static Store store;
  private Date latestSentDate;
  
  public EmailUtil()
  {
    if (session == null) {
      initSession();
    }
  }
  
  public void send(String mail_body)
  {
    try
    {
      if (session == null)
      {
        boolean b = initSession();
        if (!b) {
          return;
        }
      }
      MimeMessage mimeMsg = new MimeMessage(session);
      mimeMsg.setSubject("即时信息回复");
      Multipart mp = new MimeMultipart();
      BodyPart bp1 = new MimeBodyPart();
      bp1.setContent(mail_body, "text/html;charset=UTF-8");
      mp.addBodyPart(bp1);
      mimeMsg.setContent(mp);
      mimeMsg.setFrom(new InternetAddress(this.accout.getFromEmail()));
      mimeMsg.setRecipients(Message.RecipientType.TO, 
        InternetAddress.parse(this.accout.getToEmail()));
      mimeMsg.saveChanges();
      Transport transport = session.getTransport("smtp");
      transport.connect(this.accout.getSendHost(), 
        this.accout.getName(), this.accout.getPassword());
      transport.sendMessage(mimeMsg, mimeMsg
        .getRecipients(Message.RecipientType.TO));
      transport.close();
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, "发送出错:" + ex.getMessage());
      System.out.println("发送邮件出错:");
      ex.printStackTrace();
    }
  }
  
  public boolean receive(JEditorPane receivePane, StringBuffer sff)
  {
    try
    {
      if (session == null)
      {
        boolean b = initSession();
        if (!b) {
          return false;
        }
      }
      if ((store == null) || (!store.isConnected()))
      {
        URLName urln = new URLName("pop3", this.accout.getReceiveHost(), 110, null, 
          this.accout.getName(), this.accout.getPassword());
        store = session.getStore(urln);
        store.connect();
      }
      Folder folder = store.getFolder("INBOX");
      folder.open(1);
      System.out.println("邮件数:" + folder.getMessageCount());
      if (this.latestSentDate == null) {
        this.latestSentDate = DateUtil.parseDate("2010-12-10 23:00:00");
      }
      Address fromEmail = new InternetAddress(this.accout.getToEmail());
      SearchTerm term1 = new FromTerm(fromEmail);
      SearchTerm st = new SentDateTerm(5, this.latestSentDate);
      SearchTerm term = new AndTerm(term1, st);
      Message[] msgs = folder.search(term);
      System.out.println("符合条件邮件数:" + msgs.length);
      if (msgs.length > 0)
      {
        for (int i = 0; i < msgs.length; i++)
        {
          Message m = msgs[i];
          String msg = "";
          try
          {
            if (m.isMimeType("multipart/*"))
            {
              Multipart mp = (Multipart)m.getContent();
              Part part = mp.getBodyPart(0);
              msg = (String)part.getContent();
            }
            else
            {
              msg = (String)m.getContent();
            }
            this.latestSentDate = m.getSentDate();
            String from = getFrom(m);
            String sentUser = "<span style=\"color:#1E90FF;\">" + from + "&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtil.dateToStr(this.latestSentDate) + "</span><br/>";
            sff.append(sentUser);
            sff.append(msg + "<br/><br/>");
          }
          catch (Exception e)
          {
            System.out.println("读取第" + i + "份邮件出错");
            e.printStackTrace();
          }
        }
        receivePane.setText(sff.toString());
      }
    }
    catch (Exception e)
    {
      sff.append("<font color=\"red\">接收邮件出错:" + e.getMessage() + "</font><br/>");
      receivePane.setText(sff.toString());
      System.out.println("接收邮件出错:");
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  private boolean initSession()
  {
    if (MessageDialog.getAccount() != null)
    {
      this.accout = MessageDialog.getAccount();
      Properties props = new Properties();
      props.put("mail.smtp.host", this.accout.getSendHost());
      props.put("mail.smtp.auth", "true");
      session = Session.getDefaultInstance(props, null);
      return true;
    }
    return false;
  }
  
  private String getFrom(Message mimeMessage)
    throws Exception
  {
    InternetAddress[] address = (InternetAddress[])mimeMessage.getFrom();
    String from = address[0].getAddress();
    if (from == null) {
      from = "";
    }
    String personal = address[0].getPersonal();
    if (personal == null) {
      personal = "";
    }
    String fromaddr = personal + "&nbsp;&nbsp;&lt;" + from + "&gt;";
    return fromaddr;
  }
  
  public void getMailContent(Part part, StringBuffer sff)
    throws Exception
  {
    String contenttype = part.getContentType();
    int nameindex = contenttype.indexOf("name");
    boolean conname = false;
    if (nameindex != -1) {
      conname = true;
    }
    System.out.println("CONTENTTYPE: " + contenttype);
    if ((part.isMimeType("text/plain")) && (!conname))
    {
      sff.append((String)part.getContent());
    }
    else if ((part.isMimeType("text/html")) && (!conname))
    {
      sff.append((String)part.getContent());
    }
    else if (part.isMimeType("multipart/*"))
    {
      Multipart multipart = (Multipart)part.getContent();
      int counts = multipart.getCount();
      for (int i = 0; i < counts; i++) {
        getMailContent(multipart.getBodyPart(i), sff);
      }
    }
    else if (part.isMimeType("message/rfc822"))
    {
      getMailContent((Part)part.getContent(), sff);
    }
  }
}
