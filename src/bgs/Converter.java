package bgs;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

public class Converter {

    Current cr;

    public Current getCr() {
        return cr;
    }

    public void setCr(Current cr) {
        this.cr = cr;
    }

    public static String getColor(Point p){
        return p.inside ? "green" : "red";
    }
    public int getX(Point p){
        double nxk = p.x * 100 / cr.getR();
        double nx = 150+nxk;
        return (int)Math.round(nx);
    }
    public int getY(Point p){
        double nyk = p.y * 100 / cr.getR();
        double ny = 150-nyk;
        return (int)Math.round(ny);
    }
}
