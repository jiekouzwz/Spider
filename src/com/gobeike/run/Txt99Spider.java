package com.gobeike.run;

import com.gobeike.util.EnumEncode;
import com.gobeike.util.GobeikeHttpClient;

import java.util.List;
import java.util.Map;

/**
 * Created by xuff on 17-3-28.
 */
public class Txt99Spider {

    public List<Map<String,String>> getInfo(){
        String url="http://www.txt99.com/";
        GobeikeHttpClient client=new GobeikeHttpClient();
//           client.sendRequestByStream()
        client.setEncoding(EnumEncode.gb2312);
        try {
            String result = client.sendRequestByGet(url);
            System.out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
