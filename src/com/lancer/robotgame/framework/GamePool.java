package com.lancer.robotgame.framework;

import java.util.ArrayList;
import java.util.List;

public class GamePool<T> {
	/**
	 * It has a single method, createObject(), that will return a new object
	 * with the generic type of the Pool/ PoolObjectFactory instance.
	 * @param <T>
	 */
	public interface GamePoolObjectFactory<T> {
		public T createObject();
	}

	private final List<T> freeObjects;
	private final GamePoolObjectFactory<T> factory;
	private final int maxSize;

	public GamePool( GamePoolObjectFactory<T> factory, int maxSize ) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>( maxSize );
	}

	/**
	 * The newObject() method is responsible for either handing us a brand-new instance of the type
		held by the Pool, via the PoolObjectFactory.newObject() method, or returning a pooled instance
		in case there’s one in the freeObjectsArrayList. If we use this method, we get recycled objects
		as long as the Pool has some stored in the freeObjects list. Otherwise, the method creates a
		new one via the factory
	 */
	public T newObject() {
		T object = null;

		if( freeObjects.size() == 0 )
			object = factory.createObject();
		else
			object = freeObjects.remove( freeObjects.size() - 1 );

		return object;
	}

	/**
	 * The free() method lets us reinsert objects that we no longer use. It simply inserts the object into
		the freeObjects list if it is not yet filled to capacity. If the list is full, the object is not added, and it
		is likely to be consumed by the garbage collector the next time it executes.
	 */
	public void free( T object ) {
		if( freeObjects.size() < maxSize )
			freeObjects.add( object );
	}
} // GamePool

/**
So, how can we use that class? We’ll look at some pseudocode usage of the Pool class in
conjunction with touch events.

PoolObjectFactory <TouchEvent> factory =  new PoolObjectFactory <TouchEvent> () {
@Override
public TouchEvent createObject() {
return new TouchEvent();
}
};
Pool <TouchEvent> touchEventPool =  new Pool <TouchEvent> (factory, 50);
TouchEvent touchEvent = touchEventPool.newObject();
. . . do something here . . .
touchEventPool.free(touchEvent);

First, we define a PoolObjectFactory that creates TouchEvent instances. Next, we instantiate the
Pool by telling it to use our factory and that it should maximally store 50 TouchEvents. When we
want a new TouchEvent from the Pool, we call the Pool’s newObject() method. Initially, the Pool is
empty, so it will ask the factory to create a brand-new TouchEvent instance. When we no longer
need the TouchEvent, we reinsert it into the Pool by calling the Pool’s free() method. The next
time we call the newObject() method, we get the same TouchEvent instance and recycle it to
avoid problems with the garbage collector. This class is useful in a couple of places. Please note
that you must be careful to fully reinitialize reused objects when they’re fetched from the Pool.
*/