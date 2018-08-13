package RPC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: hadoop-2.8.2-test
 * @classname: JavaInvocationTest
 * @description：TODO
 * @author: zhiqiang32
 * @create: 2018-08-05 13:12
 **/
public class JavaInvocationTest {


    public static void main(String[] args) {
        MyInvocationHandler myInvocationHandler=new MyInvocationHandler();
        Object object=Proxy.newProxyInstance(RealOther.class.getClassLoader(),new Class[]{GoDo.class},myInvocationHandler);
        GoDo gg=(GoDo)object;
        gg.doSomething();
    }

}

class MyInvocationHandler implements InvocationHandler{

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Call call=new Call();
        call.call(method,args);
        return null;

    }
}

class Call{
    public  void call(Method method,Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(new RealOther(),args);
    }
}

interface GoDo{
    void doSomething();
}

class RealOther implements GoDo{

    public void doSomething() {
        System.out.println("hello word");
    }
}