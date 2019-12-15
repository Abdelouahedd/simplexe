package classes;

public class Solveur {
    public Contraints ctrs;
    public Solveur(Contraints contraints){this.ctrs =  contraints;}

    public boolean decision(){
        boolean phase = false;
        for (int i=0;i<this.ctrs.getCot().size()-1;i++){
            if(this.ctrs.getCot().get(i).getOp().equals(">=") || this.ctrs.getCot().get(i).getOp().equals("=")){
                phase = true;
            }
        }
     return phase;
    }
}
