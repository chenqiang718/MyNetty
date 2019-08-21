import service.EchoService;
import service.MyService;

/**
 * @Author: Chen Qiang
 * @Date: 2019-08-20 14:08
 * @description 工程启动入口
 */
public class Start {
    public static void main(String[] args) {
        int port=8080;

        MyService service=new EchoService(port);
        try {
            service.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
