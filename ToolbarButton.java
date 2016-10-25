package cn.yh.toolbarmenubutton;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 左上角菜单按钮控件
 * 
 * @author yh
 * @date 2016-8-19
 */
public class ToolbarButton extends View {

	private int mWidth;
	private int mHeight;
	/**
	 * 菜单滑动的百分比
	 */
	private float position = 0;
	/**
	 * 横线的宽度
	 */
	private float lineWidth = 5;
	/**
	 * button的宽度（三条横线的宽度）
	 */
	private float buttonWidth = 70;
	/**
	 * button的高度（三条横线的高度）
	 */
	private float buttonHeight = 50;
	/**
	 * 变为箭头状态的缩小比,值越大，缩小的幅度越小。
	 */
	private float scaleNum = 5;
	private Paint mPaint;
	/**
	 * 画笔颜色
	 */
	private int mPaintColor = 0xff29aafd;
	private PathMeasure pm;
	private List<Path> lines;

	public ToolbarButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ToolbarButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ToolbarButton(Context context) {
		super(context);
		init();
	}

	private void init(Context context, AttributeSet attrs) {
		//如果需要自行添加
//		final TypedArray a = context.obtainStyledAttributes(attrs,
//				R.styleable.ToolbarButton);
//		try {
//			if (a != null) {
//				buttonHeight = a.getDimension(
//						R.styleable.ToolbarButton_buttonHeight, 50);
//				buttonWidth = a.getDimension(
//						R.styleable.ToolbarButton_buttonWidth, 70);
//				lineWidth = a.getDimension(R.styleable.ToolbarButton_lineWidth,
//						5);
//			}
//		} finally {
//			if (a != null) {
//				a.recycle();
//			}
//		}
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(mPaintColor);
		mPaint.setStrokeWidth(lineWidth);

		pm = new PathMeasure();
		lines = new ArrayList<>();

		initLine();

	}

	private void initLine() {
		Path line1 = new Path();
		float R = (float) Math.sqrt((buttonWidth / 2) * (buttonWidth / 2)
				+ (buttonHeight / 2) * (buttonHeight / 2));
		RectF rect1 = new RectF(-R, -R, R, R);

		float angleA = (float) (Math.atan((buttonWidth / 2)
				/ (buttonHeight / 2))
				/ Math.PI * 180);
		line1.addArc(rect1, 270 - angleA, (180 + angleA));
		line1.addArc(rect1, 270 + angleA, 270 - angleA);
		lines.add(line1);

		Path line2 = new Path();
		RectF rect2 = new RectF(-buttonWidth / 2, -buttonWidth / 2,
				buttonWidth / 2, buttonWidth / 2);
		line2.addArc(rect2, 180, 180);
		line2.addArc(rect2, 0, 180);

		lines.add(line2);
		Path line3 = new Path();
		line3.addArc(rect1, 90 + angleA, (180 - angleA));
		line3.addArc(rect1, 90 - angleA, 90 + angleA);
		lines.add(line3);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	public void update(float position) {
		this.position = position;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(mWidth / 2, mHeight / 2);

	
		float scaleN = (float) (position + scaleNum - 1) / scaleNum;
		canvas.scale(scaleN, scaleN);

		float[] pos1 = new float[2];
		float[] tan = new float[2];
		float[] pos2 = new float[2];
		for (int i = 0; i < lines.size(); i++) {

			pm.setPath(lines.get(i), false);
			pm.getPosTan(Math.abs(pm.getLength() * position), pos1, tan);
			pm.nextContour();
			pm.getPosTan(Math.abs(pm.getLength() * position), pos2, tan);
			canvas.drawLine(pos1[0], pos1[1], pos2[0], pos2[1], mPaint);
		}

	}

	public float getLineWidth() {
		return lineWidth;
	}

	/**
	 * 设置线宽
	 * 
	 * @author yh
	 * @date: 2016-8-23
	 * @param lineWidth
	 */
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getButtonWidth() {
		return buttonWidth;
	}

	/**
	 * 设置button的宽度（三条横线的宽度）
	 * 
	 * @author yh
	 * @date: 2016-8-23
	 * @param buttonWidth
	 */
	public void setButtonWidth(float buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	public float getButtonHeight() {
		return buttonHeight;
	}

	/**
	 * 设置button的高度（三条横线的高度）
	 * 
	 * @author yh
	 * @date: 2016-8-23
	 * @return
	 */
	public void setButtonHeight(float buttonHeight) {
		this.buttonHeight = buttonHeight;
	}

	public float getScaleNum() {
		return scaleNum;
	}

	/**
	 * 设置变为箭头状态的缩小比,值越大，缩小的幅度越小。
	 * 
	 * @author yh
	 * @date: 2016-8-23
	 * @return
	 */
	public void setScaleNum(float scaleNum) {
		this.scaleNum = scaleNum;
	}

	public int getmPaintColor() {
		return mPaintColor;
	}

	/**
	 * 设置图标的颜色
	 * 
	 * @author yh
	 * @date: 2016-8-23
	 * @return
	 */
	public void setmPaintColor(int mPaintColor) {
		this.mPaintColor = mPaintColor;
	}
}
