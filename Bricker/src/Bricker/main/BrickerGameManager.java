package Bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BrickerGameManager extends GameManager {

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(80);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        Vector2 windowCenter = windowDimensions.mult(0.5f);


        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", true);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()), backgroundImage);

        gameObjects().addGameObject(background, Layer.BACKGROUND);
        //creating ball.
        Renderable ballImage =
            imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(500));
        ball.setCenter(windowCenter);

        gameObjects().addGameObject(ball);

        Renderable paddleImage =
                imageReader.readImage("assets/paddle.png", true);
        //create user paddle
        GameObject userPaddle = new GameObject(Vector2.ZERO, new Vector2(200, 15), paddleImage);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int)windowDimensions.y()-30));

        //create ai paddle
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(200, 15), paddleImage);
        aiPaddle.setCenter(new Vector2(windowDimensions.x() / 2, 30));

        gameObjects().addGameObject(userPaddle);
        gameObjects().addGameObject(aiPaddle);
        createWalls(windowDimensions);
    }

    private void createWalls(Vector2 windowDimensions) {
        GameObject leftWall = new GameObject(new Vector2(-30, 0), new Vector2(30, windowDimensions.y()), null),
                rightWall = new GameObject(new Vector2(windowDimensions.x(), 0), new Vector2(30, windowDimensions.y()), null),
                topWall = new GameObject(new Vector2(0, -30), new Vector2(windowDimensions.x(), 30), null);
        gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topWall, Layer.STATIC_OBJECTS);
    }

    public static void main(String[] args){
        GameManager manager = new BrickerGameManager("Bouncing Ball", new Vector2(700, 500));
        manager.run();
    }
}
