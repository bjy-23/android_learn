package bjy.edu.android_learn.rxjava;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okio.Source;

public class Test {
    public static void main(String[] args) {
        int position = 2;
        switch (position) {
            case 4:
                //flowable
                test4();
                break;
            case 3:
                // just、fromIterable、fromArray
                test3();
                break;
            case 2:
                //map flatmap lift
                test2();
                break;
            case 1:
                // Observable Observer
                test1();
                break;
        }
    }

    static void test4() {
        Flowable.intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)
//                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("doOnNext: " + aLong);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnComplete");
                    }
                })
                .subscribe();
    }

    static void test1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("Observable subscribe  线程: " + Thread.currentThread().getName());

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

                //
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())

                // 拦截onNext事件, 在observer 的onNext回调之前, 和onNext保持在一个线程
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println("Consumer  accept:" + s + "线程: " + Thread.currentThread().getName());
//                    }
//                })

                .subscribe(new Observer<String>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Observer  onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Observer onNext: " + s + "  线程: " + Thread.currentThread().getName());
                        if (s.equals("2"))
                            disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Observer onError  线程：" + Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Observer onComplete  线程：" + Thread.currentThread());
                    }
                });
    }

    static void test2() {
        /**
         * map 对Observable的事件对象进行转换 1:1的转换
         */
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

        /**
         * flatmap 1:N 的转换
         */
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

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).lift(new ObservableOperator<Object, String>() {
            @Override
            public Observer<? super String> apply(Observer<? super Object> observer) throws Exception {
                return null;
            }
        });

    }

    static void test3() {
        /**
         *  just 把传入的参数依次发送出来
         */
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

        /**
         * fromIterable fromArray; 将每个子元素发出来
         */
        System.out.println("fromIterable");
        Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Consumer accept: " + s);
                    }
                })
        ;

        System.out.println("");

        System.out.println("array");
        String[] strings = new String[]{"李嘉欣", "刘亦菲", "迪丽热巴"};
        Observable.fromArray(strings)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("Consumer  accept  " + s);
                    }
                });
    }
}
