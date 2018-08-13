package RPC;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @program: hadoop-2.8.2-test
 * @classname: DFSClientRpcTest
 * @description：TODO
 * @author: zhiqiang32
 * @create: 2018-08-02 10:34
 **/
public class DFSClientRpcTest {

    public static boolean testIP() throws UnknownHostException {

        String resolveHost="nn1.nyx.dip.weibo.com";
        InetAddress iaddr = InetAddress.getByName(resolveHost);
        InetSocketAddress server = new InetSocketAddress(iaddr, 8020);
        System.out.println(server.getAddress());
        InetAddress iaddr1 = InetAddress.getByName(resolveHost);
        InetSocketAddress server1 = new InetSocketAddress(iaddr1, 8020);
        System.out.println(server.equals(server1));

        return true;

    }

    public static void main(String[] args) throws UnknownHostException {
//        testIP();
       System.setProperty("HADOOP_USER_NAME", "hdfs");
//       writeTo("hello world".getBytes(),"/zhiqiang/test.txt");
//        mkdir("/user/yurun/szq");
    //    delete("/szq");
     rename("/zhiqiang/test.txt","/zhiqiang/pro");


     FileSystem fileSystem=getFileSystem();
//     WTest tt=new WTest();

//     Thread A=new Thread(tt);
//     A.setName("A");
//     A.start();
//     Thread B=new Thread(tt);
//     B.start();

//        System.out.println(1<<5);

    }
    /*
    * @Description:
    * @Date: 上午10:37 2018/8/2
    * @Param: []
    * @return: org.apache.hadoop.fs.FileSystem
    */
    public static FileSystem getFileSystem(){
        Configuration conf=new Configuration();
//        conf.set("fs.defaultFS","nn1.nyx.dip.weibo.com:8020");
        conf.set("fs.defaultFS","hdfs://kraken");
        conf.set("dfs.nameservices", "kraken");
        conf.set("dfs.ha.namenodes.kraken", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.kraken.nn1", "nn1.kraken.dip.weibo.com:8020");
        conf.set("dfs.namenode.rpc-address.kraken.nn2", "nn2.kraken.dip.weibo.com:8020");
        conf.set("dfs.client.failover.proxy.provider.kraken", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        FileSystem fs=null;
        try {
            fs=FileSystem.get(conf);
            return fs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fs;
    }

    public static void writeTo(byte[] bytes,String filename){
        Path path=new Path(filename);
        FSDataOutputStream outputStream= null;
        try {
            outputStream = getFileSystem().create(path);
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void delete(String path) {
        Path p=new Path(path);
        try {
            getFileSystem().delete(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void rename(String old,String nnew) {
        Path o=new Path(old);
        Path n=new Path(nnew);
        try {
            FileSystem fs=getFileSystem();
            fs.rename(o,n);
            fs=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void mkdir(String dir){
        Path d =new Path(dir);
        try {
            getFileSystem().mkdirs(d);
        } catch (IOException e) {

        }
    }
}

class WTest implements Runnable{

    public void run() {
        echo();
    }

    public synchronized void echo(){
        try {
            if (Thread.currentThread().getName()=="A")
                wait(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <10 ; i++) {
            System.out.println(Thread.currentThread().getName()+i);
        }
    }
}
