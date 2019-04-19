package ShimonHeadController.innards.namespace.rhizome.metavariable;

import java.util.*;
import java.util.List;


import ShimonHeadController.innards.namespace.context.*;
import ShimonHeadController.innards.namespace.context.ContextTreeInternals.Bobj;

/**
 * implements a list that is split up among many levels of the context tree
 * @author marc
 */
public class MetaList implements List {
	static int contextListKeyUniq= 0;
	//Key storageKey= new Key((contextListKeyUniq++) + "_contextListStorage");

	Object cached_listOfLists_where= null;
	List _cached_listOfLists= new ArrayList();

	boolean iteratesTopToBottom= false;

	public MetaList() {
		this(true);
		Cull.registerForCull(this);
	}

	/**

	 * warning with 'false' get(index) is in a different order from the iterator. The default is true

	 * @param iteratesTopToBottom
	 */

	public MetaList(boolean iteratesTopToBottom) {
		this.iteratesTopToBottom= iteratesTopToBottom;
		Cull.registerForCull(this);
	}

	/**
	 * propogates the contents of this level up to the parent level rarely used it seems
	 */
	public MetaList retain() {
		List l= listOfLists();
		List lowest= ((List) l.get(0));
		Bobj at= (Bobj) ContextTree.where().getParent(0);
		if (at != null) {
			List list= (List) at.get(this, null);
			if (list == null)
				at.set(this, new ArrayList(lowest));
			else
				list.addAll(lowest);
			lowest.clear();
		}
		return this;
	}

	protected List listOfLists() {
		Bobj at= ContextTree.where();
		if (at == cached_listOfLists_where)
			return _cached_listOfLists;
		// need to build lists
		_cached_listOfLists.clear();
		List firstList= (List) at.get(this, null);
		if (firstList == null)
			ContextTree.set(this, firstList= new ArrayList());
		_cached_listOfLists.add(firstList);
		final Bobj fat= at;
		cached_listOfLists_where= at;
		
		while (at != null) {
			at= (Bobj) at.getParent(0);
			if (at != null) {
				List list= (List) at.get(this, null);
				if (list != null) {
					_cached_listOfLists.add(list);
				}
			}
		}
		Metas.ensureMetaAdded(this);
		return _cached_listOfLists;
	}
	/**
	 * @see List#size()
	 */
	public int size() {
		int t= 0;
		List l= listOfLists();
		for (int i= 0; i < l.size(); i++)
			t += ((List) l.get(i)).size();
		return t;
	}
	/**
	 * @see List#isEmpty()
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * @see List#contains(Object)
	 */
	public boolean contains(Object o) {
		List l= listOfLists();
		for (int i= 0; i < l.size(); i++)
			if (((List) o).contains(o))
				return true;
		return false;
	}
	/**
	 * @see List#iterator()
	 */
	public Iterator iterator() {
		final List l= listOfLists();
		return new Iterator() {
			int index= !iteratesTopToBottom ? 0 : l.size() - 1;
			Iterator iNow= ((List) l.get(index)).iterator();
			public boolean hasNext() {
				if (iNow.hasNext())
					return true;
				while ((index >= 0) && (index < l.size())) {
					index += !iteratesTopToBottom ? 1 : -1;

					if ((index >= 0) && (index < l.size())) {
						iNow= ((List) l.get(index)).iterator();
						if (iNow.hasNext())
							return true;

					}
				}
				iNow= null;
				return false;
			}

			public Object next() {
				if (hasNext())
					return iNow.next();
				throw new NoSuchElementException(" ran out");
			}

			public void remove() {
				iNow.remove();
			}
		};
	}
	/**
	 * @see List#toArray()
	 */
	public Object[] toArray() {
		List l= listOfLists();
		Object[] a= new Object[this.size()];
		int t= l.size() - 1;
		List at= (List) l.get(t);
		int n= 0;
		for (int i= 0; i < a.length; i++) {
			if (n >= at.size()) {
				t--;
				at= (List) l.get(t);
			}

			a[i]= at.get(n);
			n++;
		}
		return a;
	}
	/**
	 * @see List#toArray(Object[])
	 */
	public Object[] toArray(Object[] template) {
		List l= listOfLists();
		Object[] a= (Object[]) java.lang.reflect.Array.newInstance(template.getClass().getComponentType(), this.size());
		int t=0;
		List at= (List) l.get(t);
		int n= 0;
		for (int i= 0; i < a.length; i++) {
			while (n >= at.size()) {
				t++;
				n-=at.size();
				at= (List) l.get(t);
			}

			try {
				a[i]= at.get(n);
			} catch (ArrayStoreException ex) {
				ArrayStoreException e= new ArrayStoreException(" type not assignable <" + a.getClass() + ">" + at.get(n).getClass());
				e.initCause(ex);
				throw e;
			}
			catch (ArrayIndexOutOfBoundsException ex)
			{
				System.out.println(" a.length <"+a.length+"> <"+at.size()+"> <"+n+">");
			}
			n++;
		}
		return a;
	}
	/**
	 * @see List#add(Object)
	 */
	public boolean add(Object o) {
		List l= listOfLists();
		return ((List) l.get(0)).add(o);
	}
	/**
	 * @see List#remove(Object)
	 */
	public boolean remove(Object o) {
		List l= listOfLists();
		int m= l.size() - 1;
		while (m >= 0) {
			if (((List) l.get(m)).remove(o))
				return true;
			m--;
		}
		return false;
	}
	/**
	 * @see List#containsAll(Collection)
	 */
	public boolean containsAll(Collection c) {
		throw new IllegalArgumentException(" not implemented");
	}
	/**
	 * @see List#addAll(Collection)
	 */
	public boolean addAll(Collection c) {
		List l= listOfLists();
		return ((List) l.get(0)).addAll(c);
	}
	/**
	 * @see List#addAll(int, Collection)
	 */
	public boolean addAll(int index, Collection c) {
		throw new IllegalArgumentException(" not implemented");
	}
	/**
	 * @see List#removeAll(Collection)
	 */
	public boolean removeAll(Collection c) {
		List l= listOfLists();
		for (int i= 0; i < l.size(); i++)
			 ((List) l.get(i)).removeAll(c);
		return true;
	}
	/**
	 * @see List#retainAll(Collection)
	 */
	public boolean retainAll(Collection c) {
		List l= listOfLists();
		for (int i= 0; i < l.size(); i++)
			 ((List) l.get(i)).retainAll(c);
		return true;
	}
	/**
	 * @see List#clear()
	 */
	public void clear() {
		List l= listOfLists();
		for (int i= 0; i < l.size(); i++)
			 ((List) l.get(i)).clear();
	}
	/**
	 * @see List#get(int)
	 */
	public Object get(int index) {
		
		List l= listOfLists();
		//System.out.println("          inside get <"+index+"> from <"+l.size()+"> lists");
		int m= 0;
		while (m < l.size()) {
			
			List ll= (List) l.get(m);
			//System.out.println(m+" "+index+" "+ll.size());
			if (index < ll.size())
				return ll.get(index);
			m++;
			index -= ll.size();
		}
		throw new ArrayIndexOutOfBoundsException(" out of bounds <" + index + "> <" + size() + ">");
	}
	/**
	 * @see List#set(int, Object)
	 */
	public Object set(int index, Object element) {
		List l= listOfLists();
		int m= 0;
		while (m <l.size()) {;
			List ll= (List) l.get(m);
			if (index < ll.size())
				return ll.set(index, element);
			m++;
			index -= ll.size();
		}
		throw new ArrayIndexOutOfBoundsException(" out of bounds <" + index + "> <" + size() + ">");
	}
	/**
	 * @see List#add(int, Object)
	 */
	public void add(int index, Object element) {
		List l= listOfLists();
		int m= 0;
		while (m <l.size()) {
			List ll= (List) l.get(m);
			//System.out.println(" index <"+index+"> <"+m+">");
			//if ((index == 0) && (m > 0)) {
			//	((List) l.get(m - 1)).add(((List)l.get(m-1)).size(), element);
			//	return;
			//}
			if (index <= ll.size()) {
				ll.add(index, element);
				return;
			}
			m++;
			index -= ll.size();
		}
		throw new ArrayIndexOutOfBoundsException(" out of bounds <" + index + "> <" + size() + ">");
	}
	/**
	 * @see List#remove(int)
	 */
	public Object remove(int index) {
		List l= listOfLists();
		int m= l.size() - 1;
		while (m >= 0) {
			List ll= (List) l.get(m);
			if (index < ll.size()) {
				ll.remove(index);
			}
			m--;
			index -= ll.size();
		}
		throw new ArrayIndexOutOfBoundsException(" out of bounds <" + index + "> <" + size() + ">");
	}
	/**
	 * @see List#indexOf(Object)
	 */
	public int indexOf(Object o) {
		List l= listOfLists();
		int m= l.size() - 1;
		int off= 0;
		while (m >= 0) {
			List ll= (List) l.get(m);
			int mm= ll.indexOf(o);
			if (mm != -1)
				return mm + off;
			off += ll.size();
			m--;
		}
		return -1;
	}
	/**
	 * @see List#lastIndexOf(Object)
	 */
	public int lastIndexOf(Object o) {
		List l= listOfLists();
		int m= l.size() - 1;
		int off= 0;
		while (m < l.size()) {
			List ll= (List) l.get(m);
			int mm= ll.lastIndexOf(o);
			if (mm != -1)
				return mm + off;
			off += ll.size();
			m++;
		}
		return -1;
	}
	/**
	 * @see List#listIterator()
	 */
	public ListIterator listIterator() {
		throw new IllegalArgumentException(" not implemented");
	}
	/**
	 * @see List#listIterator(int)
	 */
	public ListIterator listIterator(int index) {
		throw new IllegalArgumentException(" not implemented");
	}
	/**
	 * @see List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		throw new IllegalArgumentException(" not implemented");
	}

	public String toString() {
		String s= "[\n";
		Iterator i= listOfLists().iterator();
		while (i.hasNext()) {
			s += " " + i.next()+",\n";
		}
		return s + " ]";
	}

}
