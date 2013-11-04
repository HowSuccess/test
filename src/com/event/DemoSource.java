package com.event;

import java.util.Enumeration;
import java.util.Vector;

public class DemoSource {

    private Vector repository = new Vector();

    DemoListener dl;

    public DemoSource()

    {



    }

    public void addDemoListener(DemoListener dl)

    {

           repository.addElement(dl);

    }

    public void notifyDemoEvent()

    {

    	Enumeration nubs = repository.elements();

           while(nubs.hasMoreElements())

           {

                 dl = (DemoListener)nubs.nextElement();

                 dl.demoEvent(new DemoEvent(this));

           }

    }
}
