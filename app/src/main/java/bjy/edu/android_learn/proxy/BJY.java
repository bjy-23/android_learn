package bjy.edu.android_learn.proxy;

public class BJY implements Man{
    @Override
    public void work() {
        System.out.println("bjy工作了");
    }

    @Override
    public void love() {
        System.out.println("bjy恋爱了");
    }
}
