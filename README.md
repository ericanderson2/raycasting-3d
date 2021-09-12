# raycasting-3d
 
This is an attempt to create a pseudo-3D renderer using raycasting. Written in Java, using java.awt.Graphics and fillRect() commands to render. This is not true 3D - it is a top down grid-world being rendered in such a way that it looks 3D. For each column of pixels on the screen, a ray is cast from the player to determine the distance to the nearest wall. That wall slice is then drawn in the pixel column, with the slice being taller if the wall is close and shorter if the wall is far.

Here's what the project looks like running! (Mild motion-sickness warning, the second video is smoother)

https://user-images.githubusercontent.com/28969597/133003841-397e833e-be47-4cec-a9e4-68270dc2d781.mp4

More of a doom style/enclosed feel, just changing the colors:

https://user-images.githubusercontent.com/28969597/133003846-d583117b-9ba0-497a-a1c7-1fe44ca4154e.mp4

## Features
Move the character with W and S and look around with A and D. There is simple acceleration and friction to make the controls feel better (only for movement, not for looking around). The player collides with walls and the edge of the world.

The world is grid-based and can be changed to be any size with any number of walls. Walls are a set height and can be given a 15x15 texture. Any number of different textures and colors are supported.

Code is written to be easily modified for a different window size, FOV, player width, etc. 

## Original Goals
* Player controls character movement and direction facing
* Walls render at correct height and shading depending on the location of the player
* Walls, floor, and ceiling have simple textures

All of the original goals except for floor and ceiling texturing are done. Floor/ceiling-casting ended up being more time consuming than expected.

## Improvements
* Aforementioned floor and ceiling casting to support textures for the floors and ceilings
* Walls of variable height
* Read textures from png files rather than hardcoding
* Support for textures of variable sizes
* Make looking side to side feel more natural (add acceleration?)

Many of the concepts used to create this demo were adapted from [this website](https://permadi.com/1996/05/ray-casting-tutorial-1/).

## MIT License
Copyright © 2021 Eric Anderson

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
