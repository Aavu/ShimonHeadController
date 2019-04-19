package ShimonHeadController.innards.namespace.rhizome;

import ShimonHeadController.innards.namespace.rhizome.iTopologicalView.iDispatchesTopolgyChange;

/**
 * @author marc
 * Created on May 24, 2003
 */
public class TopologicalViewUtils
{

	static public class Synchronize implements iTopologicalView.iHandlesTopologyChange
	{
		iTopologicalView.Mutable dest;
		public Synchronize(iDispatchesTopolgyChange from, iTopologicalView.Mutable to)
		{
			from.addHandler(this);
			this.dest= to;
		}

		/*
		 * @see ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iHandlesTopologyChange#notifyInsertChild(ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iDispatchesTopolgyChange, java.lang.Object, int, java.lang.Object)
		 */
		public void notifyInsertChild(iDispatchesTopolgyChange from, Object to, int atIndex, Object child)
		{
			dest.insertChild(to, atIndex, child);
		}
		/*
		 * @see ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iHandlesTopologyChange#notifyNodeAdded(ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iDispatchesTopolgyChange, java.lang.Object)
		 */
		public void notifyNodeAdded(iDispatchesTopolgyChange from, Object added)
		{
		}
		/*
		 * @see ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iHandlesTopologyChange#notifyNodeRemoved(ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iDispatchesTopolgyChange, java.lang.Object)
		 */
		public void notifyNodeRemoved(iDispatchesTopolgyChange from, Object removed)
		{
		}
		/*
		 * @see ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iHandlesTopologyChange#notifyRemoveChild(ShimonHeadController.innards.namespace.rhizome.iHierarchicalView.iDispatchesTopolgyChange, java.lang.Object, java.lang.Object)
		 */
		public void notifyRemoveChild(iDispatchesTopolgyChange from, Object fromC, Object child)
		{
			dest.removeChild(fromC, child);
		}
	}

	//	/**
	//		 * 'freezes' the topology of a collection of nodes. One can then thaw a topology to change the topology
	//		 * of the noted nodes to be exactly the same as it used to be
	//		 */
	//		public FrozenTopology freezeTopology(final BasicHierarchicalView view, final Collection ofNodes)
	//		{
	//			final Map nodesToParents = new HashMap();
	//			final Map nodesToChildren = new HashMap();
	//			
	//			for (Iterator i= ofNodes.iterator(); i.hasNext();)
	//			{
	//				Object element= (Object) i.next();
	//				nodesToParents.put(element, view.getParents(element));
	//				nodesToChildren.put(element, view.getChildren(element));
	//			}
	//			
	//			return new FrozenTopology()
	//			{
	//			
	//				public void thaw(Resolver resolver)
	//				{
	//					
	//				}
	//			};
	//		}
	//	
	//		public interface FrozenTopology
	//		{
	//			public void thaw(Resolver resolver)
	//		}
	//	
	//		public interface Resolver
	//		{
	//			public Object resolveExternalNode(Object o);
	//		}

}
