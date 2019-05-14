/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Yeoh Hong Jing
 */
public class Neuron {
    public int numOfLinks;
    public int id;
    public ArrayList<Synapse> synapse=new ArrayList<>();
    public Neuron(int id) {
        this.id=id;         
        }
    public Neuron(int id, int numOfLinks){
        Scanner s=new Scanner(System.in);
        this.id=id;
        this.numOfLinks=numOfLinks;
        for (int i = 0; i < numOfLinks; i++) {
            System.out.print("Neuron id1: ");
            int id1=s.nextInt();
            System.out.print("d:");
            int d=s.nextInt();
            System.out.print("t:");
            int t=s.nextInt();
            System.out.print("Life Time: ");
            int lifeTime=s.nextInt();
            Synapse sy=new Synapse(this,new Neuron(id1),d,t,lifeTime);
            synapse.add(sy);   
            System.out.println(synapse.get(i).toString());
        }
    }
    
    public boolean isEndNode(){
        if(numOfLinks==0)
            return true;
        else 
            return false;
    }
    
}
