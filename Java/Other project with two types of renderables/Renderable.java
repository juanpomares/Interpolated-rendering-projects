import java.awt.Graphics;

public interface Renderable 
{
	public abstract void pintar(Graphics g);    
    public abstract void render(double t);
    public abstract void update(double deltaTime);

}
