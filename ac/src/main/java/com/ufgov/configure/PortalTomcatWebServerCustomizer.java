package com.ufgov.configure;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class PortalTomcatWebServerCustomizer
  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

  /* (non-Javadoc)
   * <p>Title: customize</p>    
   * <p>Description: </p>    
   * @param factory    
   * @see org.springframework.boot.web.server.WebServerFactoryCustomizer#customize(org.springframework.boot.web.server.WebServerFactory)   
   */
  @Override
  public void customize(ConfigurableServletWebServerFactory factory) {
    ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
      @Override
      public void customize(Connector connector) {
        connector.setAttribute("relaxedQueryChars", "[]|{}^&#x5c;&#x60;&quot;&lt;&gt;");
        connector.setAttribute("relaxedPathChars", "[]|");
      }
    });
  }
}