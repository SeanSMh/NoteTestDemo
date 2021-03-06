package sean.com.example.notetest.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * @author Sean
 * @data 2019/5/27
 */
public class MyAnimation extends Animation {
    int centerX, centerY;
    Camera camera = new Camera();

    /**
     * 获取坐标，定义动画时间
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //获得中心点坐标
        centerX = width;
        centerY = height;
        //动画执行时间 自行定义
        setDuration(3*1000);
        setInterpolator(new DecelerateInterpolator());
    }

    /**
     * 旋转的角度设置
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        camera.save();
        //中心是Y轴旋转，这里可以自行设置X轴 Y轴 Z轴
        camera.rotateX(-180 * interpolatedTime);
        //把我们的摄像头加在变换矩阵上
        camera.getMatrix(matrix);
        //这是翻转效果，设置翻转中心点
        matrix.preTranslate(-centerX, -centerY);  //将图片上移到x轴为底
        matrix.postTranslate(centerX, centerY);   //将图片复位，加上角度，看起来就像翻转

        camera.restore();
    }



}
