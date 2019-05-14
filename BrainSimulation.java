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
public class BrainSimulation {
    public int numberOfNeuron;
    public int numberOfMessage;
    public int[] originMsg;
    public int[] destiMsg;
    public Neuron[] neuron;
    ArrayList<ArrayList<Synapse>>pathCollection=new ArrayList<>();
    ArrayList<Synapse> path=new ArrayList<>();
    public void init(){
        Scanner s=new Scanner(System.in);
        numberOfNeuron=s.nextInt();
        neuron=new Neuron[numberOfNeuron];
        for (int i = 0; i < numberOfNeuron; i++) {
            System.out.print("Neuron ID:");
            int id=s.nextInt();
            System.out.print("Number of Connection: ");
            int n=s.nextInt();
            neuron[i]=new Neuron(id,n);
        }
        System.out.print("Number of Message: ");
        numberOfMessage=s.nextInt();
        originMsg=new int[numberOfMessage];
        destiMsg=new int[numberOfMessage];
        for (int i = 0; i < numberOfMessage; i++) {
            System.out.print("Origin: ");
            originMsg[i]=s.nextInt();
            System.out.print("Destination");
            destiMsg[i]=s.nextInt();       
            
        }
        bidirectional();
        
        ArrayList<ArrayList<Synapse>> pathCollection=new ArrayList<>();
        ArrayList<ArrayList> path=new ArrayList<>();
        ArrayList<ArrayList> bestPath=new ArrayList<>();
        for (int i = 0; i < numberOfMessage; i++) {          
            findPath(originMsg[i],destiMsg[i]); 
            pathCollection=new ArrayList<>(this.pathCollection);
            this.pathCollection.clear();
            path.add(pathCollection); 
            if(pathCollection.size()>0){
            lifeTime(bestPath(pathCollection));
            bestPath.add(bestPath(pathCollection));        
            }            
        }
        for (int j = 0; j < bestPath.size(); j++) {
            System.out.println(j+":");
            System.out.println(bestPath.get(j));           
        }
     
    }
    int i=-1;
        public void lifeTime(ArrayList<Synapse> e){
            for (int j = 0; j < e.size(); j++) {
                e.get(j).lifeTime--;
                if(e.get(j).lifeTime==0){
                    Synapse opposite=new Synapse(e.get(j).destination,e.get(j).origin,e.get(j).d,e.get(j).t,e.get(j).lifeTime);
                    e.get(j).destination.synapse.remove(opposite);
                    e.get(j).origin.synapse.remove(e.get(j));
                }
            }
        }

        public void findPath(int originID,int destiID){
            i++;
            System.out.println(originID);
            System.out.println(i);
            int index=-1;
            for (int j = 0; j < neuron.length; j++) {
                if(neuron[j].id==originID){
                    index=j;
                }            
            }
            boolean passed=false;
            for (int j = 0; j < path.size(); j++) {
                if(path.get(j).origin.id==neuron[index].id){
                    passed=true;
                }      
            }
            Neuron current=neuron[index];
            ArrayList <Synapse> synapse=new ArrayList<>(current.synapse);
            if(originID!=destiID && !passed){
                for (int j = 0; j < synapse.size(); j++) {
                    path.add(i,synapse.get(j));
                    current=synapse.get(j).destination;
                    System.out.println(current.id);
                    findPath(current.id,destiID);             
                }
            }
            if(originID==destiID){
                ArrayList<Synapse> path=new ArrayList<>(this.path);
                pathCollection.add(path);
            }

            i--;
            System.out.println(i);
            if(i>=0){
                path.remove(i);
            }
            
        }
        
        public void bidirectional(){
            boolean exist=false;
            for (int j = 0; j < neuron.length; j++) {
                for (int k = 0; k < neuron[j].synapse.size(); k++) {
                    for (int l = 0; l <neuron.length ; l++) {
                        if(neuron[l].id==neuron[j].synapse.get(k).destination.id){
                           Synapse newSynapse= new Synapse(neuron[l],neuron[j].synapse.get(k).origin,neuron[j].synapse.get(k).d,neuron[j].synapse.get(k).t,neuron[j].synapse.get(k).lifeTime);
                            for (int m = 0; m < neuron[l].synapse.size(); m++) {
                                if(neuron[l].synapse.get(m).equals(newSynapse))
                                    exist=true;                               
                            }
                            if(!exist){
                                neuron[l].synapse.add(newSynapse);
                            }
                            exist=false;
                        }
                        
                    }
                    
                }                
            }
        }
        
        public ArrayList<Synapse> bestPath(ArrayList<ArrayList<Synapse>> e){
            int [] sumOfD=new int[e.size()];
            int [] sumOfT=new int[e.size()];
            for (int j = 0; j < e.size(); j++) {
                int sumD=0;
                int sumT=0;
                for (int k = 0; k < e.get(j).size(); k++) {
                    sumD+=e.get(j).get(k).d;
                    sumT+=e.get(j).get(k).t;                    
                }
                sumOfD[j]=sumD;
                sumOfT[j]=sumT;
            }
            int min=sumOfT[findMin(sumOfT)];
            int duplicate=0;
            ArrayList<Integer> duplicateOfT=new ArrayList<>();
            for (int j = 0; j < sumOfT.length; j++) {
                if(sumOfT[j]==min){
                    duplicate++;
                    duplicateOfT.add(j);
                } 
            }
            if(duplicate>1){
                int[]duplicateT=new int[duplicate];
                for (int j = 0; j < duplicate; j++) {
                    duplicateT[j]=sumOfD[duplicateOfT.get(j)];
                }
                int minD=duplicateT[findMin(duplicateT)];
                for (int j = 0; j < sumOfD.length; j++) {
                    if (sumOfD[j]==minD && sumOfT[j]==min){
                        return e.get(j);
                    }
                    
                }
            }
            return e.get(findMin(sumOfT));
        }
        
        public int findMin(int[] arr){
            int min=arr[0];
            int minIndex=0;
            for (int j = 1; j < arr.length; j++) {
                if(min>arr[j]){
                    min=arr[j];
                    minIndex=j;
                }              
            }
            return minIndex;
        }
}
