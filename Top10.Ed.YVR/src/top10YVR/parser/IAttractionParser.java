package top10YVR.parser;

import top10YVR.model.Guide;

import java.io.IOException;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public interface IAttractionParser {

    // EFFECTS:
    //   return parsed guide;
    //   attraction will not to guide if any one of the following fields is missing:
    //     - name, address, phone, web address, location, services;
    //   throws IOException if there is a problem reading data from file (e.g., path to file is not valid);
    //   throws AttractionParsingException if:
    //     - data in file does not follow expected syntax
    //     - URL cannot be formed from given webaddress
    //     - latitude or longitude cannot be parsed as a double
    //     - no resources were added to the registry
    Guide parse() throws AttractionParsingException, IOException;
}
