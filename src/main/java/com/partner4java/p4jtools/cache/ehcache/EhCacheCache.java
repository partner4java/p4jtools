package com.partner4java.p4jtools.cache.ehcache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

/**
 * {@link Cache} implementation on top of an {@link Ehcache} instance.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 3.1
 */
public class EhCacheCache implements Cache {

	private final Ehcache cache;


	/**
	 * Create an {@link EhCacheCache} instance.
	 * @param ehcache backing Ehcache instance
	 */
	public EhCacheCache(Ehcache ehcache) {
		Assert.notNull(ehcache, "Ehcache must not be null");
		Status status = ehcache.getStatus();
		Assert.isTrue(Status.STATUS_ALIVE.equals(status),
				"An 'alive' Ehcache is required - current cache is " + status.toString());
		this.cache = ehcache;
	}


	public String getName() {
		return this.cache.getName();
	}

	public Ehcache getNativeCache() {
		return this.cache;
	}

	public ValueWrapper get(Object key) {
		Element element = this.cache.get(key);
		return (element != null ? new SimpleValueWrapper(element.getObjectValue()) : null);
	}

	public void put(Object key, Object value) {
		this.cache.put(new Element(key, value));
	}

	public void evict(Object key) {
		this.cache.remove(key);
	}

	public void clear() {
		this.cache.removeAll();
	}


	@Override
	public <T> T get(Object paramObject, Class<T> paramClass) {
		throw new RuntimeException("没有实现");
	}


	@Override
	public ValueWrapper putIfAbsent(Object paramObject1, Object paramObject2) {
		throw new RuntimeException("没有实现");
	}

}
