import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Main extends KeyAdapter
{
	public static final int WIDTH_WINDOW=500;
	public static final int HEIGHT_WINDOW=280;
	public static final int MIN_UPDATES=1;
	public static final int MAX_UPDATES=60;
    private static double OneSec=1000.000000000;

	public static int WIDTH_CANVAS=500;
	public static int HEIGHT_CANVAS=200;
	
	private JFrame frame;
	private MiPanel panel;
	
	private JLabel updatesPerSecondLabel;
	
	private int UpdatesPerSecond=8;
	private double timeBetweenUpdates=OneSec/UpdatesPerSecond;

    private boolean parar=false, interpolatedRendering=false;
    private Random random=new Random();
       

    public static void main(String arg[]) 
    {
       new Main().start();
       System.exit(0);
    }
    
    
    private void addRectangles()
    {		
    	for(int i=0; i<20; i++)
    		panel.addMiCuadrado(new MiCuadrado(WIDTH_WINDOW/2, HEIGHT_WINDOW/2, Aleatorio(25, 50), Aleatorio(25, 50), Aleatorio(-100, 100), Aleatorio(-100, 100), new Color(Aleatorio(0, 255), Aleatorio(0, 255), Aleatorio(0, 255))));
	}
    
	public void start()
	{
		frame = new JFrame("Cuadrado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        frame.setVisible(true);
        frame.setResizable(false);
        
        frame.addKeyListener(this);
        
         panel=new MiPanel();
        
      
        updatesPerSecondLabel=new JLabel("Interpolated render: false 8 Updates/s Time between Updates: 125,00ms", SwingConstants.CENTER);
      
        
        //CalculateTimeUpdateLabel();
        
        frame.setLayout(new BorderLayout());
        frame.add(updatesPerSecondLabel, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER); 
               
        
        new GetSizeThread(1000).start();
        addRectangles();

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
        	if(!interpolatedRendering)
        		render(0);
        	else
        		render(dif/timeBetweenUpdates);
        }
	}
	
	
    public void render(double t)
    {
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

		case KeyEvent.VK_PLUS:
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
			if(UpdatesPerSecond<MAX_UPDATES)
				UpdatesPerSecond++;
			CalculateTimeUpdateLabel();
			break;

		case KeyEvent.VK_MINUS:
		case KeyEvent.VK_SUBTRACT:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_DOWN:
			if(UpdatesPerSecond>MIN_UPDATES)
				UpdatesPerSecond--;
			CalculateTimeUpdateLabel();
			break;


		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_I:
		case KeyEvent.VK_ENTER:
			this.interpolatedRendering=!this.interpolatedRendering;
			CalculateTimeUpdateLabel();
			break;
			
		default:
			System.out.println("Default");
			break;
		}
    }
    
    private int Aleatorio(int min, int max)
    {
    	return random.nextInt(max-min)+min;
    }
    
    
    private String getStringUpdatesSecondLabel()
    {
     	return ("Interpolated rendering: "+this.interpolatedRendering+" "+UpdatesPerSecond+" Updates/s Time between Updates: "+String.format("%.2f", timeBetweenUpdates)+"ms");
        
    }
    
    private void CalculateTimeUpdateLabel()
    {
    	timeBetweenUpdates=OneSec/UpdatesPerSecond;
		updatesPerSecondLabel.setText(getStringUpdatesSecondLabel());
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
