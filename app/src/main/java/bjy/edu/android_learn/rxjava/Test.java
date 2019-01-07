package bjy.edu.android_learn.rxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Test {
    public static void main(String[] args) {

        // Observable Observer
//        test1();

        //Observable
//        test3();

        //map flatmap
        test2();
    }

    static void test1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("Observable  subscribe");
                System.out.println("Observable  subscribe  线程  " + Thread.currentThread().getName());

                System.out.println("发送1");
                emitter.onNext("1");

                System.out.println("发送2");
                emitter.onNext("2");

                System.out.println("发送3");
                emitter.onNext("3");

                System.out.println("发送4");
                emitter.onNext("4");
            }
        })

//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())

                // 拦截onNext事件, 在observer 的onNext回调之前, 和onNext保持在一个线程
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Consumer  accept  " + s);

                        System.out.println("Consumer  accept  线程  " + Thread.currentThread().getName());
                    }
                })

                .subscribe(new Observer<String>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Observer  onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Observer  onNext");
                        System.out.println("Observer  onNext  线程" + Thread.currentThread().getName());

                        System.out.println("Observer  接收" + s);

                        if (s.equals("2"))
                            disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    static void test2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("Observable  发送  1");
                emitter.onNext("1");
            }
        })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        System.out.println("map  apply  " + s);
                        return Integer.parseInt(s) + 10;
                    }
                })

                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept  " + integer);
                    }
                })
        ;

        System.out.println("");

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("Observable  subscribe  3");
                emitter.onNext("3");
            }
        })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        int num = Integer.parseInt(s);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < num; i++) {
                            list.add("" + i);
                        }
                        return Observable.fromIterable(list);
                    }
                })

                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Consumer  " + s);
                    }
                });
    }

    static void test3() {
        List<String> list = new ArrayList<>();
        list.add("na");
        list.add("ruto");

        List<String> list2 = new ArrayList<>();
        list2.add("lu");
        list2.add("ffy");

        System.out.println("just");
        Observable.just(list)
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println("Consumer  accept  " + strings.toString());
                    }
                })
        ;

        System.out.println("");

        System.out.println("just");
        Observable.just(list, list2)
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println("Consumer  accept  " + strings.toString());
                    }
                })
        ;

        System.out.println("");

        System.out.println("fromIterable");
        Observable.fromIterable(list)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("Consumer  accept  " + integer);
                    }
                })
        ;
    }
}