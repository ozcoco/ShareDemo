package com.xdynamics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    class A {

        {

            System.out.println("11111111111111111111");

        }

        public A() {

            System.out.println("xxxxxxxxxxxxxxxxx");
            System.out.println("--------" + hashCode());

        }


        void a(){}
        void a1(){}
        void a2(){}


    }


    @Test
    public void test() {


        A a = new A();

        a.equals();

        new A() {
            {

                a();

                a1();

                a2();

            }


            {



            }

            {



            }
        };


    }


}