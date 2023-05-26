package test;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class test1 {


    @Test
    public void iotest() throws FileNotFoundException {
        System.out.println("张开后");
        var len = 0;
        var bytes = new byte[1024];
        var inputStream = new FileInputStream("C:\\Users\\张欢\\Desktop\\src\\main\\java\\com\\vvvv\\Dao\\studentMapper.java");
        try (inputStream) {
            while ((len = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test1(){
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();


    }
}
