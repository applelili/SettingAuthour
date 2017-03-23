package com.gfd.xml;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

/** Properties文件（配置文件）操作测试类*/
@RunWith(AndroidJUnit4.class)
public class PropertiesTest {

    private Context context;

    @Before
    public void init(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void readProperties(){
        try {
            //读取assets目录下的文件
            InputStream is = context.getAssets().open("user.properties");
            Properties properties = new Properties();
            properties.load(new InputStreamReader(is,"GBK"));//加载指定的文件
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                String valus = (String) properties.get(key);
                Log.e("p",key + " = " + valus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void writeProperties(){

        try {
            Properties properties = new Properties();
            //指定输出的文件
            File file = new File(context.getFilesDir() + "/d.properties");
            if(!file.exists())file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            properties.put("userp","www.bai.com");
            properties.store(out,null);//写到文件中
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
