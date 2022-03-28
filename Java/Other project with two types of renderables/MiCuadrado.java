import java.awt.Color;
import java.awt.Graphics;

public class MiCuadrado implements Renderable
{
	int posx, posy;
	int tamx, tamy;	
	int velx, vely;
	Color color;
	
	public MiCuadrado(int px, int py, int vx, int vy, int tx, int ty, Color newColor)
	{
		posx=px;
		posy=py;
		if(vx<5 && vx>-5) vx=5;
		if(vy<5 && vy>-5) vy=-5;
		
		System.out.println(""+vx+" "+vy);
		
		velx=vx; vely=vy;
		tamx=tx; tamy=ty;
		color=newColor;
	}

    public void pintar(Graphics g) 
    {        
    	g.setColor(color);
        g.fillRect(posx, posy, tamx, tamy);
    }
    
    public void render(double t)    {    }
    
    public void update(double deltaTime)
    {  	
    	if((posx+tamx)>=Game.WIDTH_CANVAS || posx<=0)
    		velx*=-1;
    	
    	if((posy+tamy)>=Game.HEIGHT_CANVAS|| posy<=0)
    		vely*=-1;
    	
    	posx+=deltaTime*velx;
    	posy+=deltaTime*vely;      	
    }
}