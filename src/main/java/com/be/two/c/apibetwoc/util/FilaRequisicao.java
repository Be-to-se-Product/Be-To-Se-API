package com.be.two.c.apibetwoc.util;

public class FilaRequisicao {
    private FilaObj<Thread> fila;
    private final Object lock = new Object();

    public FilaRequisicao() {
        this.fila = new FilaObj<>(10);
    }



    public void entrarFila(){
        synchronized (lock) {
            Thread currentThread = Thread.currentThread();
            fila.insert(currentThread);
            aguardarFila();
        }
    }

    private void aguardarFila() {
        synchronized (lock) {
            while (!fila.isEmpty() && fila.peek() != Thread.currentThread()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sairFila() {
        synchronized (lock) {
            fila.poll();
            if (!fila.isEmpty()) {
                lock.notify();
            }
        }
    }
}
