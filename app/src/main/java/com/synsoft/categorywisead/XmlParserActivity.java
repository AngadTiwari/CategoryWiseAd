package com.synsoft.categorywisead;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);

        new GetStateListAsync().execute();
    }

    public boolean getStates()
    {
        String METHOD_NAME = "GetStateList"; // our
        String SOAP_ACTION = "http://emstv.in/Service1.asmx/GetStates";

        String URL = "http://emstv.in/Service1.asmx";
        String DATABASEURL = "http://touchmee.in/touchmeeservice.asmx";
        String NAMESPACE = "http://tempuri.org/";
        String result = "";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(
                    SoapEnvelope.VER12);
            envelop.dotNet = true;
            envelop.setOutputSoapObject(request);
            HttpTransportSE aht = new HttpTransportSE(URL);
            aht.call(SOAP_ACTION, envelop);
            SoapPrimitive response = (SoapPrimitive) envelop.getResponse();
            result = response.toString();
            Log.e("RETURNED RESULT", "" + result);
            parseSOAPResponse(result,false);
            return true;
            /*parser = new Parser(ctx);
            parser.parseStates(result);*/

        }
        catch (Exception e)
        {
            result = e.toString();
            Log.e("RETURNED RESULT", "" + result);

            return false;
        }
    }

    public void parseSOAP(String xmlString)
    {
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        Document doc = getDomElement(xmlString); // getting DOM element

        NodeList nl = doc.getElementsByTagName("sVideoList");
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put("ID", getValue(e, "ID"));
            map.put("Title", getValue(e, "Title"));
            map.put("Rate", "Rs." + getValue(e, "Rate"));
            map.put("Desc", getValue(e, "Desc"));

            // adding HashList to ArrayList
            menuItems.add(map);
        }

        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, menuItems,
                R.layout.list_itme,
                new String[] { "Title", "Desc", "Rate" }, new int[] {
                R.id.textView, R.id.textView2, R.id.textView3 });

        ((ListView)findViewById(R.id.list)).setAdapter(adapter);
    }

    public void parseSOAPResponse(String xmlString,boolean hasToRemoveXMLTag)
    {
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        String xml="";
        if(hasToRemoveXMLTag)
        {
            xml = "<string xmlns=\"http://tempuri.org/\">\n" +
                "<?xml version=\"1.0\"?>" +
                "<ArrayOfSVideoList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
                "<sVideoList>" +
                "<ID>b9f84abd-7ad8-4b69-8641-f28d2b646b1c</ID>" +
                "<ImgUrl>http://www.emstv.in/Contents/Video/MO_VideoImages/Madhya_Pradesh_bhopal__228432093.jpg</ImgUrl>" +
                "<TitleImgUrl>http://www.emstv.in/Contents/TitleImage/Tb9f84abd-7ad8-4b69-8641-f28d2b646b1c.jpg</TitleImgUrl>" +
                "<Title>खेल की ..</Title>" +
                "<Desc>1&lt;br&gt;इंग्लैंड के ..</Desc>" +
                "<Rate>0</Rate>" +
                "<View>0</View>" +
                "</sVideoList><" +
                "PageNumber>1</PageNumber><TotalVideos>100</TotalVideos></ArrayOfSVideoList>\n" +
                "</string>"; // getting XML
            xml=xml.replace("<?xml version=\"1.0\"?>","");
        }

        Document doc = getDomElement(hasToRemoveXMLTag?xml:xmlString); // getting DOM element

        NodeList nl = doc.getElementsByTagName("sVideoList");
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put("ID", getValue(e, "ID"));
            map.put("Title", getValue(e, "Title"));
            map.put("Rate", "Rs." + getValue(e, "Rate"));
            map.put("Desc", getValue(e, "Desc"));

            // adding HashList to ArrayList
            menuItems.add(map);
        }

        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, menuItems,
                R.layout.list_itme,
                new String[] { "Title", "Desc", "Rate" }, new int[] {
                R.id.textView, R.id.textView2, R.id.textView3 });

        ((ListView)findViewById(R.id.list)).setAdapter(adapter);
    }

    public class GetStateListAsync extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return getStates();
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {

            if(!aVoid)
                parseSOAPResponse(null,true);
        }
    }

    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
