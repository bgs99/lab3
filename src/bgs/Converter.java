package bgs;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Converter {

    @ManagedProperty(value = "#{current.r}")
    int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public static String getColor(Point p){
        return p.inside ? "green" : "red";
    }
    public int getX(Point p){
        double nxk = p.x * radius * 100 / p.r;
        double nx = 150+nxk;
        return (int)Math.round(nx);
    }
    public int getY(Point p){
        double nyk = p.y * radius * 100 / p.r;
        double ny = 150-nyk;
        return (int)Math.round(ny);
    }
}
