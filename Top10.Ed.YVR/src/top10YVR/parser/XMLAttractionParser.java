package top10YVR.parser;

import org.xml.sax.SAXException;
import top10YVR.model.Guide;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class XMLAttractionParser implements IAttractionParser {
    private String sourceFileName;

    // EFFECTS: constructs parser for data in given source file
    public XMLAttractionParser(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public Guide parse() throws AttractionParsingException, IOException {

        Guide registry = new Guide();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            AttractionHandler ah = new AttractionHandler(registry);
            saxParser.parse(sourceFileName, ah);
        } catch (ParserConfigurationException e) {
            AttractionParsingException attractionParsingException = new AttractionParsingException("Parser configuration error");
            attractionParsingException.initCause(e);
            throw attractionParsingException;
        } catch (SAXException e) {
            AttractionParsingException attractionParsingException = new AttractionParsingException("SAX parser error");
            attractionParsingException.initCause(e);
            throw attractionParsingException;
        } catch(NumberFormatException e) {
            AttractionParsingException attractionParsingException = new AttractionParsingException("XML format error");
            attractionParsingException.initCause(e);
            throw attractionParsingException;
        } catch (MalformedURLException e) {
            AttractionParsingException attractionParsingException = new AttractionParsingException("URL format error");
            attractionParsingException.initCause(e);
            throw attractionParsingException;
        }

        return registry;
    }
}