/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/**
 *
 * @author Yeoh Hong Jing
 */
public class Synapse {
    public int d,t;
    public Neuron origin;
    public Neuron destination;
    public int lifeTime;

    public Synapse(Neuron origin, Neuron destination,int d, int t ,int lifeTime ) {
        this.d = d;
        this.t = t;
        this.origin = origin;
        this.destination = destination;
        this.lifeTime=lifeTime;
    }
    public String toString(){
        return "origin: "+origin.id+"\ndestination: "+destination.id+"\nd: "+d+"\nt: "+t+"\nLife Time: "+lifeTime;
    }
    public boolean equals(Synapse s){
        if(d==s.d && t==s.t && origin.id==s.origin.id && destination.id==s.destination.id && lifeTime==s.lifeTime){
            return true;
        }
        else return false;
    }
    
}
