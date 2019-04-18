package com.testapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/parser")
public class ParseData extends HttpServlet {
  private Logger logger = Logger.getLogger(String.valueOf(ParseData.class));

  @EJB
  private ParseUtil util;


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    logger.info("ParseDataServlet started. "+ LocalTime.now());

    String text1 = req.getParameter("name").trim();
    InputStream inputStream = new ByteArrayInputStream(text1.getBytes());
    Document document = null;
    Element element = null;
    NodeList nodeList = null;
    try {
      document = util.getDocumentBuilder().parse(inputStream);
      element = document.getDocumentElement();
      nodeList = element.getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
    } catch (SAXException e) {
      logger.warning("Data Parse error! "+e.getMessage());
    }

    Map<String, String > params = new HashMap<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      String strPar = "";
      if (nodeList.item(i).hasAttributes()) {
        try {
          strPar = nodeList.item(i).getAttributes().getNamedItem("type").toString();
        }catch (Exception e){
//             empty
        }
      }
      String strData = nodeList.item(i).getNodeName()+strPar;
      params.put(strData, nodeList.item(i).getTextContent() );

    }

    logger.info(params.entrySet().stream().map(entry -> entry.getKey() + " : " + entry.getValue()).collect(Collectors.joining(", ","{ ", " }")));


    resp.setContentType("plain/text");
    PrintWriter out = resp.getWriter();
    out.println("{ \"token\":"+params.get("token")+", "+
        "\"cardNumber\":"+params.get("cardNumber")+", "+
        "\"requestId\":"+params.get("requestId")+", "+
        "\"amount\":"+params.get("amount")+", "+
        "\"currency\":"+params.get("currency")+", "+
        "\"account-source\":"+params.get("uts:accounttype=\"source\"")+", "+
        "\"account-destination\":"+params.get("uts:accounttype=\"destination\"")+
        " }");

    params.clear();
  }
}
