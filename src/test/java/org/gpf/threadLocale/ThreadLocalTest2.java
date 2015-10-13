package org.gpf.threadLocale;


public class ThreadLocalTest2 implements Runnable {

	/**
	 * 线程本地
	 */
	ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	int i = 0;

	@Override
	public void run() {
		for (; i < 10; i++) {
			threadLocal.set(Thread.currentThread().getName());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(Thread.currentThread().getName() + ':' + threadLocal.get());
		}
	}

	/**
	 * 测试线程安全
	 */
	public static void main(String[] args) {
		ThreadLocalTest2 tlt = new ThreadLocalTest2();

		new Thread(tlt, "AAA").start();
		new Thread(tlt, "BBB").start();
	}

}
