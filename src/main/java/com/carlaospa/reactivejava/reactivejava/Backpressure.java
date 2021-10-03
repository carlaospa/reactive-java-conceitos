package com.carlaospa.reactivejava.reactivejava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static io.reactivex.schedulers.Schedulers.computation;

public class Backpressure {

    public static void main(String[] args) {

        Flowable.create(
                Backpressure::emit,
                //BackpressureStrategy.BUFFER
                //BackpressureStrategy.DROP
                //BackpressureStrategy.ERROR
                //BackpressureStrategy.LATEST
                BackpressureStrategy.MISSING
        )
                .observeOn(computation(), true, 2)
                .subscribe(Backpressure::process);
        sleep(10000);
    }

    private static void process(Integer number) {
        System.out.println("processing " + number);
        sleep(1000);
    }

    public static void emit (FlowableEmitter<Integer> emitter){
        IntStream.rangeClosed(1, 10)
                .forEach(n -> {
                    System.out.println("emitting " + n);
                    emitter.onNext(n);
                    sleep(500);
                } );
        emitter.onComplete();
    }

    private static void sleep(Integer millis){
        try{
            MILLISECONDS.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
