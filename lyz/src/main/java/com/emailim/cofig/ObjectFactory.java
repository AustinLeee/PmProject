package com.emailim.cofig;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
  public Account createAccount()
  {
    return new Account();
  }
}
