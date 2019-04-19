package ShimonHeadController.innards.namespace.factory;

import java.io.Serializable;

/**
 * this is the interface for the production rules that are used by Factories.
 * returns a new Object based on the specified parameters.
 * @see Factory
 */
public interface iProduction extends Serializable
{
    public Object produce(Object params);   
}


