package com.gfd.xml;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Xml;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/** XML文件操作测试类 */
@RunWith(AndroidJUnit4.class)
public class XmlTest {

    private List<Person> persons;
    private Context context;

    @Before
    public void init(){
        context = InstrumentationRegistry.getTargetContext();
        persons = new ArrayList<>();
        persons.add(new Person(40,1,"林志玲","女"));
        persons.add(new Person(50,2,"刘德华","男"));
        persons.add(new Person(45,3,"林心如","女"));
        persons.add(new Person(42,4,"范冰冰","女"));
        persons.add(new Person(40,5,"郭富东","男"));
    }

    /* XML序列化 */
    @Test
    public void serialize(){
        try {
            //指定存储的路径
            File outFile = new File(context.getFilesDir() + "/person.xml");
            XmlSerializer serializer = Xml.newSerializer();//获取xml序列化对象
            FileOutputStream out = new FileOutputStream(outFile);
            serializer.setOutput(out,"UTF-8");
            serializer.startDocument("UTF-8",true);//设置文档的开始
            serializer.startTag(null,"persons");
            for (Person person : persons) {
                serializer.startTag(null,"person");
                serializer.attribute(null,"id",person.getId() + "");
                //写name标签
                serializer.startTag(null,"name");
                serializer.text(person.getName());
                serializer.endTag(null,"name");
                //age标签
                serializer.startTag(null,"age");
                serializer.text(person.getAge() + "");
                serializer.endTag(null,"age");
                //sex标签
                serializer.startTag(null,"sex");
                serializer.text(person.getSex());
                serializer.endTag(null,"sex");
                //写一组的结束标签
                serializer.endTag(null,"person");
            }
            serializer.endTag(null,"persons");
            serializer.endDocument();
            serializer.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Xml解析
    *  Java：
    *       DOM ：会把整个文件加载到内存中，仔解析，不适合大文件
    *       SAX ：基于事件的，可以一个一个的解析
    *  Android新增：
    *       PULL ：跟SAX类似
    * */
    @Test
    public void pullParser(){

        try {
            //指定读取的路径
            File inFile = new File(context.getFilesDir() + "/person.xml");
            if(!inFile.exists())return;
            XmlPullParser parser = Xml.newPullParser();//获取解析xml的对象
            parser.setInput(new FileInputStream(inFile),"UTF-8");
            int eventType = parser.getEventType();//获取解析的事件类型
            List<Person> persons = null;
            Person person = null;
            while (eventType != XmlPullParser.END_DOCUMENT){//不是文档的结尾就解析
                switch (eventType){
                    case XmlPullParser.START_TAG://开始标签
                        String tagName = parser.getName();
                        if(tagName.equals("persons")){//开始解析
                            persons = new ArrayList<>();
                        }else if(tagName.equals("person")){
                            person = new Person();
                            person.setId(Integer.parseInt(parser.getAttributeValue(null,"id")));//获取标签内的属性
                        }else if(tagName.equals("name")){
                            person.setName(parser.nextText());
                        }else if(tagName.equals("age")){
                            person.setAge(Integer.parseInt(parser.nextText()));
                        }else if(tagName.equals("sex")){
                            person.setSex(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG://结束标签
                        String endTag = parser.getName();
                        if(endTag.equals("person")){//一个数据解析完毕，添加到集合里面
                            persons.add(person);
                            person = null;
                        }
                        break;
                }
                //开始下一个
                eventType = parser.next();
            }
            //打印集合中的数据
            for (Person person1 : persons) {
                System.out.println(person1.toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
