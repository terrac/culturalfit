package com.caines.cultural.server;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;


public class Dao<T> {

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC
			| Modifier.TRANSIENT;

	protected Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 * 
	 * @param clazz
	 */
	protected Dao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Ref<T> put(T entity)

	{
	
		return Ref.create(OService.ofy().save().entity(entity).now());
	}

	public Map<Key<T>, T> putAll(Iterable<T> entities) {
		return OService.ofy().save().entities(entities).now();
	}

	public void delete(T entity) {
		ofy().delete().entity(entity);
	}

	public void deleteAll(Iterable<T> entities) {
		ofy().delete().entities(entities);
	}

	public T get(Long id) {

		return OService.ofy().load().type(this.clazz).id(id).now();
	}

	public T get(String id) {
		if(id == null){
			return null;
		}
		return OService.ofy().load().type(this.clazz).id(id).now();
	}

	public T getRN(Long id) {
		T o;
		try {
			o = get(id);
		} catch (NotFoundException e) {
			return null;
		}
		return o;
	}

	public T getRN(String id) {
		T o;
		try {
			o = get(id);
		} catch (NotFoundException e) {
			return null;
		}
		return o;
	}

	public T get(Ref<T> ref) {
		return ref.get();
	}

	public T getRN(Ref<T> Ref) {
		T o;
		try {
			o = get(Ref);
		} catch (NotFoundException e) {
			return null;
		}

		return o;
	}

	/**
	 * Convenience method to get all objects matching a single property
	 * 
	 * @param propName
	 * @param propValue
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue) {
		return ofy().load().type(clazz).filter(propName, propValue).first().now();
	}

	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().load().type(clazz).filter(propName, propValue);
		return asList(q.iterator());
	}

	public Query<T> getQByProperty(String propName, Object propValue) {
		return ofy().load().type(clazz).filter(propName, propValue);
	}

	private Objectify ofy() {
		return OService.ofy();
	}

	private List<T> asList(QueryResultIterator<T> queryResultIterator) {
		ArrayList<T> list = new ArrayList<T>();
		while (queryResultIterator.hasNext()) {
			list.add(queryResultIterator.next());
		}
		return list;
	}

	public Query<T> getQuery() {
		return OService.ofy().load().type(this.clazz);
	}

	public Query<T> fieldStartsWith(String field, String search) {
		Query<T> query = getQuery();
		return query.filter(field + " >=", search).filter(field + " <=", search + "\ufffd");
	}

	public static class Func<T> implements Function<Ref<T>, T> {
        public static Func<Object> INSTANCE = new Func<Object>();

        @Override
        public T apply(Ref<T> ref) {
            return deref(ref);
        }
    }

    public static <T> T deref(Ref<T> ref) {
        return ref == null ? null : ref.get();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> deref(List<Ref<T>> reflist) {
        return Lists.transform(reflist, (Func)Func.INSTANCE);
    }
    
}
