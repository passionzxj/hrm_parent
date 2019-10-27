public class Singleton {
    //懒汉模式
    //将构造函数私有化
    private Singleton() {

    }

    //将对象实例化
    private static volatile Singleton instance;

    //得到实例的方法

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        Singleton singleton1 = getInstance();
        Singleton singleton2 = getInstance();
        System.out.println(singleton1);
        System.out.println(singleton2);
        System.out.println(singleton2.equals(singleton1));
    }
}


