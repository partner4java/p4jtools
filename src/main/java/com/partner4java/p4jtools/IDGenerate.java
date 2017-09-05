package com.partner4java.p4jtools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ID生成器<br/>
 * 用于生成各种需要类型的唯一id。
 * 
 * @author partner4java
 * 
 */
public class IDGenerate {

	public static IdWorker w = null;
	static {
		if (Config.intValue("worker_id") <= 0) {
			System.out.println("server id error, please check config file!");
			System.exit(-1);
		}
		w = new IdWorker(Config.intValue("worker_id"),
				Config.intValue("datacenter_id"));
	}

	/**
	 * 获取主键ID<br/>
	 * 需要初始化配置worker_id、datacenter_id以保证及期间的唯一性
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized long getUniqueID(){
		return w.nextId();
	}

	private static Lock tableIdLock = new ReentrantLock();

	/**
	 * 获取数据主键ID<br/>
	 * 保证同机下id的唯一性，并不保证id的效率，也不保证不同机器间的唯一性
	 * 
	 * @return 返回个数>=13
	 */
	public static long getTableId() {
		tableIdLock.lock();
		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			tableIdLock.unlock();
		}
		return System.currentTimeMillis();
	}

	private static Lock loginIdLock = new ReentrantLock();

	/**
	 * 获取用户登录的唯一id<br/>
	 * 
	 * @return
	 */
	public static String getLoginId() {
		loginIdLock.lock();
		try {
			TimeUnit.NANOSECONDS.sleep(1);
			StringBuilder builder = new StringBuilder();
			long nano = System.nanoTime();
			String nanoStr = nano + "";
			builder.append(nanoStr.substring(2, nanoStr.length() - 2));
			builder.append((nano & Integer.MAX_VALUE >> 2) + getOrderId());
			builder.append(Thread.currentThread().getId());
			return builder.toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			loginIdLock.unlock();
		}
		return System.nanoTime() + "";
	}

	/**
	 * 刷新后的排序id<br/>
	 * 并不保证唯一性
	 * 
	 * @return
	 */
	public static long getOrderId() {
		return System.nanoTime() / 1000L;
	}
}

/**
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 * 
 * @author Polim
 */
class IdWorker {
	private final static long twepoch = 1288834974657L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 数据中心标识位数
	private final static long datacenterIdBits = 5L;
	// 机器ID最大值
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private final static long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final static long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final static long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;

	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;

	private long sequence = 0L;
	private final long workerId;
	private final long datacenterId;

	public IdWorker(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
					"worker Id can't be greater than %d or less than 0");
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(
					"datacenter Id can't be greater than %d or less than 0");
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try {
				throw new Exception(
						"Clock moved backwards.  Refusing to generate id for "
								+ (lastTimestamp - timestamp) + " milliseconds");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift)
				| (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}
}
