package com.synsoft.categorywisead;

import android.content.Intent;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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

        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
        String xml = "<string xmlns=\"http://tempuri.org/\">\n" +
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

        Document doc = getDomElement(xml); // getting DOM element

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
