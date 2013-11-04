package com.event;

public class TestDemo

{

       DemoSource ds;

 

       public TestDemo()

       {

              try{

                    ds = new DemoSource();

                    DemoListener1 l1 = new DemoListener1();

                    ds.addDemoListener(l1);


                    ds.addDemoListener(new DemoListener(){
                               public void demoEvent(DemoEvent event){
                                         System.out.println("Method come from ƒ‰√˚¿‡...");
                               }
                       });

                    ds.notifyDemoEvent();

 

              }catch(Exception ex)

              {ex.printStackTrace();}

       }

 

       public static void main(String args[])

       {

              new TestDemo();

       }

}