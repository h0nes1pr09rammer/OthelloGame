package com.lzq.othellogame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	/**
	 * 控件宽度
	 */
	private int screenWidth;
	/**
	 * 控件高度
	 */
	private int screenHeight;
	/**
	 *距离上下的长度
	 */
	private int paddingTopAndBottom;
	/**
	 *棋格边长
	 */
	private int chessPieceLength;
	/**
	 *棋子半径
	 */
	private int chessPieceRadius;
	/**
	 *点击坐标X
	 */
	private int touchX;
	/**
	 *点击坐标Y
	 */
	private int touchY;
	/**
	 *行数
	 */
	private int lines;
	/**
	 *列数
	 */
	private int columns;
	private boolean isFirst = true;
	/**
	 *棋盘
	 */
	private int chessBoard[][];
	/**
	 *轮到什么颜色的轮次
	 */
	private boolean isBlacked = false;
	private TextShow textShow;
	/**
	 *黑棋画笔
	 */
	private Paint leftPaint;
	/**
	 *白棋画笔
	 */
	private Paint rightPaint;
	/**
	 *边框画笔
	 */
	private Paint slidePaint;
	/**
	 *文字画笔
	 */
	private Paint textPaint;
	/**
	 *自定义参数-每行棋格数
	 */
	private int tabCount;
	
	public interface TextShow
	{
		void textShow(int a);
	}
	public void setTextShow(TextShow textShow)
	{
		this.textShow = textShow;
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GameView);
		tabCount = a.getInt(R.styleable.GameView_tab_count,8);
		a.recycle();
		// TODO Auto-generated constructor stub
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GameView);
		tabCount = a.getInt(R.styleable.GameView_tab_count,8);
		a.recycle();
		init();
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){
		isFirst = true;
		chessBoard = new int[tabCount][tabCount];
		textPaint = new Paint();
		textPaint.setTextSize(50);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.BLACK);

		leftPaint = new Paint();
		leftPaint.setAntiAlias(true);
		leftPaint.setColor(Color.BLACK);
		leftPaint.setStrokeWidth(1);

		rightPaint = new Paint();
		rightPaint.setAntiAlias(true);
		rightPaint.setColor(Color.WHITE);
		rightPaint.setStrokeWidth(1);

		slidePaint = new Paint();
		slidePaint.setStyle(Paint.Style.STROKE);
		slidePaint.setColor(Color.RED);
		slidePaint.setStrokeWidth(5);
		GameUtils.init(chessBoard,tabCount);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		screenWidth = MeasureSpec.getSize(widthMeasureSpec);
		screenHeight = MeasureSpec.getSize(heightMeasureSpec);
		paddingTopAndBottom = (screenHeight - screenWidth) / 2;
		chessPieceLength = screenWidth  / tabCount;
		chessPieceRadius = chessPieceLength  / 2;
		setMeasuredDimension(screenWidth,screenHeight);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		for (int i = 0; i <= tabCount; i++) {
			canvas.drawLine(chessPieceLength *i, paddingTopAndBottom, chessPieceLength *i, screenHeight - paddingTopAndBottom, leftPaint);
			canvas.drawLine(0, paddingTopAndBottom + chessPieceLength *i, screenWidth, paddingTopAndBottom + chessPieceLength *i, leftPaint);
		}
		if (isFirst) {
			canvas.drawCircle(screenWidth /2 , paddingTopAndBottom /2, chessPieceRadius *0.9f, rightPaint);
			isFirst = false;
		}else if (isBlacked) {
			canvas.drawCircle(screenWidth /2 , paddingTopAndBottom /2, chessPieceRadius *0.9f, leftPaint);
		}else{
			canvas.drawCircle(screenWidth /2 , paddingTopAndBottom /2, chessPieceRadius *0.9f, rightPaint);
		}
	
		for (int i = 0; i < tabCount; i++) {
			for (int j = 0; j < tabCount; j++) {
				if (chessBoard[i][j]==1) {
					canvas.drawCircle(chessPieceRadius + chessPieceLength *i, paddingTopAndBottom + chessPieceRadius + chessPieceLength *j, chessPieceRadius *0.9f, leftPaint);
				}else if(chessBoard[i][j]==2) {
					canvas.drawCircle(chessPieceRadius + chessPieceLength *i, paddingTopAndBottom + chessPieceRadius + chessPieceLength *j, chessPieceRadius *0.9f, rightPaint);
				}
			}
		}
		RectF rectF = new RectF(chessPieceLength *columns,paddingTopAndBottom+chessPieceLength *lines,chessPieceLength *(columns+1),paddingTopAndBottom+chessPieceLength *(lines+1));
		canvas.drawRect(rectF,slidePaint);
	}
	@SuppressLint("NewApi") @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchX =(int) event.getX();
			touchY =(int) event.getY();
			if(touchY >= paddingTopAndBottom && touchY <= paddingTopAndBottom + screenWidth)
			{
				lines = (touchY - paddingTopAndBottom)/ chessPieceLength;
				columns = touchX / chessPieceLength;
				if(!isBlacked)
				{
					if(GameUtils.removeUp(columns, lines, 2))
					{
						isBlacked = true;
					}else{
						showMsg(4);
					}
					
				}else
				{
					if(GameUtils.removeUp(columns, lines, 1))
					{
						isBlacked = false;
					}
					else{
						showMsg(4);
					}
				}
			}
			balance();
			postInvalidate();
			break;
		}
		return super.onTouchEvent(event);
	}

	private void balance() {
		if (GameUtils.end()==2) {
			showMsg(2);
			GameUtils.init(chessBoard,tabCount);
		}
		if(GameUtils.end()==1)
		{
			showMsg(1);
			GameUtils.init(chessBoard,tabCount);
		}
		if(GameUtils.end()==3){
			showMsg(3);
			GameUtils.init(chessBoard,tabCount);
		}
	}

	private void showMsg(int code){
		if (textShow!=null) {
			textShow.textShow(code);
		}
	}
	
}
