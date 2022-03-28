import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MiPanel extends JPanel
{
	private ArrayList<MiCuadrado> objetos=new ArrayList<MiCuadrado>();
	

	
	public synchronized void ClearObjects()
	{
		objetos.clear();
	}
	
    public synchronized void render(double t)
    {
    	for(MiCuadrado objeto : objetos)
    		objeto.render(t);
    }
    
    public synchronized void update(double timedelta)
    {
    	for(MiCuadrado objeto : objetos)
    	{	
    		objeto.update(timedelta);
    	}
    }    

    public synchronized void paint(Graphics g) 
    {     
    	for(MiCuadrado objeto : objetos)
    		objeto.pintar(g);
    }
    

    public synchronized void addMiCuadrado(MiCuadrado r) 
    {     
    	objetos.add(r);
    }
    
}
