package com.cleaner.gank.rxGank;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 描述:
 * Created by mjd on 2017/3/7.
 */

public class Test {

    public static void main(String[] args) throws InterruptedException {

//        Flowable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(1000);
//                return "Done";
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        System.out.println(s);
//                    }
//
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        System.out.println(throwable.getMessage());
//                    }
//                });
//        Thread.sleep(2000);


//        Flowable.range(1, 10)
//                .observeOn(Schedulers.computation())
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
//                        return integer * integer;
//                    }
//                })
//                .blockingSubscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        System.out.println(integer);
//                    }
//                });

        Flowable.range(1,10)
                .flatMap(new Function<Integer, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(@NonNull Integer integer) throws Exception {
                        return Flowable.just(integer);
                    }
                });
    }

}
