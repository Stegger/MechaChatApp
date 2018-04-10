/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.bll.util;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author pgn
 */
public class EndlessIterator<E> implements Iterator<E>
{

    private List<E> source;
    private Iterator<E> currentIterator;
    
    public EndlessIterator(List<E> source)
    {
        this.source = source;
        currentIterator = source.iterator();
    }

    @Override
    public boolean hasNext()
    {
        return !source.isEmpty();
    }

    @Override
    public E next()
    {
        currentIterator = currentIterator.hasNext() ? currentIterator : source.iterator();
        return currentIterator.next();
    }

    
    
}
