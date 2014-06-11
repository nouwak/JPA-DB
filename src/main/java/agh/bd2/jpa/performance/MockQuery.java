/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agh.bd2.jpa.performance;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Szimin
 */
public class MockQuery extends QueryTester{
    private int time;

    public MockQuery(EntityManager entityManager, int time) {
        super("Mock test "+String.valueOf(time), entityManager);
        this.time=time;
    }

    @Override
    public void executeQuery() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(MockQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printResult() {
        System.out.println(getName());
    }
    
}
