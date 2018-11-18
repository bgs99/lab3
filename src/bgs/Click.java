package bgs;

public class Click {
    private PointRep pr;
    private Current cur;
    public void setPr(PointRep pr) {
        this.pr = pr;
    }

    public PointRep getPr() {
        return pr;
    }

    public Current getCur() {
        return cur;
    }

    public void setCur(Current cur) {
        this.cur = cur;
    }

    private double x = 100, y = 100;
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double val){
        x = val;
    }
    public void setY(double val){
        y = val;
    }
    public void add(){
        double rx = (x-150) * cur.getR()/100;
        double ry = (150-y) * cur.getR()/100;
        pr.add(rx, ry);
    }
}
