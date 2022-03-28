var interpolate=false;
var time_for_update=1;
const context=document.getElementById('mycanvas').getContext('2d');
const interpolationInput=document.getElementById('interpolation');
const updatesInput=document.getElementById('time_updates');
const updatesSpan=document.getElementById('updates_per_second');
const updatesBetweenSpan=document.getElementById('second_between_updates');

const WIDTH_CANVAS=500;
const HEIGHT_CANVAS=500;

function drawRect(color, pointX, pointY, width, height)
{	
    context.fillStyle=color;
    context.fillRect(pointX, pointY, width, height);
}


class MyCircle
{
  constructor(x, y, width, height, velx, vely, color)
  {
    this.PointActual={x,y};
    this.PointAnterior={x,y};
    this.Velocity={x:velx, y:vely};
    this.Dimensions={width, height};
    this.color=color;
  }

  Render(percent)
  {
    //let renderX=this.PointActual.x, renderY=this.PointActual.y;
    if(!interpolate)
    {
      percent=0;
    }
    let renderX=percent*this.PointActual.x+(1-percent)*this.PointAnterior.x;
    let renderY=percent*this.PointActual.y+(1-percent)*this.PointAnterior.y;

    drawRect(this.color, renderX,renderY, this.Dimensions.width,this.Dimensions.height);
  }

  Update(deltaTime)
  {
    this.PointAnterior.x=this.PointActual.x;
    this.PointAnterior.y=this.PointActual.y;

    this.PointActual.x+=this.Velocity.x*deltaTime;
    this.PointActual.y+=this.Velocity.y*deltaTime;

    //Comprobamos que no se salga de los límites
    if(this.PointActual.y<=0 && this.Velocity.y<0)
    {
		//this.PointActual.y=0; 
		this.Velocity.y*=-1;
    }else if(this.PointActual.y>=(HEIGHT_CANVAS-this.Dimensions.height)&& this.Velocity.y>0) 
    {    
		//this.PointActual.y=(HEIGHT_CANVAS-this.Dimensions.height);
		this.Velocity.y*=-1;
	}


    //Comprobamos que no se salga de los límites
    if(this.PointActual.x<=0 && this.Velocity.x<0)
    {
		//this.PointActual.x=0; 
		this.Velocity.x*=-1;
    }else if(this.PointActual.x>=(WIDTH_CANVAS-this.Dimensions.width) && this.Velocity.x>0) 
    {    
		//this.PointActual.x=(WIDTH_CANVAS-this.Dimensions.width);
		this.Velocity.x*=-1;						
	}
  }				
}

function GenerateRandom(min, max)
{
  return Math.random()*(max-min)+min;
}			

const minVel=-100, maxVel=100;

var Circles=[
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'yellow'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'green'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'orange'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel),  'black'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel),  'blue'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel),  'pink'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'purple'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'red'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'aquamarine'), 
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'grey'),
  
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'lime'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'maroon'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'navy'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'fuchsia'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'lightpink'),
  new MyCircle(WIDTH_CANVAS/2, HEIGHT_CANVAS/2, GenerateRandom(25, 50), GenerateRandom(25, 50), GenerateRandom(minVel, maxVel), GenerateRandom(minVel, maxVel), 'olive')
];

var cantCircles=Circles.length;

function Update(deltaTime)
{
  //console.log("Update "+deltaTime);
  interpolate=interpolationInput.checked;
  time_for_update=1/(updatesInput.value*1);
  updates_per_second.innerHTML=updatesInput.value;
  updatesBetweenSpan.innerHTML=(time_for_update*1000).toFixed(2);
  for(let i=0; i<cantCircles; i++)
    Circles[i].Update(deltaTime);
}

function Render(percent)
{
  //console.log("Render "+percent);
  if(percent<0)percent=0;
  else if(percent>1) percent=1;

  //context.clearRect(0, 0, WIDTH_CANVAS, HEIGHT_CANVAS);
  
   drawRect("white", 0, 0, WIDTH_CANVAS, HEIGHT_CANVAS);
	
  for(let i=0; i<cantCircles; i++)
    Circles[i].Render(percent);			
}			

Update(0);
Render(0);

var lastUpdate=new Date().getTime();
var actual=lastUpdate;


function Gameloop()
{
  let actual=new Date().getTime();
  let secs=(actual-lastUpdate)/1000;
  if((secs)>time_for_update)
  {
    Render(1);
    Update(secs);
    lastUpdate=actual;
  }else
    Render( secs/time_for_update);

  setTimeout(Gameloop, 0);
}			
Gameloop();	

document.body.onkeydown = function(event)
{
	switch(event.which)
	{
		case 107: // + Numpad
		case 187: // +
		case 39: // Right Key
		case 38: // Up Key
			if(updatesInput.value*1<updatesInput.max*1)
				updatesInput.value++;
			break;
			
		case 109: // - Numpad
		case 189: // -
		case 40: // Left Key
		case 37: // Down Key
			if(updatesInput.value>updatesInput.min*1)
				updatesInput.value--;
		
			break;
		
		case 32: // Space
		case 73: // I
		case 13: // Enter
			interpolationInput.checked=!interpolationInput.checked;
			break;
			
			
			
			
	}
};

//Creado por juanpomares  ¯\_(ツ)_/¯