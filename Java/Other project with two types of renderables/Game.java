import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Game extends KeyAdapter
{
	public static final int WIDTH_WINDOW=500;
	public static final int HEIGHT_WINDOW=500;
    private static double OneSec=1000.000000000;

	public static int WIDTH_CANVAS;
	public static int HEIGHT_CANVAS;
	
	private JFrame frame;
	private MiPanel panel;
	
	private JLabel updatesPerSecondLabel;
	
	private int UpdatesPerSecond=5;
	private double timeBetweenUpdates=OneSec/UpdatesPerSecond;

    private boolean parar=false;
    private Random random=new Random();
    
    private static int Renders=0;
    private UpdateFPS FPSThread;
    
    
	public void start()
	{
		frame = new JFrame("Cuadrado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        frame.setVisible(true);
        frame.setResizable(false);
        
        frame.addKeyListener(this);
        
         panel=new MiPanel();
        
      
        updatesPerSecondLabel=new JLabel("Updates per second "+UpdatesPerSecond+" Time between Updates: 200ms", SwingConstants.CENTER);
        
        //CalculateTimeUpdateLabel();
        
        frame.setLayout(new BorderLayout());
        frame.add(updatesPerSecondLabel, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER); 
               
        
        new GetSizeThread(1000).start();
        (FPSThread=new UpdateFPS()).start();

        long lastUpdate=System.currentTimeMillis();
        
        
        while(!parar)
        {
        	long actual=System.currentTimeMillis();
        	double dif=actual-lastUpdate;
        	if(dif>timeBetweenUpdates)
        	{
        		update(dif/OneSec);
        		lastUpdate=actual;
        	}
        	
        	render(dif/timeBetweenUpdates);
        }
        FPSThread.parar();
	}
	
	
    public void render(double t)
    {
    	Renders++;
    	t=(t>1?1:(t<0)?0:t);
    	panel.render(t);   	
    	frame.repaint();
    }
    
    public void update(double DeltaTime)
    {
		panel.update(DeltaTime);    	
    }
	

    @Override
    public void keyPressed(KeyEvent e) 
    {
    	switch (e.getKeyCode()) {
    	case KeyEvent.VK_ESCAPE:
    		parar=true;
    		break;
    		
		case KeyEvent.VK_C:
			panel.ClearObjects();
			System.out.println("Clear Objects");
			break;

		case KeyEvent.VK_PLUS:
		case KeyEvent.VK_ADD:
			UpdatesPerSecond++;
			CalculateTimeUpdateLabel();
			break;

		case KeyEvent.VK_MINUS:
		case KeyEvent.VK_SUBTRACT:
			UpdatesPerSecond--;
			if(UpdatesPerSecond<1) UpdatesPerSecond=1;
			CalculateTimeUpdateLabel();
			break;
			
		case KeyEvent.VK_ENTER:
			panel.addRenderable(new MiCuadradoInterpolado(WIDTH_WINDOW/2, HEIGHT_WINDOW/2, Aleatorio(100, -100), Aleatorio(100, -100), 50, 50, new Color(Aleatorio(255, 170), Aleatorio(255, 170), Aleatorio(255, 170))));
			break;
			
		case KeyEvent.VK_SPACE:
			panel.addRenderable(new MiCuadrado(WIDTH_WINDOW/2, HEIGHT_WINDOW/2, Aleatorio(100, -100), Aleatorio(100, -100), 50, 50, new Color(Aleatorio(150, 0), Aleatorio(150, 0), Aleatorio(150, 0))));
			break;
				
		default:
			System.out.println("Default");
			break;
		}
    }
    
    private int Aleatorio(int max, int min)
    {
    	return random.nextInt(max-min)+min;
    }
    
    private void CalculateTimeUpdateLabel()
    {
    	timeBetweenUpdates=OneSec/UpdatesPerSecond;
		updatesPerSecondLabel.setText("Updates per second "+UpdatesPerSecond+" Time between Updates: "+String.format("%.2f", timeBetweenUpdates)+"ms");
    }
    
    
    private class UpdateFPS extends Thread
    {
    	public boolean stop=false;
    	public void run()
    	{
    		do
    		{
    			if(frame!=null) frame.setTitle("FPS: "+Renders);
	    		Renders=0;
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					stop=true;
				}   		
    		}while(!stop);
    	}
    	public void parar() {stop=true;}
    }
    
    private class GetSizeThread extends Thread
    {
    	int t;
    	public GetSizeThread(int time) {t=time;}
    	public void run()
    	{
    		try {
				Thread.sleep(t);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            WIDTH_CANVAS=(int)panel.getBounds().getWidth();
            HEIGHT_CANVAS=(int)panel.getBounds().getHeight();
    	}
    }

}
