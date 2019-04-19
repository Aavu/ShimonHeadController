package ShimonHeadController.innards.namespace.rhizome;

import java.util.*;

import ShimonHeadController.innards.*;
import ShimonHeadController.innards.namespace.context.*;
import ShimonHeadController.innards.namespace.context.ContextTreeInternals.Bobj;


/**
 * a new baseclass. acting as a drop in replacement for NamedGroup.
 * @author marc
 * Created on May 6, 2003
 */
public class VirtualizedNamedGroup implements iNamedGroup, iRhizomeKeys
{
	protected String name;
	protected Key key= null;
	transient protected StackTraceElement[] stacktrace;
	transient protected Bobj creationContext;
	public VirtualizedNamedGroup(String name)
	{
		this.name= name.intern();
		if (NamedObject.allocationStackTraces)
		{
			stacktrace= new Exception().getStackTrace();
		}
		creationContext= ContextTreeSpecial.where();
	}

	public VirtualizedNamedGroup(Key key)
	{
		this.name= key.toString().intern();
		this.key= key;
		if (NamedObject.allocationStackTraces)
		{
			stacktrace= new Exception().getStackTrace();
		}
		creationContext= ContextTreeSpecial.where();
	}

	public VirtualizedNamedGroup()
	{
		this.name= "unnamed";
		if (NamedObject.allocationStackTraces)
		{
			stacktrace= new Exception().getStackTrace();
		}
		creationContext= ContextTreeSpecial.where();
	}

	public iTopologicalView getView()
	{
		iTopologicalView view= getDefaultView();
		
		if (view == null)
			{
				view = (iTopologicalView) ContextTree.get(default_heirarchical_view, null);
			}
			
		return view;
	}

	public iTopologicalView.Mutable getMutableView()
	{
		iTopologicalView.Mutable view = getDefaultMutableView();
		if (view == null)
			{
				view= (iTopologicalView.Mutable) ContextTree.get(default_heirarchical_view, null);
			}
		return view;
	}
	
	public iWeightedTopologicalView getWeightedView()
	{
		return null;
	}
	
	public iWeightedTopologicalView.WeightedMutable getWeightedMutableView()
	{
		return null;
	}

	protected iTopologicalView getDefaultView()
	{
		return getDefaultMutableView();
	}

	protected iTopologicalView.Mutable getDefaultMutableView()
	{
		return null;
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#getChildrenIterator()
	 */
	public Iterator getChildrenIterator()
	{
		return getView().getChildren(this).iterator();
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#getChildrenList()
	 */
	public List getChildrenList()
	{
		return getView().getChildren(this);
	}
	
	public List getParentList()
	{
		return getView().getParents(this);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#addChild(ShimonHeadController.innards.iNamedObject)
	 */
	public void addChild(iNamedObject child)
	{
		getMutableView().addChild(this, child);
	}

	/**
	 * 
	 * @see ShimonHeadController.innards.iNamedGroup#insertChildAt(ShimonHeadController.innards.iNamedObject, int)
	 */
	public void insertChildAt(iNamedObject child, int index) throws ArrayIndexOutOfBoundsException
	{
		getMutableView().insertChild(this, index, child);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#getChild(String)
	 */
	public iNamedObject getChild(String child_name)
	{
		List l= getView().getChildren(this);
		for (int i= 0; i < l.size(); i++)
		{
			Object o= l.get(i);
			if (o instanceof iNamedObject)
			{
				if (((iNamedObject) o).getName().equals(child_name))
				{
					return ((iNamedObject) o);
				}
			}
		}
		return null;
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#getChild(int)
	 */
	public iNamedObject getChild(int index) throws ArrayIndexOutOfBoundsException
	{
		return (iNamedObject) getView().getChildren(this).get(index);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#hasChild(ShimonHeadController.innards.iNamedObject)
	 */
	public boolean hasChild(iNamedObject child)
	{
		return getView().getChildren(this).contains(child);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#hasChild(String)
	 */
	public boolean hasChild(String child)
	{
		return getChild(child) != null;
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#removeChild(ShimonHeadController.innards.iNamedObject)
	 */
	public iNamedObject removeChild(iNamedObject child_to_remove) throws NoSuchElementException
	{
		return (iNamedObject) getMutableView().removeChild(this, child_to_remove);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#removeChild(String)
	 */
	public iNamedObject removeChild(String child_to_remove) throws NoSuchElementException
	{
		iNamedObject r= getChild(child_to_remove);
		return removeChild(r);
	}

	/**
	 * @see ShimonHeadController.innards.iNamedGroup#getNumChildren()
	 */
	public int getNumChildren()
	{
		return getView().getChildren(this).size();
	}

	/**
	 * @see ShimonHeadController.innards.iNamedObject#getName()
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @see ShimonHeadController.innards.iNamedObject#getKey()
	 */
	public Key getKey()
	{
		if (key == null)
		{
			key = new Key(this.getName());
		}
		return key;
	}

	/**
	 * does nothing - this is up to the parent's hierarchical view to perform the relevant connection
	 * upon the removeChild call below this
	 */
	public void setParent(iNamedGroup parent)
	{
	}

	/**
	 * does nothing - this is up to the parent's hierarchical view to perform the relevant connection
	 * upon the removeChild call below this
	 */
	public void removeParent(iNamedGroup parent)
	{
	}

	/**
	 * @see ShimonHeadController.innards.iNamedObject#getParent()
	 */
	public iNamedGroup getParent()
	{
		List l= getView().getParents(this);
		return l.size() > 0 ? (iNamedGroup) l.get(0) : null;
	}
	
	public List getParents()
	{
		List l= getView().getParents(this);
		return l;
	}

	/**
	 * 	a convience class for joining contexts with the creation context of this class.
	 * see marc.context.T_MultipleParents
	*/
	public class JoinWithCreationContext extends ContextTreeUtil.JoinWith
	{
		public JoinWithCreationContext()
		{
			super(creationContext);
		}
	}
	
	public String toString()
	{
		return this.getClass()+"<"+this.getName()+">";
	}
	
	public List sortChildren(Comparator c)
	{
		return getMutableView().sortChildren(this, c);
	}
	public List sortChildrenAlphabetically()
	{
		return sortChildren(new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				iNamedObject no1= (iNamedObject) o1;
				iNamedObject no2= (iNamedObject) o2;
				return no1.getName().compareTo(no2.getName());
			}
		});
	}
}
