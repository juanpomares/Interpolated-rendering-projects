import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MiPanel extends JPanel
{
	private ArrayList<Renderable> objetos=new ArrayList<Renderable>();
	

	
	public synchronized void ClearObjects()
	{
		objetos.clear();
	}
	
    public synchronized void render(double t)
    {
    	for(Renderable objeto : objetos)
    		objeto.render(t);
    }
    
    public synchronized void update(double timedelta)
    {
    	for(Renderable objeto : objetos)
    	{	
    		objeto.update(timedelta);
    	}
    }    

    public synchronized void paint(Graphics g) 
    {     
    	for(Renderable objeto : objetos)
    		objeto.pintar(g);
    }
    

    public synchronized void addRenderable(Renderable r) 
    {     
    	objetos.add(r);
    }
    
}
