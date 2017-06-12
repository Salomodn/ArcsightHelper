/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author root
 */
public class DataWriter {

    public DataWriter() {
    }
    
     void saveData(String filepath,HashMap<String, String> map) {
        try {

            FileWriter fstream;
            BufferedWriter out;

            // create your filewriter and bufferedreader
            fstream = new FileWriter(filepath);
            out = new BufferedWriter(fstream);

            // initialize the record count
            int count = 0;

            // create your iterator for your map
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

            // then use the iterator to loop through the map, stopping when we reach the
            // last record in the map or when we have printed enough records
            while (it.hasNext()) {

                // the key/value pair is stored here in pairs
                Map.Entry<String, String> pairs = it.next();
                //System.out.println("Value is " + pairs.getValue());

                // since you only want the value, we only care about pairs.getValue(), which is written to out
                out.write(pairs.getKey() + "=" + pairs.getValue() + "\n");

                // increment the record count once we have printed to the file
                count++;
            }
            // lastly, close the file and end
            out.close();
        } catch (Exception e) {
        }
    }
     public void saveContent(String filepath,String content) {
        try {

            FileWriter fstream;
            BufferedWriter out;

            // create your filewriter and bufferedreader
            fstream = new FileWriter(filepath);
            out = new BufferedWriter(fstream);

            
            //write out content
            out.write(content);
            // lastly, close the file and end
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
