/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.pivotal.coffeemachine;

/**
 *
 * @author shahs
 */
public class InvertoryTestImpl extends InventoryTests{

    private static InventoryImpl invertoryInstance = null;

    @Override
    protected Inventory getInventory() {
        if (invertoryInstance == null) {
            invertoryInstance = new InventoryImpl();
        }
        return invertoryInstance;
    }

}
