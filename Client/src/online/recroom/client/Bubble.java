package online.recroom.client;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

/**
 * Created by Yeedle on 5/17/2016 9:35 AM.
 */
public class Bubble extends Circle
{
    //all variables are as a percentage
    public double x;
    public double y;
    public double deltaX;
    public double deltaY;
    public double radius;

    public Bubble(double xAsAPercentage, double yAsAPercentage, double deltaXAsAPercentage, double deltaYAsAPercentage, double radiusAsAPercentage)
    {
        x = RecRoom.HEIGHT * xAsAPercentage;
        y = RecRoom.WIDTH * yAsAPercentage;
        super.setCenterX(x);
        super.setCenterY(y);
        Color color = getRandomColor();
        RadialGradient g = new RadialGradient(
                0, 0,
                0.35, 0.35,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.WHITE),
                new Stop(1.0, color));
        super.setFill(g);
    }

    private Color getRandomColor()
    {
        Double r = Math.random();
        double transparency = 0.3;
        return r < 0.5 ? Color.rgb(123, 104, 238, transparency) : r < 0.1 ? Color.rgb(123, 104, 238, transparency) : r < 0.15? Color.rgb(245, 245, 220, transparency) :
               r < 0.2 ? Color.rgb(0,     0, 255, transparency) : r < 0.25? Color.rgb(205,  92,  92, transparency) : r < 0.3 ? Color.rgb(139,   0, 139, transparency) :
               r < 0.35? Color.rgb(165,  42,  42, transparency) : r < 0.4 ? Color.rgb(0,   100,   0, transparency) : r < 0.45? Color.rgb(189, 183, 107, transparency) :
               r < 0.5 ? Color.rgb(85,  107,  47, transparency) : r < 0.55? Color.rgb(255, 140,   0, transparency) : r < 0.6 ? Color.rgb(153,  50, 204, transparency) :
               r < 0.65? Color.rgb(143, 188, 143, transparency) : r < 0.7 ? Color.rgb(255,  20, 147, transparency) : r < 0.75? Color.rgb(222, 184, 135, transparency) :
               r < 0.8 ? Color.rgb(105, 105, 105, transparency) : r < 0.85? Color.rgb(30,  144, 255, transparency) : r < 0.9 ? Color.rgb(188, 143, 143, transparency) :
               r < 0.95? Color.rgb(102, 205, 170, transparency) :           Color.rgb(173,216,230,transparency);
    }
}
