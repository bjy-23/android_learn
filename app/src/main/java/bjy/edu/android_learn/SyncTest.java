package bjy.edu.android_learn;

public class SyncTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    fun_1();

                    try {
//                        Thread.sleep(2000);

                        wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    fun_2();

                    try {
//                        Thread.sleep(2000);

                        wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public static void fun_1(){
        synchronized (SyncTest.class) {
            System.out.println("函数1：");
        }
    }

    public static void fun_2(){
        synchronized (SyncTest.class){
            System.out.println("函数2：");
        }
    }
}
