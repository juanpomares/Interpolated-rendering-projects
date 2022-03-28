import java.awt.Color;
import java.awt.Graphics;


public class MiCuadrado
{
	double posx_ant, posy_ant;
	double posx, posy;
	int tamx, tamy;
	Color color;
	int posx_render, posy_render;
	int velx, vely;
	public MiCuadrado(int px, int py, int tx, int ty, int vx, int vy, Color newColor)
	{
		posx=posx_ant=posx_render=px;
		posy=posy_ant=posy_render=py;
		
		if(vx<5 && vx>-5) vx=-5;
		if(vy<5 && vy>-5) vy=5;

		System.out.println(""+vx+" "+vy);
		
		velx=vx; vely=vy;tamx=tx; tamy=ty;
		color=newColor;
	}

    public void pintar(Graphics g) 
    {        
    	g.setColor(color);
        g.fillRect(posx_render, posy_render, tamx, tamy);
    }
    
    public void render(double t)
    {
    	posx_render=(int) (posx*t+(1-t)*posx_ant);
    	posy_render=(int) (posy*t+(1-t)*posy_ant);
    }
    
    public void update(double deltaTime)
    {
    	posx_ant=posx;
    	posy_ant=posy;    	
    	
    	if((posx+tamx)>=Main.WIDTH_CANVAS || posx<=0)
    		velx*=-1;
    	
    	if((posy+tamy)>=Main.HEIGHT_CANVAS || posy<=0)
    		vely*=-1;
    	
    	posx+=deltaTime*velx;
    	posy+=deltaTime*vely;      	
    }
}
