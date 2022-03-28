# Importing the library
import pygame
import random
import time

from pygame import KEYDOWN

interpolate = True
HEIGHT_CANVAS = 500
WIDTH_CANVAS = 500

UPDATES_PER_SECOND = 8
TIME_BETWEEN_UPDATES = 1.0/UPDATES_PER_SECOND

# Initializing surface
screen = pygame.display.set_mode((WIDTH_CANVAS, HEIGHT_CANVAS))



class MyRectangle:
    def __init__(self, posx, posy, width, height, velx, vely, colorR, colorG, colorB):
        self.currentPositionX = self.previousPositionX = posx
        self.currentPositionY = self.previousPositionY = posy
        self.width = width
        self.height = height
        self.velocityX = velx
        self.velocityY = vely
        self.color = (colorR, colorG, colorB)

    def render(self, percent: float):
        renderx = percent * self.currentPositionX + (1 - percent) * self.previousPositionX
        rendery = percent * self.currentPositionY + (1 - percent) * self.previousPositionY

        pygame.draw.rect(screen, self.color, (renderx, rendery, self.width, self.height))

    def update(self, deltaTime: float):
        self.previousPositionX = self.currentPositionX
        self.previousPositionY = self.currentPositionY

        self.currentPositionX += self.velocityX * deltaTime
        self.currentPositionY += self.velocityY * deltaTime

        if self.currentPositionX < 0 and self.velocityX < 0:
            self.velocityX *= -1
        elif self.currentPositionX > (WIDTH_CANVAS - self.width) and self.velocityX > 0:
            self.velocityX *= -1

        if self.currentPositionY < 0 and self.velocityY < 0:
            self.velocityY *= -1
        elif self.currentPositionY > (HEIGHT_CANVAS - self.height) and self.velocityY > 0:
            self.velocityY *= -1


# Initializing Pygame
pygame.init()

Rectangles = []

for i in range(10):
    Rectangles.append(MyRectangle(WIDTH_CANVAS / 2, HEIGHT_CANVAS / 2, random.randint(25, 50), random.randint(25, 50),
                                  random.randint(-100, 100), random.randint(-100, 100), random.randint(0, 255),
                                  random.randint(0, 255), random.randint(0, 255)))

running = True

clock = pygame.time.Clock()

lastFrameTime = time.time()

pygame.font.init() # you have to call this at the start,
                   # if you want to use this module.
myfont = pygame.font.SysFont('calibri', 30)

while running:
    # dt is the time delta in seconds (float).
    currentTime = time.time()
    dt = currentTime - lastFrameTime

    for event in pygame.event.get():
      if event.type == pygame.QUIT or (event.type == KEYDOWN and event.key == pygame.K_ESCAPE):
       quit()
      if event.type == KEYDOWN:
          if event.key == pygame.K_PLUS or event.key == pygame.K_KP_PLUS or event.key == pygame.K_RIGHT or event.key == pygame.K_UP:
              if(UPDATES_PER_SECOND<60):
                  UPDATES_PER_SECOND+=1
                  TIME_BETWEEN_UPDATES=1.0/UPDATES_PER_SECOND

          if event.key == pygame.K_MINUS or event.key == pygame.K_KP_MINUS or event.key == pygame.K_LEFT or event.key == pygame.K_DOWN:
              if (UPDATES_PER_SECOND > 1):
                  UPDATES_PER_SECOND -= 1
                  TIME_BETWEEN_UPDATES = 1.0 / UPDATES_PER_SECOND

          if event.key == pygame.K_KP_ENTER or event.key == pygame.K_SPACE or event.key == pygame.K_i  or event.key == pygame.K_RETURN:
              interpolate = not interpolate

    if dt > TIME_BETWEEN_UPDATES:
        lastFrameTime = currentTime
        for rectangle in Rectangles:
            rectangle.update(dt)
        dt=0

    interpolatedTime = 0
    if interpolate is True:
        interpolatedTime = dt / TIME_BETWEEN_UPDATES
        if interpolatedTime > 1:
            interpolatedTime = 1
        elif interpolatedTime < 0:
            interpolatedTime = 0


    screen.fill((255, 255, 255))

    font = pygame.font.SysFont("calibri", 16)
    text = font.render(f'Interpolated render: {str(interpolate)} {str(UPDATES_PER_SECOND)} Updates/s Time between Updates: {str(round(TIME_BETWEEN_UPDATES*1000,2))}ms', True, (0, 0, 0))

    screen.blit(text, (0, 0))
    for rectangle in Rectangles:
       rectangle.render(interpolatedTime)
    pygame.display.update()

    clock.tick(200) # Running loop at 200Hz

