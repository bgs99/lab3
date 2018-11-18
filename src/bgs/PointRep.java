package bgs;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import javax.transaction.*;

public class PointRep {
    // Injected database connection:
    @PersistenceContext(unitName = "Points")
    private EntityManager em;

    private Current cur;

    public Current getCur(){
        return cur;
    }

    public void setCur(Current val){
        cur = val;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    private String session;
    @PostConstruct
    void init(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession hsession = (HttpSession) fCtx.getExternalContext().getSession(true);
        session = hsession.getId();
    }
    @Resource
    private UserTransaction utx;
    // Stores a new guest:

    private boolean checkCoords(double x, double y){
        int r = cur.getR();
        if(2*x > r || x < -r || 2*Math.abs(y) > r)
            return false;
        if(x > 0 && y > 0)
            return false;
        if(x >= 0 && y <= 0)
            return x*x+y*y <= r*r/4.0;
        if(x <= 0 && y <= 0)
            return true;
        if(x <= 0 && y > 0)
            return y <= x + r/2.0;
        return false;
    }

    public void add(double x, double y){
        addMessage("Add entity");
        try{
            Point pt = new Point(x, y, cur.getR(), checkCoords(x, y), session);
            utx.begin();
            em.persist(pt);
            em.flush();
            utx.commit();
        } catch (Exception e){
            addMessage("Cannot add");
        }
    }

    private void clear(){
        addMessage("clear entity");
        try {
            utx.begin();
            if (session == null)
                return;
            TypedQuery<Point> query = em.createQuery("DELETE FROM Point p WHERE p.session = :session", Point.class)
                    .setParameter("session", session);
            query.executeUpdate();
            utx.commit();
        } catch (Exception e){
            System.out.println("Cannot remove!");
        }
    }
    public long getC(){
        addMessage("get c");
        return session.hashCode();
    }
    public int getSize(){
        try {
            List<Point> ps = getAllPoints();
            return ps.size();
        } catch (Exception e){
            return -1;
        }
    }
    // Retrieves all the guests:
    public List<Point> getAllPoints() {
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p WHERE p.session = :session ORDER BY p.id", Point.class)
                .setParameter("session", session);
        return query.getResultList();
    }
    @PreDestroy
    private void cleanup(){
        clear();
    }

}