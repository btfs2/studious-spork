package uk.ac.cam.bizrain.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a pointer.
 * 
 * Java has pointers now
 * 
 * @author btfs2
 *
 * @param <T> Type to point to
 */
public class Pointer<T> {
	
	T backing = null;
	List<PCB<T>> ccbs = new ArrayList<>();
	
	public Pointer() {
		this(null);
	}
	
	public Pointer(T thing) {
		backing = thing;
	}
	
	public T get() {
		return backing;
	}
	
	public T set(T to) {
		T old = backing;
		backing = to;
		ccbs.stream().forEach(i -> i.cb(to));
		return old;
	}
	
	public void addCB(PCB<T> cb) {
		ccbs.add(cb);
	}
	
	/**
	 * Pointer change callback
	 * 
	 * @author btfs2
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public interface PCB<T> {
		public void cb(T thing);
	}

}
